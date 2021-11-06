// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.hardware.platform.unix.freebsd;

import java.util.Iterator;
import java.util.Map;
import oshi.util.ParseUtil;
import oshi.util.ExecutingCommand;
import java.util.ArrayList;
import java.util.HashMap;
import oshi.hardware.SoundCard;
import java.util.List;
import oshi.annotation.concurrent.Immutable;
import oshi.hardware.common.AbstractSoundCard;

@Immutable
final class FreeBsdSoundCard extends AbstractSoundCard
{
    private static final String LSHAL = "lshal";
    
    FreeBsdSoundCard(final String kernelVersion, final String name, final String codec) {
        super(kernelVersion, name, codec);
    }
    
    public static List<SoundCard> getSoundCards() {
        final Map<String, String> vendorMap = new HashMap<String, String>();
        final Map<String, String> productMap = new HashMap<String, String>();
        vendorMap.clear();
        productMap.clear();
        final List<String> sounds = new ArrayList<String>();
        String key = "";
        for (String line : ExecutingCommand.runNative("lshal")) {
            line = line.trim();
            if (line.startsWith("udi =")) {
                key = ParseUtil.getSingleQuoteStringValue(line);
            }
            else {
                if (key.isEmpty() || line.isEmpty()) {
                    continue;
                }
                if (line.contains("freebsd.driver =") && "pcm".equals(ParseUtil.getSingleQuoteStringValue(line))) {
                    sounds.add(key);
                }
                else if (line.contains("info.product")) {
                    productMap.put(key, ParseUtil.getStringBetween(line, '\''));
                }
                else {
                    if (!line.contains("info.vendor")) {
                        continue;
                    }
                    vendorMap.put(key, ParseUtil.getStringBetween(line, '\''));
                }
            }
        }
        final List<SoundCard> soundCards = new ArrayList<SoundCard>();
        for (final String _key : sounds) {
            soundCards.add(new FreeBsdSoundCard(productMap.get(_key), vendorMap.get(_key) + " " + productMap.get(_key), productMap.get(_key)));
        }
        return soundCards;
    }
}

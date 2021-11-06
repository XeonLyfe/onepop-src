//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.misc;

import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import com.onepop.api.util.chat.ChatSuffixUtil;
import net.minecraft.network.play.client.CPacketChatMessage;
import com.onepop.client.event.network.PacketEvent;
import com.onepop.api.util.client.FlagBoolUtil;
import com.onepop.api.setting.value.ValueString;
import com.onepop.api.setting.value.ValueEnum;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Chat Suffix", tag = "ChatSuffix", description = "Send at end message the custom client suffix.", category = ModuleCategory.MISC)
public class ModuleChatSuffix extends Module
{
    public static ValueEnum settingIgnorePrefixes;
    public static ValueString settingIgnoredPrefixes;
    public static ValueString settingSuffix;
    
    @Override
    public void onSetting() {
        ModuleChatSuffix.settingIgnoredPrefixes.setEnabled(ModuleChatSuffix.settingIgnorePrefixes.getValue() == FlagBoolUtil.TRUE);
    }
    
    @Listener
    public void onListen(final PacketEvent.Send event) {
        if (!(event.getPacket() instanceof CPacketChatMessage)) {
            return;
        }
        final CPacketChatMessage packet = (CPacketChatMessage)event.getPacket();
        String message = packet.getMessage();
        boolean isContinuable = true;
        if (ModuleChatSuffix.settingIgnoredPrefixes.isEnabled()) {
            for (final String prefixes : ModuleChatSuffix.settingIgnoredPrefixes.getValue().split("")) {
                if (message.startsWith(prefixes)) {
                    isContinuable = false;
                    break;
                }
            }
        }
        if (isContinuable) {
            message = message + " " + ChatSuffixUtil.hephaestus(ModuleChatSuffix.settingSuffix.getValue());
        }
        packet.message = message;
    }
    
    static {
        ModuleChatSuffix.settingIgnorePrefixes = new ValueEnum("Ignore Prefixes", "IgnorePrefixes", "Ignore specified characters.", FlagBoolUtil.TRUE);
        ModuleChatSuffix.settingIgnoredPrefixes = new ValueString("Ignored Prefixes", "IgnoredPrefixes", "Characters to ignore.", "/!;&$(\\:.@*#)");
        ModuleChatSuffix.settingSuffix = new ValueString("Suffix", "Suffix", "The lower case suffix.", "onepop");
    }
}

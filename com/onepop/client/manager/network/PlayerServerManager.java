//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.manager.network;

import java.util.Collection;
import com.onepop.api.util.client.NullUtil;
import net.minecraft.client.Minecraft;
import com.onepop.Onepop;
import java.util.Iterator;
import net.minecraft.client.network.NetworkPlayerInfo;
import java.util.ArrayList;
import com.onepop.api.manager.Manager;

public class PlayerServerManager extends Manager
{
    public static PlayerServerManager INSTANCE;
    private ArrayList<NetworkPlayerInfo> onlineList;
    
    public PlayerServerManager() {
        super("Player Server", "An manager with all players online on server.");
        PlayerServerManager.INSTANCE = this;
        this.onlineList = new ArrayList<NetworkPlayerInfo>();
    }
    
    public void setOnlineList(final ArrayList<NetworkPlayerInfo> onlineList) {
        this.onlineList = onlineList;
    }
    
    public ArrayList<NetworkPlayerInfo> getOnlineList() {
        return this.onlineList;
    }
    
    public static NetworkPlayerInfo get(final String name) {
        for (final NetworkPlayerInfo playersInfo : PlayerServerManager.INSTANCE.getOnlineList()) {
            if (playersInfo == null) {
                continue;
            }
            if (playersInfo.getGameProfile().getName().equalsIgnoreCase(name)) {
                return playersInfo;
            }
        }
        return null;
    }
    
    public static boolean isOnline() {
        final Minecraft mc = Onepop.getMinecraft();
        return mc.getConnection() != null;
    }
    
    @Override
    public void onUpdateAll() {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        if (PlayerServerManager.mc.getConnection().getPlayerInfoMap() == null) {
            return;
        }
        this.onlineList.addAll(PlayerServerManager.mc.getConnection().getPlayerInfoMap());
    }
}

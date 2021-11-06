//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.util.network;

import net.minecraft.client.network.NetworkPlayerInfo;

public class ServerUtil
{
    public static int getPing(final NetworkPlayerInfo player) {
        final boolean flag = player.getResponseTime() < 0;
        return flag ? -1 : player.getResponseTime();
    }
}

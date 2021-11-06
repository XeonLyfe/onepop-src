//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.util.client;

import com.onepop.Onepop;

public class NullUtil
{
    public static boolean isPlayerWorld() {
        return Onepop.MC.player == null && Onepop.MC.world == null;
    }
    
    public static boolean isWorld() {
        return Onepop.MC.world == null;
    }
    
    public static boolean isPlayer() {
        return Onepop.MC.player == null;
    }
    
    public static boolean isGUI() {
        return Onepop.MC.currentScreen == null;
    }
}

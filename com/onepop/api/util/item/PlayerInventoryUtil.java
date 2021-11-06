//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.util.item;

import com.onepop.Onepop;

public class PlayerInventoryUtil
{
    public static void setCurrentHotBarItem(final int slotIn) {
        Onepop.MC.player.inventory.currentItem = slotIn;
    }
}

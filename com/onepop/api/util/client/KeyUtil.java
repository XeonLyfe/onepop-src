//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.util.client;

import com.onepop.Onepop;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraft.client.settings.KeyBinding;

public class KeyUtil
{
    public static final KeyBinding[] ALL_MOVEMENT_KEY_BIND;
    
    public static void press(final KeyBinding keyBinding, final boolean pressed) {
        if (keyBinding.getKeyConflictContext() != KeyConflictContext.UNIVERSAL) {
            keyBinding.setKeyConflictContext((IKeyConflictContext)KeyConflictContext.UNIVERSAL);
        }
        KeyBinding.setKeyBindState(keyBinding.getKeyCode(), pressed);
    }
    
    public static boolean isMoving() {
        return !NullUtil.isPlayerWorld() && (Onepop.MC.gameSettings.keyBindForward.isKeyDown() || Onepop.MC.gameSettings.keyBindBack.isKeyDown() || Onepop.MC.gameSettings.keyBindLeft.isKeyDown() || Onepop.MC.gameSettings.keyBindRight.isKeyDown());
    }
    
    public static boolean isJumping() {
        return Onepop.MC.gameSettings.keyBindJump.isKeyDown();
    }
    
    public static boolean isPressed(final KeyBinding keyBinding) {
        return keyBinding.isKeyDown();
    }
    
    static {
        ALL_MOVEMENT_KEY_BIND = new KeyBinding[] { Onepop.MC.gameSettings.keyBindForward, Onepop.MC.gameSettings.keyBindBack, Onepop.MC.gameSettings.keyBindLeft, Onepop.MC.gameSettings.keyBindRight, Onepop.MC.gameSettings.keyBindJump };
    }
}

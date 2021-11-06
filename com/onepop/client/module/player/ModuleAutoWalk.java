//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.player;

import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import com.onepop.api.util.client.KeyUtil;
import com.onepop.api.ISLClass;
import com.onepop.client.event.client.ClientTickEvent;
import com.onepop.api.setting.value.ValueEnum;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Auto-Walk", tag = "AutoWalk", description = "Automatically walks.", category = ModuleCategory.PLAYER)
public class ModuleAutoWalk extends Module
{
    public static ValueEnum settingDirection;
    
    @Listener
    public void onUpdate(final ClientTickEvent event) {
        switch (ModuleAutoWalk.settingDirection.getValue()) {
            case FORWARD: {
                KeyUtil.press(ISLClass.mc.gameSettings.keyBindForward, true);
                break;
            }
            case BACK: {
                KeyUtil.press(ISLClass.mc.gameSettings.keyBindBack, true);
                break;
            }
            case LEFT: {
                KeyUtil.press(ISLClass.mc.gameSettings.keyBindLeft, true);
                break;
            }
            case RIGHT: {
                KeyUtil.press(ISLClass.mc.gameSettings.keyBindRight, true);
                break;
            }
        }
    }
    
    @Override
    public void onDisable() {
        KeyUtil.press(ISLClass.mc.gameSettings.keyBindForward, false);
    }
    
    static {
        ModuleAutoWalk.settingDirection = new ValueEnum("Direction", "Direction", "The direction of walk.", Direction.BACK);
    }
    
    public enum Direction
    {
        FORWARD, 
        BACK, 
        LEFT, 
        RIGHT;
    }
}

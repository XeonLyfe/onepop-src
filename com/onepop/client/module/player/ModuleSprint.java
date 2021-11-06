//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.player;

import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import com.onepop.api.util.client.NullUtil;
import com.onepop.client.event.client.ClientTickEvent;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Sprint", tag = "Sprint", description = "Automatically sprints player.", category = ModuleCategory.PLAYER)
public class ModuleSprint extends Module
{
    public static ValueBoolean settingAlways;
    
    @Listener
    public void onTick(final ClientTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        boolean flag = ModuleSprint.settingAlways.getValue();
        if ((ModuleSprint.mc.player.movementInput.field_192832_b != 0.0f || ModuleSprint.mc.player.movementInput.moveStrafe != 0.0f) && !ModuleSprint.settingAlways.getValue()) {
            flag = true;
        }
        ModuleSprint.mc.player.setSprinting(flag);
    }
    
    static {
        ModuleSprint.settingAlways = new ValueBoolean("Always", "Always", "Set every tick sprint to true!", false);
    }
}

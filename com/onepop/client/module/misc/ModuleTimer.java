//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.misc;

import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import com.onepop.api.util.client.NullUtil;
import com.onepop.client.event.client.RunTickEvent;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.setting.value.ValueNumber;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Timer", tag = "Timer", description = "Change all ticks in your Minecraft!", category = ModuleCategory.MISC)
public class ModuleTimer extends Module
{
    public static ValueNumber settingValue;
    public static ValueBoolean settingDisableGUI;
    
    @Override
    public void onShutdown() {
        this.setDisabled();
    }
    
    @Override
    public void onDisable() {
        this.disableTimer();
    }
    
    @Listener
    public void onTick(final RunTickEvent tick) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        this.updateTimer();
    }
    
    protected void updateTimer() {
        if (ModuleTimer.settingDisableGUI.getValue() && ModuleTimer.mc.currentScreen != null) {
            this.disableTimer();
        }
        else {
            ModuleTimer.mc.timer.field_194149_e = 50.0f / ModuleTimer.settingValue.getValue().floatValue();
        }
    }
    
    public void disableTimer() {
        ModuleTimer.mc.timer.field_194149_e = 50.0f;
    }
    
    static {
        ModuleTimer.settingValue = new ValueNumber("Value", "Value", "Sets custom timer value.", 2.0f, 0.1f, 30.0f);
        ModuleTimer.settingDisableGUI = new ValueBoolean("Disable in GUI", "DisableInGUI", "Disable timer when any GUIs is open!", true);
    }
}

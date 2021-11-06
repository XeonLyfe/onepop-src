//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.client;

import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import net.minecraft.client.gui.GuiScreen;
import com.onepop.Onepop;
import com.onepop.api.ISLClass;
import com.onepop.client.event.client.ClientTickEvent;
import com.onepop.api.setting.value.ValueNumber;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "HUD Editor", tag = "HUD", description = "Draws overlay component of client HUD.", category = ModuleCategory.CLIENT)
public class ModuleHUD extends Module
{
    public static ModuleHUD INSTANCE;
    public static ValueBoolean settingRender;
    public static ValueNumber settingSpeedHUE;
    public static ValueNumber settingRed;
    public static ValueNumber settingGreen;
    public static ValueNumber settingBlue;
    
    public ModuleHUD() {
        ModuleHUD.INSTANCE = this;
    }
    
    @Override
    public void onShutdown() {
        this.setDisabled();
    }
    
    @Listener
    public void onListen(final ClientTickEvent event) {
        if (ISLClass.mc.currentScreen != Onepop.getComponentClickGUI()) {
            ISLClass.mc.displayGuiScreen((GuiScreen)Onepop.getComponentClickGUI());
        }
    }
    
    @Override
    public void onDisable() {
        if (ISLClass.mc.currentScreen == Onepop.getComponentClickGUI()) {
            Onepop.getComponentClickGUI().setClosingGUI(false);
        }
    }
    
    static {
        ModuleHUD.settingRender = new ValueBoolean("Render", "Render", "Render HUDs components.", true);
        ModuleHUD.settingSpeedHUE = new ValueNumber("Speed HUE", "SpeedHUE", "The speed of rainbow hue cycle!", 20, 0, 100);
        ModuleHUD.settingRed = new ValueNumber("Red", "Red", "Color string.", 255, 0, 255);
        ModuleHUD.settingGreen = new ValueNumber("Green", "Green", "Color string.", 255, 0, 255);
        ModuleHUD.settingBlue = new ValueNumber("Blue", "Blue", "Color string.", 0, 0, 255);
    }
}

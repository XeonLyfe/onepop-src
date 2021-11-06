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
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Click GUI", tag = "ClickGUI", description = "Open GUI to manage module, settings...", category = ModuleCategory.CLIENT)
public class ModuleClickGUI extends Module
{
    public static ModuleClickGUI INSTANCE;
    public static ValueNumber settingScrollHeight;
    public static ValueNumber settingBaseRed;
    public static ValueNumber settingBaseGreen;
    public static ValueNumber settingBaseBlue;
    public static ValueNumber settingBackgroundRed;
    public static ValueNumber settingBackgroundGreen;
    public static ValueNumber settingBackgroundBlue;
    
    public ModuleClickGUI() {
        ModuleClickGUI.INSTANCE = this;
    }
    
    @Listener
    public void onListen(final ClientTickEvent event) {
        if (ISLClass.mc.currentScreen != Onepop.getModuleClick()) {
            ISLClass.mc.displayGuiScreen((GuiScreen)Onepop.getModuleClick());
        }
    }
    
    @Override
    public void onDisable() {
        if (ISLClass.mc.currentScreen == Onepop.getModuleClick()) {
            Onepop.getModuleClick().setClosingGUI(true);
        }
    }
    
    static {
        ModuleClickGUI.settingScrollHeight = new ValueNumber("Scroll Height", "ScrollHeight", "Makes the clamp for scroll.", 100, 200, 500);
        ModuleClickGUI.settingBaseRed = new ValueNumber("Base Red", "BaseRed", "The color base red!", 150, 0, 255);
        ModuleClickGUI.settingBaseGreen = new ValueNumber("Base Green", "BaseGreen", "The color base green!", 0, 0, 255);
        ModuleClickGUI.settingBaseBlue = new ValueNumber("Base Blue", "BaseBlue", "The color base blue!", 150, 0, 255);
        ModuleClickGUI.settingBackgroundRed = new ValueNumber("Back. Red", "BackgroundRed", "The color background red!", 0, 0, 255);
        ModuleClickGUI.settingBackgroundGreen = new ValueNumber("Back. Green", "BackgroundGreen", "The color background green!", 0, 0, 255);
        ModuleClickGUI.settingBackgroundBlue = new ValueNumber("Back. Blue", "BackgroundBlue", "The color background blue!", 0, 0, 255);
    }
}

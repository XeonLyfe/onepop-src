//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.render;

import com.onepop.api.ISLClass;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import net.minecraft.potion.PotionEffect;
import net.minecraft.init.MobEffects;
import com.onepop.api.util.client.NullUtil;
import com.onepop.client.event.client.RunTickEvent;
import com.onepop.api.setting.value.ValueEnum;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Full Bright", tag = "FullBright", description = "Changes brightness level of Minecraft.", category = ModuleCategory.RENDER)
public class ModuleFullBright extends Module
{
    public static ValueEnum settingMode;
    
    @Listener
    public void onRunTick(final RunTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        ModuleFullBright.mc.gameSettings.gammaSetting = 100.0f;
        if (ModuleFullBright.settingMode.getValue() == Mode.POTION) {
            ModuleFullBright.mc.player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 5210));
        }
    }
    
    @Override
    public void onEnable() {
        ISLClass.mc.gameSettings.gammaSetting = 100.0f;
    }
    
    @Override
    public void onDisable() {
        ModuleFullBright.mc.gameSettings.gammaSetting = 1.0f;
    }
    
    static {
        ModuleFullBright.settingMode = new ValueEnum("Mode", "Mode", "Modes for full bright!", Mode.POTION);
    }
    
    public enum Mode
    {
        POTION, 
        GAMMA;
    }
}

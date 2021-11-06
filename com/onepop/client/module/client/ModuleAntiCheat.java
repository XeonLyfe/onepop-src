//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.client;

import com.onepop.api.setting.value.ValueEnum;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Anti-Cheat", tag = "AntiCheat", description = "Enable if you know what anti-cheat is on.", category = ModuleCategory.CLIENT)
public class ModuleAntiCheat extends Module
{
    public static ModuleAntiCheat INSTANCE;
    public static ValueEnum settingType;
    
    public ModuleAntiCheat() {
        ModuleAntiCheat.INSTANCE = this;
    }
    
    public static float getRange() {
        float range = 0.0f;
        if (!ModuleAntiCheat.INSTANCE.isEnabled()) {
            range = ModuleAntiCheat.mc.playerController.getBlockReachDistance();
        }
        if (ModuleAntiCheat.settingType.getValue() == Type.NCP) {
            range = 4.3f;
        }
        if (ModuleAntiCheat.settingType.getValue() == Type.VANILLA) {
            range = ModuleAntiCheat.mc.playerController.getBlockReachDistance();
        }
        return range;
    }
    
    static {
        ModuleAntiCheat.settingType = new ValueEnum("Type", "Type", "Type of anti-cheat.", Type.NCP);
    }
    
    public enum Type
    {
        NCP, 
        VANILLA;
    }
}

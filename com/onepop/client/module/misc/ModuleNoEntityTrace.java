//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.misc;

import net.minecraft.item.ItemPickaxe;
import com.onepop.api.util.client.NullUtil;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "No Entity Trace", tag = "NoEntityTrace", description = "No entity trace when mining!", category = ModuleCategory.MISC)
public class ModuleNoEntityTrace extends Module
{
    public static ModuleNoEntityTrace INSTANCE;
    public static ValueBoolean settingOnlyPickaxe;
    
    public ModuleNoEntityTrace() {
        ModuleNoEntityTrace.INSTANCE = this;
    }
    
    public boolean doAccept() {
        return !NullUtil.isPlayerWorld() && (!ModuleNoEntityTrace.settingOnlyPickaxe.getValue() || ModuleNoEntityTrace.mc.player.getHeldItemMainhand().getItem() instanceof ItemPickaxe);
    }
    
    static {
        ModuleNoEntityTrace.settingOnlyPickaxe = new ValueBoolean("Only Pickaxe", "OnlyPickaxe", "Only pickaxe.", false);
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.misc;

import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import net.minecraft.item.ItemPickaxe;
import com.onepop.api.util.client.NullUtil;
import com.onepop.client.event.client.RunTickEvent;
import com.onepop.api.util.client.KeyUtil;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Auto-Mine", tag = "AutoMine", description = "Auto clicks mouse for mine.", category = ModuleCategory.MISC)
public class ModuleAutoMine extends Module
{
    @Override
    public void onDisable() {
        KeyUtil.press(ModuleAutoMine.mc.gameSettings.keyBindAttack, false);
    }
    
    @Listener
    public void onTick(final RunTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        if (ModuleAutoMine.mc.player.getHeldItemMainhand().getItem() instanceof ItemPickaxe) {
            KeyUtil.press(ModuleAutoMine.mc.gameSettings.keyBindAttack, true);
        }
        else {
            KeyUtil.press(ModuleAutoMine.mc.gameSettings.keyBindAttack, false);
        }
    }
}

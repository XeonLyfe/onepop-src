//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.player;

import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import com.onepop.client.event.client.RunTickEvent;
import com.onepop.api.util.client.NullUtil;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Creative Fly", tag = "CreativeFly", description = "Force creative fly.", category = ModuleCategory.PLAYER)
public class ModuleCreativeFly extends Module
{
    @Override
    public void onDisable() {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        ModuleCreativeFly.mc.player.capabilities.isFlying = false;
    }
    
    @Listener
    public void onTick(final RunTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        ModuleCreativeFly.mc.player.capabilities.isFlying = true;
    }
}

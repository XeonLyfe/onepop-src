//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.misc;

import com.onepop.api.util.client.NullUtil;
import com.onepop.client.event.client.RunTickEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import net.minecraft.network.play.client.CPacketPlayer;
import com.onepop.client.event.network.PacketEvent;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Anti-Hunger", tag = "AntiHunger", description = "Preserve hunger!", category = ModuleCategory.MISC)
public class ModuleAntiHunger extends Module
{
    @Listener
    public void onPacketReceive(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer) {
            final CPacketPlayer packet = (CPacketPlayer)event.getPacket();
            packet.onGround = false;
        }
    }
    
    @Listener
    public void onListen(final RunTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        if (ModuleAntiHunger.mc.player.isSprinting()) {
            ModuleAntiHunger.mc.player.setSprinting(false);
        }
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.player;

import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import net.minecraft.network.play.client.CPacketPlayer;
import com.onepop.api.util.client.NullUtil;
import com.onepop.client.event.network.PacketEvent;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "No Fall", tag = "NoFall", description = "Prevent fall.", category = ModuleCategory.PLAYER)
public class ModuleNoFall extends Module
{
    @Listener
    public void onPacketSend(final PacketEvent.Send event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        if (event.getPacket() instanceof CPacketPlayer && ModuleNoFall.mc.player.fallDistance > 0.0f) {
            final CPacketPlayer packet = (CPacketPlayer)event.getPacket();
            packet.onGround = true;
        }
    }
}

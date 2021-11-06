//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.misc;

import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import com.onepop.client.event.network.PacketEvent;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Build Height", tag = "BuildHeight", description = "Extend build height at 255.", category = ModuleCategory.MISC)
public class ModuleBuildHeight extends Module
{
    @Listener
    public void onPacketReceive(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
            final CPacketPlayerTryUseItemOnBlock packet = (CPacketPlayerTryUseItemOnBlock)event.getPacket();
            if (packet.getPos().getY() >= 255 && packet.getDirection() == EnumFacing.UP) {
                packet.placedBlockDirection = EnumFacing.DOWN;
            }
        }
    }
}

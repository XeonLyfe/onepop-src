//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.player;

import com.onepop.client.event.entity.PushPlayerEvent;
import com.onepop.client.event.entity.PushWatterEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import com.onepop.api.util.client.NullUtil;
import com.onepop.client.event.network.PacketEvent;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Velocity", tag = "Velocity", description = "No kinetic force for Minecraft.", category = ModuleCategory.PLAYER)
public class ModuleVelocity extends Module
{
    public static ValueBoolean settingCancelExplosion;
    public static ValueBoolean settingCancelVelocity;
    public static ValueBoolean settingLiquid;
    public static ValueBoolean settingPush;
    
    @Listener
    public void onListen(final PacketEvent.Receive event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        if (event.getPacket() instanceof SPacketExplosion && ModuleVelocity.settingCancelExplosion.getValue()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketEntityVelocity && ((SPacketEntityVelocity)event.getPacket()).entityID == ModuleVelocity.mc.player.getEntityId() && ModuleVelocity.settingCancelVelocity.getValue()) {
            event.setCanceled(true);
        }
    }
    
    @Listener
    public void onWatter(final PushWatterEvent event) {
        if (ModuleVelocity.settingLiquid.getValue()) {
            event.setCanceled(true);
        }
    }
    
    @Listener
    public void onPush(final PushPlayerEvent event) {
        if (ModuleVelocity.settingPush.getValue()) {
            event.setCanceled(true);
        }
    }
    
    static {
        ModuleVelocity.settingCancelExplosion = new ValueBoolean("Cancel Explosion", "CancelExplosion", "Client cancel explosion packet event.", true);
        ModuleVelocity.settingCancelVelocity = new ValueBoolean("Cancel Velocity", "CancelVelocity", "Cancels velocity hits.", true);
        ModuleVelocity.settingLiquid = new ValueBoolean("Liquid", "Liquid", "No liquid push.", true);
        ModuleVelocity.settingPush = new ValueBoolean("Push", "Push", "No push player.", true);
    }
}

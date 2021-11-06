//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.misc;

import net.minecraft.network.play.client.CPacketPlayer;
import com.onepop.client.event.network.PacketEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import net.minecraft.util.EnumHand;
import com.onepop.client.event.client.RunTickEvent;
import java.util.Iterator;
import java.util.Collection;
import com.onepop.Onepop;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import com.onepop.api.ISLClass;
import com.onepop.api.util.client.NullUtil;
import java.util.ArrayList;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.Packet;
import java.util.List;
import com.onepop.api.setting.value.ValueNumber;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Blink", tag = "Blink", description = "Cancels packets then sends on disable.", category = ModuleCategory.MISC)
public class ModuleBlink extends Module
{
    public static ValueNumber settingPacketLimiter;
    private final List<Packet<?>> currentPlayerPacket;
    private int packetsCount;
    private EntityOtherPlayerMP customPlayer;
    
    public ModuleBlink() {
        this.currentPlayerPacket = new ArrayList<Packet<?>>();
    }
    
    @Override
    public void onShutdown() {
        this.setDisabled();
    }
    
    @Override
    public void onEnable() {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        this.packetsCount = 0;
        (this.customPlayer = new EntityOtherPlayerMP((World)ISLClass.mc.world, ISLClass.mc.player.getGameProfile())).copyLocationAndAnglesFrom((Entity)ISLClass.mc.player);
        Onepop.getEntityWorldManager().saveEntity(-888, (Entity)this.customPlayer);
        ModuleBlink.mc.world.addEntityToWorld(-888, (Entity)this.customPlayer);
    }
    
    @Override
    public void onDisable() {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        Onepop.getEntityWorldManager().removeEntity(-888);
        ModuleBlink.mc.world.removeEntityFromWorld(-888);
        this.packetsCount = 0;
        for (final Packet<?> packets : new ArrayList(this.currentPlayerPacket)) {
            ModuleBlink.mc.player.connection.sendPacket((Packet)packets);
            this.currentPlayerPacket.remove(packets);
        }
        this.currentPlayerPacket.clear();
    }
    
    @Listener
    public void onTick(final RunTickEvent event) {
        if (this.customPlayer != null) {
            this.customPlayer.setHealth(ModuleBlink.mc.player.getHealth());
            this.customPlayer.setHeldItem(EnumHand.MAIN_HAND, ModuleBlink.mc.player.getHeldItemMainhand());
            this.customPlayer.setHeldItem(EnumHand.OFF_HAND, ModuleBlink.mc.player.getHeldItemOffhand());
            this.customPlayer.inventory = ModuleBlink.mc.player.inventory;
        }
        if (this.packetsCount >= ModuleBlink.settingPacketLimiter.getValue().intValue()) {
            this.setDisabled();
        }
    }
    
    @Listener
    public void onSendPacket(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer) {
            this.currentPlayerPacket.add(event.getPacket());
            ++this.packetsCount;
            event.setCanceled(true);
        }
    }
    
    static {
        ModuleBlink.settingPacketLimiter = new ValueNumber("Packet Limiter", "PacketLimiter", "The maximum packets for resend!", 32, 16, 64);
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.player;

import net.minecraft.world.World;
import com.onepop.api.util.entity.PlayerUtil;
import com.onepop.Onepop;
import com.onepop.client.event.entity.PlayerMoveEvent;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import com.onepop.client.event.network.PacketEvent;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import net.minecraft.util.EnumHand;
import com.onepop.api.ISLClass;
import com.onepop.api.util.client.NullUtil;
import com.onepop.client.event.client.ClientTickEvent;
import net.minecraft.entity.Entity;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.setting.value.ValueNumber;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Freecam", tag = "Freecam", description = "Cancel your server movements and fly client.", category = ModuleCategory.PLAYER)
public class ModuleFreecam extends Module
{
    public static ValueNumber settingSpeed;
    public static ValueBoolean settingRotate;
    public static ValueBoolean settingRotateHead;
    public static ValueBoolean settingMoveDirection;
    private EntityOtherPlayerMP customPlayer;
    private Entity ridingEntity;
    private boolean isRiding;
    private double[] lastPosition;
    private float[] lastRotation;
    
    @Override
    public void onShutdown() {
        this.setDisabled();
    }
    
    @Listener
    public void onListenClientTick(final ClientTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        ISLClass.mc.player.setVelocity(0.0, 0.0, 0.0);
        ISLClass.mc.player.capabilities.isFlying = true;
        ISLClass.mc.player.capabilities.setFlySpeed(ModuleFreecam.settingSpeed.getValue().intValue() * 2 / 1000.0f);
        this.customPlayer.setRotationYawHead(ModuleFreecam.settingRotateHead.getValue() ? ISLClass.mc.player.getRotationYawHead() : this.lastRotation[0]);
        if (ModuleFreecam.settingRotate.getValue()) {
            this.customPlayer.rotationYaw = ISLClass.mc.player.rotationYaw;
            this.customPlayer.rotationPitch = ISLClass.mc.player.rotationPitch;
        }
        float playerRotationYaw = ISLClass.mc.player.rotationYaw;
        final float playerRotationPitch = ISLClass.mc.player.rotationPitch;
        float playerForward = ISLClass.mc.player.movementInput.field_192832_b;
        float playerStrafe = ISLClass.mc.player.movementInput.moveStrafe;
        this.customPlayer.setHealth(ModuleFreecam.mc.player.getHealth());
        this.customPlayer.inventory = ModuleFreecam.mc.player.inventory;
        this.customPlayer.setHeldItem(EnumHand.MAIN_HAND, ModuleFreecam.mc.player.getHeldItemMainhand());
        this.customPlayer.setHeldItem(EnumHand.OFF_HAND, ModuleFreecam.mc.player.getHeldItemOffhand());
        final float speed = ModuleFreecam.settingSpeed.getValue().intValue() * 6 / 1000.0f;
        if (ModuleFreecam.settingMoveDirection.getValue() && playerForward != 0.0f) {
            ModuleFreecam.mc.player.motionY = ((playerForward > 0.0) ? (playerForward - playerRotationPitch) : (playerForward + playerRotationPitch)) / (1000.0f - ModuleFreecam.settingSpeed.getValue().intValue() * 10.0f);
        }
        if (playerForward == 0.0 && playerStrafe == 0.0) {
            ISLClass.mc.player.motionX = 0.0;
            ISLClass.mc.player.motionZ = 0.0;
        }
        else if ((playerForward != 0.0 & playerStrafe != 0.0) && playerForward != 0.0) {
            if (playerStrafe > 0.0) {
                playerRotationYaw += ((playerForward > 0.0) ? -45 : 45);
            }
            else if (playerStrafe < 0.0) {
                playerRotationYaw += ((playerForward > 0.0) ? 45 : -45);
            }
            playerStrafe = 0.0f;
            if (playerForward > 0.0) {
                playerForward = 1.0f;
            }
            else if (playerForward < 0.0f) {
                playerForward = -1.0f;
            }
        }
        ISLClass.mc.player.motionX = playerForward * speed * Math.cos(Math.toRadians(playerRotationYaw + 90.0f)) + playerStrafe * speed * Math.sin(Math.toRadians(playerRotationYaw + 90.0f));
        ISLClass.mc.player.motionZ = playerForward * speed * Math.sin(Math.toRadians(playerRotationYaw + 90.0f)) - playerStrafe * speed * Math.cos(Math.toRadians(playerRotationYaw + 90.0f));
    }
    
    @Listener
    public void onListenPushPlayer(final PlayerSPPushOutOfBlocksEvent event) {
        event.setCanceled(true);
    }
    
    @Listener
    public void onListenEvent(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer || event.getPacket() instanceof CPacketInput) {
            event.setCanceled(true);
        }
    }
    
    @Listener
    public void onListenPlayerMove(final PlayerMoveEvent event) {
        ISLClass.mc.player.noClip = true;
    }
    
    @Override
    public void onDisable() {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        ISLClass.mc.player.capabilities.isFlying = false;
        ISLClass.mc.player.noClip = false;
        Onepop.getEntityWorldManager().removeEntity(-100);
        ISLClass.mc.world.removeEntityFromWorld(-100);
        if (this.isRiding) {
            ISLClass.mc.player.startRiding(this.ridingEntity, true);
        }
        else {
            ISLClass.mc.player.setPositionAndRotation(this.lastPosition[0], this.lastPosition[1], this.lastPosition[2], this.lastRotation[0], this.lastRotation[1]);
        }
    }
    
    @Override
    public void onEnable() {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        this.lastPosition = PlayerUtil.getPos();
        this.lastRotation = PlayerUtil.getRotation();
        (this.customPlayer = new EntityOtherPlayerMP((World)ISLClass.mc.world, ISLClass.mc.player.getGameProfile())).copyLocationAndAnglesFrom((Entity)ISLClass.mc.player);
        this.isRiding = (ISLClass.mc.player.isRiding() && ISLClass.mc.player.getRidingEntity() != null);
        if (this.isRiding) {
            this.ridingEntity = ISLClass.mc.player.getRidingEntity();
            ISLClass.mc.player.dismountRidingEntity();
        }
        Onepop.getEntityWorldManager().saveEntity(-100, (Entity)this.customPlayer);
        ISLClass.mc.world.addEntityToWorld(-100, (Entity)this.customPlayer);
    }
    
    static {
        ModuleFreecam.settingSpeed = new ValueNumber("Speed", "Speed", "Speed fly.", 50, 0, 100);
        ModuleFreecam.settingRotate = new ValueBoolean("Rotate", "Rotate", "Enable rotation for you watch your player rotating at freecam.", true);
        ModuleFreecam.settingRotateHead = new ValueBoolean("Rotate Head", "RotateHead", "Enable head rotate to your main player at freecam.", true);
        ModuleFreecam.settingMoveDirection = new ValueBoolean("Move Direction", "MoveDirection", "Moves y by direction pitch/looking.", false);
    }
}

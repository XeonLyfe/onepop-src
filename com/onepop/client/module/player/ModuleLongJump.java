//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.player;

import com.onepop.api.ISLClass;
import net.minecraft.init.MobEffects;
import com.onepop.client.event.entity.PlayerMoveEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import com.onepop.api.util.client.NullUtil;
import com.onepop.client.event.network.PacketEvent;
import com.onepop.api.setting.value.ValueEnum;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Long Jump", tag = "LongJump", description = "Uses damage to long jump.", category = ModuleCategory.PLAYER)
public class ModuleLongJump extends Module
{
    public static ModuleLongJump INSTANCE;
    public static ValueBoolean settingInstaDisable;
    public static ValueEnum settingMode;
    private boolean ableJump;
    private int lastMotionX;
    private int lastMotionZ;
    
    public ModuleLongJump() {
        ModuleLongJump.INSTANCE = this;
    }
    
    public boolean isAbleJump() {
        return this.ableJump;
    }
    
    @Override
    public void onSetting() {
        if (!this.isEnabled()) {
            this.ableJump = false;
        }
    }
    
    @Listener
    public void onListen(final PacketEvent.Receive event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        if (ModuleLongJump.mc.player.onGround) {
            return;
        }
        if (!(event.getPacket() instanceof SPacketEntityVelocity)) {
            return;
        }
        final SPacketEntityVelocity packet = (SPacketEntityVelocity)event.getPacket();
        if (packet.getEntityID() != ModuleLongJump.mc.player.entityId) {
            return;
        }
        this.ableJump = (ModuleLongJump.settingMode.getValue() == Mode.INSTANT || ModuleLongJump.settingMode.getValue() == Mode.CRAZY);
        this.lastMotionX = packet.getMotionX();
        this.lastMotionZ = packet.getMotionZ();
        final double motionX = packet.getMotionX() / 8000.0;
        final double motionZ = packet.getMotionZ() / 8000.0;
        if (ModuleLongJump.settingMode.getValue() == Mode.MOTION || ModuleLongJump.settingMode.getValue() == Mode.CRAZY) {
            ModuleLongJump.mc.player.setVelocity(motionX, ModuleLongJump.mc.player.motionY, motionZ);
        }
        if (ModuleLongJump.settingInstaDisable.getValue() && ModuleLongJump.settingMode.getValue() == Mode.MOTION) {
            this.setDisabled();
        }
    }
    
    @Listener
    public void onMove(final PlayerMoveEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        if (ModuleLongJump.mc.player.onGround) {
            return;
        }
        if (!this.ableJump) {
            return;
        }
        if (ModuleLongJump.settingMode.getValue() != Mode.INSTANT && ModuleLongJump.settingMode.getValue() != Mode.CRAZY) {
            return;
        }
        final double motionX = this.lastMotionX / 8000.0;
        final double motionZ = this.lastMotionZ / 8000.0;
        double speed = (Math.sqrt(motionX * motionX + motionZ * motionZ) > 1.704780062794025) ? Math.sqrt(motionX * motionX + motionZ * motionZ) : 1.704780062794025;
        if (ModuleLongJump.mc.player.isPotionActive(MobEffects.SPEED)) {
            final int amplifier = ModuleLongJump.mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
            speed *= 1.0 + 0.2 * (amplifier + 1);
        }
        final float playerRotationYaw = ISLClass.mc.player.rotationYaw;
        final float playerForward = 1.0f;
        final float playerStrafe = ISLClass.mc.player.movementInput.moveStrafe;
        event.setX(playerForward * speed * Math.cos(Math.toRadians(playerRotationYaw + 90.0f)) + playerStrafe * speed * Math.sin(Math.toRadians(playerRotationYaw + 90.0f)));
        event.setZ(playerForward * speed * Math.sin(Math.toRadians(playerRotationYaw + 90.0f)) - playerStrafe * speed * Math.cos(Math.toRadians(playerRotationYaw + 90.0f)));
        this.ableJump = false;
        if (ModuleLongJump.settingInstaDisable.getValue()) {
            this.setDisabled();
        }
    }
    
    static {
        ModuleLongJump.settingInstaDisable = new ValueBoolean("Insta Disable", "InstaDisable", "Insta disables when you jump.", false);
        ModuleLongJump.settingMode = new ValueEnum("Mode", "Mode", "Modes for jump.", Mode.INSTANT);
    }
    
    public enum Mode
    {
        MOTION, 
        INSTANT, 
        CRAZY;
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.player;

import com.onepop.client.manager.world.BlockManager;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.Vec3i;
import com.onepop.api.util.entity.PlayerUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.init.MobEffects;
import com.onepop.api.ISLClass;
import com.onepop.client.event.entity.PlayerMoveEvent;
import com.onepop.client.event.client.ClientTickEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import com.onepop.api.util.client.NullUtil;
import com.onepop.client.event.network.PacketEvent;
import com.onepop.api.setting.value.ValueEnum;
import com.onepop.api.setting.value.ValueNumber;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Strafe", tag = "Strafe", description = "Allows you control air movement.", category = ModuleCategory.PLAYER)
public class ModuleStrafe extends Module
{
    public static ValueBoolean settingOnGround;
    public static ValueBoolean settingFastControll;
    public static ValueBoolean settingLowhop;
    public static ValueNumber settingLowhopSize;
    public static ValueEnum settingBoostExplosion;
    public static ValueBoolean settingSpeedEffectAmpl;
    public static ValueEnum settingStrafingType;
    public static ValueEnum settingJumpMode;
    private boolean isBoosting;
    private int motionX;
    private int motionZ;
    
    @Override
    public void onSetting() {
        ModuleStrafe.settingLowhopSize.setEnabled(ModuleStrafe.settingLowhop.getValue());
    }
    
    @Listener
    public void onReceivePacket(final PacketEvent.Receive event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        if (event.getPacket() instanceof SPacketEntityVelocity && ModuleStrafe.settingBoostExplosion.getValue() != BoostType.NONE) {
            final SPacketEntityVelocity velocity = (SPacketEntityVelocity)event.getPacket();
            if (ModuleStrafe.settingBoostExplosion.getValue() == BoostType.MOTION && velocity.getEntityID() == ModuleStrafe.mc.player.entityId) {
                final double velocityX = this.motionX / 8000.0;
                final double velocityZ = this.motionZ / 8000.0;
                ModuleStrafe.mc.player.setVelocity(velocityX, ModuleStrafe.mc.player.motionY, velocityZ);
            }
            this.motionX = velocity.getMotionX();
            this.motionZ = velocity.getMotionZ();
            this.isBoosting = (velocity.getEntityID() == ModuleStrafe.mc.player.entityId && ModuleStrafe.settingBoostExplosion.getValue() == BoostType.SPEED);
        }
    }
    
    @Listener
    public void onListenClientTick(final ClientTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
    }
    
    @Listener
    public void onListenPlayerMove(final PlayerMoveEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        if (ModuleStrafe.mc.player.isSneaking() || ModuleStrafe.mc.player.isOnLadder() || ModuleStrafe.mc.player.isInWeb || ModuleStrafe.mc.player.isInLava() || ModuleStrafe.mc.player.isInWater() || ModuleStrafe.mc.player.capabilities.isFlying || (ModuleLongJump.INSTANCE.isEnabled() && ModuleLongJump.INSTANCE.isAbleJump())) {
            return;
        }
        if (!ModuleStrafe.settingOnGround.getValue() && !ModuleStrafe.settingLowhop.getValue() && ISLClass.mc.player.onGround) {
            return;
        }
        float speed = (Math.sqrt(event.getX() * event.getX() + event.getZ() * event.getZ()) > 0.2872999906539917) ? ((float)Math.sqrt(event.getX() * event.getX() + event.getZ() * event.getZ())) : 0.2873f;
        if (ModuleStrafe.mc.player.isPotionActive(MobEffects.SPEED) && ModuleStrafe.settingSpeedEffectAmpl.getValue()) {
            final int amplifier = ModuleStrafe.mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
            speed *= (float)(1.0 + 0.2 * (amplifier + 1));
        }
        float playerRotationYaw = ISLClass.mc.player.rotationYaw;
        final float playerRotationPitch = ISLClass.mc.player.rotationPitch;
        float playerForward = ISLClass.mc.player.movementInput.field_192832_b;
        float playerStrafe = ISLClass.mc.player.movementInput.moveStrafe;
        if (playerForward == 0.0 && playerStrafe == 0.0) {
            event.setX(0.0);
            event.setZ(0.0);
        }
        else {
            if (playerForward != 0.0 && playerStrafe != 0.0 && playerForward != 0.0) {
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
            boolean underground = false;
            for (final BlockPos add : new BlockPos[] { new BlockPos(1, -1, 0), new BlockPos(-1, -1, 0), new BlockPos(1, -1, 1), new BlockPos(-1, -1, 1), new BlockPos(-1, -1, -1), new BlockPos(0, -1, 1), new BlockPos(0, -1, -1) }) {
                final BlockPos offset = PlayerUtil.getBlockPos().add((Vec3i)add);
                if (ISLClass.mc.world.getBlockState(offset).getBlock() == Blocks.AIR) {
                    underground = true;
                    break;
                }
            }
            final boolean lowhoping = !ModuleStrafe.mc.gameSettings.keyBindJump.isKeyDown() && ModuleStrafe.settingLowhop.getValue() && !underground && !BlockManager.getAirSurroundPlayer().isEmpty();
            if (lowhoping) {
                if (ModuleStrafe.mc.player.onGround) {
                    if (ModuleStrafe.settingFastControll.getValue()) {
                        speed = 0.6174077f;
                    }
                    event.setY(ModuleStrafe.mc.player.motionY = this.getMotionJumpY(ModuleStrafe.settingLowhopSize.getValue().intValue() / 1000.0f));
                }
            }
            else if ((ModuleStrafe.mc.gameSettings.keyBindJump.isKeyDown() || ModuleStrafe.settingJumpMode.getValue() == JumpMode.AUTO) && ModuleStrafe.mc.player.onGround) {
                if (ModuleStrafe.settingFastControll.getValue()) {
                    speed = 0.6174077f;
                }
                event.setY(ModuleStrafe.mc.player.motionY = this.getMotionJumpY(0.4012313f));
            }
            if (this.isBoosting) {
                final double velocityX = this.motionX / 8000.0;
                final double velocityZ = this.motionZ / 8000.0;
                speed += (float)Math.sqrt(velocityX * velocityX + velocityZ * velocityZ);
                this.isBoosting = false;
            }
            switch (ModuleStrafe.settingStrafingType.getValue()) {
                case MINIMAL: {
                    event.setX(playerForward * speed * Math.cos(Math.toRadians(playerRotationYaw + 90.0f)) + playerStrafe * speed * Math.sin(Math.toRadians(playerRotationYaw + 90.0f)));
                    event.setZ(playerForward * speed * Math.sin(Math.toRadians(playerRotationYaw + 90.0f)) - playerStrafe * speed * Math.cos(Math.toRadians(playerRotationYaw + 90.0f)));
                    break;
                }
                case LARGE: {
                    final double x = Math.cos(Math.toRadians(playerRotationYaw + 90.0f));
                    final double z = Math.sin(Math.toRadians(playerRotationYaw + 90.0f));
                    event.setX(playerForward * speed * x + playerStrafe * speed * z);
                    event.setZ(playerForward * speed * z - playerStrafe * speed * x);
                    break;
                }
            }
        }
    }
    
    public float getMotionJumpY(final float size) {
        float y = size;
        if (ISLClass.mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
            final int amplify = ISLClass.mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier();
            y += (amplify + 1) * 0.1f;
        }
        return y;
    }
    
    static {
        ModuleStrafe.settingOnGround = new ValueBoolean("On Ground", "OnGround", "Controll strafe onground!", true);
        ModuleStrafe.settingFastControll = new ValueBoolean("Fast Controll", "FastControll", "Make your air controll very fast!", true);
        ModuleStrafe.settingLowhop = new ValueBoolean("Lowhop", "Lowhop", "Makes you lowhop!", true);
        ModuleStrafe.settingLowhopSize = new ValueNumber("Lowhop Size", "LowhopSize", "The jump size!", 200, 0, 300);
        ModuleStrafe.settingBoostExplosion = new ValueEnum("Boost", "Boost", "Boost you!", BoostType.SPEED);
        ModuleStrafe.settingSpeedEffectAmpl = new ValueBoolean("Speed Effect Ampl", "SpeedEffectAmpl", "Increase speed with effects!", true);
        ModuleStrafe.settingStrafingType = new ValueEnum("Strafing Type", "StrafingType", "Uses diff types of strafing calculation.", StrafingType.LARGE);
        ModuleStrafe.settingJumpMode = new ValueEnum("Jump Mode", "JumpMode", "Mode jump.", JumpMode.AUTO);
    }
    
    public enum JumpMode
    {
        AUTO, 
        MANUAL;
    }
    
    public enum StrafingType
    {
        MINIMAL, 
        LARGE;
    }
    
    public enum BoostType
    {
        SPEED, 
        MOTION, 
        NONE;
    }
}

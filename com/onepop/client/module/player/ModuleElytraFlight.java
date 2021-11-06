//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.player;

import me.rina.turok.util.TurokMath;
import com.onepop.api.ISLClass;
import net.minecraft.entity.MoverType;
import com.onepop.client.event.entity.PlayerMoveEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import com.onepop.api.util.client.KeyUtil;
import net.minecraft.init.Items;
import com.onepop.api.util.item.SlotUtil;
import com.onepop.api.util.client.NullUtil;
import com.onepop.client.event.client.RunTickEvent;
import com.onepop.api.setting.value.ValueEnum;
import com.onepop.api.setting.value.ValueNumber;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Elytra Flight", tag = "ElytraFlight", description = "A better fly to elytra.", category = ModuleCategory.PLAYER)
public class ModuleElytraFlight extends Module
{
    public static ValueBoolean settingMovesDirection;
    public static ValueNumber settingSpeedIncrease;
    public static ValueNumber settingSpeedIncreaseY;
    public static ValueEnum settingMode;
    private boolean isElytraEquipped;
    private boolean isPlayerFlying;
    private boolean preJumpPressed;
    
    @Override
    public void onSetting() {
        ModuleElytraFlight.settingSpeedIncreaseY.setEnabled(ModuleElytraFlight.settingMode.getValue() == Mode.STATIC);
    }
    
    @Listener
    public void onRunTick(final RunTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        if (!(this.isElytraEquipped = (SlotUtil.getArmourItem(2) == Items.ELYTRA))) {
            this.isPlayerFlying = false;
            return;
        }
        if (ModuleElytraFlight.mc.player.fallDistance > 0.0f && !ModuleElytraFlight.mc.player.onGround && !ModuleElytraFlight.mc.player.isElytraFlying()) {
            KeyUtil.press(ModuleElytraFlight.mc.gameSettings.keyBindJump, true);
            this.preJumpPressed = true;
        }
        else if (this.preJumpPressed) {
            KeyUtil.press(ModuleElytraFlight.mc.gameSettings.keyBindJump, false);
            this.preJumpPressed = false;
        }
        this.isPlayerFlying = ModuleElytraFlight.mc.player.isElytraFlying();
    }
    
    @Listener
    public void onMove(final PlayerMoveEvent event) {
        if (event.getType() != MoverType.SELF) {
            return;
        }
        if (!this.isElytraEquipped) {
            return;
        }
        if (!this.isPlayerFlying) {
            return;
        }
        final float speed = 1.190259f + ModuleElytraFlight.settingSpeedIncrease.getValue().intValue() / 100.0f;
        float playerRotationYaw = ISLClass.mc.player.rotationYaw;
        final float playerRotationPitch = ISLClass.mc.player.rotationPitch;
        float playerForward = ISLClass.mc.player.movementInput.field_192832_b;
        float playerStrafe = ISLClass.mc.player.movementInput.moveStrafe;
        if (ModuleElytraFlight.mc.gameSettings.keyBindJump.isKeyDown()) {
            event.y += ((ModuleElytraFlight.settingMode.getValue() == Mode.NORMAL) ? 0.5 : (1.190259f + ModuleElytraFlight.settingSpeedIncreaseY.getValue().intValue() / 100.0f));
        }
        else if (ModuleElytraFlight.mc.gameSettings.keyBindSneak.isKeyDown()) {
            ModuleElytraFlight.mc.player.setSneaking(false);
            event.y -= ((ModuleElytraFlight.settingMode.getValue() == Mode.NORMAL) ? 0.49099984765052795 : (1.190259f + ModuleElytraFlight.settingSpeedIncreaseY.getValue().intValue() / 100.0f));
        }
        else if (playerForward != 0.0f && ModuleElytraFlight.settingMovesDirection.getValue()) {
            event.setY(TurokMath.clamp(((playerForward > 0.0) ? (playerForward - playerRotationPitch) : (playerForward + playerRotationPitch)) / 150.0f, -((ModuleElytraFlight.settingMode.getValue() == Mode.NORMAL) ? 0.49099985f : (1.190259f + ModuleElytraFlight.settingSpeedIncreaseY.getValue().intValue() / 100.0f)), (ModuleElytraFlight.settingMode.getValue() == Mode.NORMAL) ? 0.5f : (1.190259f + ModuleElytraFlight.settingSpeedIncreaseY.getValue().intValue() / 100.0f)));
        }
        else {
            event.setY(0.0);
        }
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
            event.setX(playerForward * speed * Math.cos(Math.toRadians(playerRotationYaw + 90.0f)) + playerStrafe * speed * Math.sin(Math.toRadians(playerRotationYaw + 90.0f)));
            event.setZ(playerForward * speed * Math.sin(Math.toRadians(playerRotationYaw + 90.0f)) - playerStrafe * speed * Math.cos(Math.toRadians(playerRotationYaw + 90.0f)));
        }
    }
    
    static {
        ModuleElytraFlight.settingMovesDirection = new ValueBoolean("Move Direction", "MoveDirection", "Moves by direction.", true);
        ModuleElytraFlight.settingSpeedIncrease = new ValueNumber("Speed Increase", "SpeedIncrease", "Increase speed.", 0, -100, 200);
        ModuleElytraFlight.settingSpeedIncreaseY = new ValueNumber("Speed Increase Y", "Speed Increase Y", "Speed for increase y!", 0, -100, 200);
        ModuleElytraFlight.settingMode = new ValueEnum("Mode", "Mode", "Modes for elytra.", Mode.NORMAL);
    }
    
    public enum Mode
    {
        NORMAL, 
        STATIC;
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.player;

import java.util.Iterator;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.Entity;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import com.onepop.api.ISLClass;
import com.onepop.client.event.client.ClientTickEvent;
import com.onepop.api.util.client.NullUtil;
import me.rina.turok.util.TurokTick;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.setting.value.ValueNumber;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Step", tag = "Step", description = "Step up blocks.", category = ModuleCategory.PLAYER)
public class ModuleStep extends Module
{
    public static ValueNumber settingHeight;
    public static ValueBoolean settingVanilla;
    public static ValueBoolean settingDisable;
    public static ValueNumber settingDelayDisable;
    private final TurokTick tick;
    private boolean counting;
    
    public ModuleStep() {
        this.tick = new TurokTick();
    }
    
    @Override
    public void onSetting() {
        ModuleStep.settingDisable.setEnabled(!ModuleStep.settingVanilla.getValue());
        ModuleStep.settingDelayDisable.setEnabled(ModuleStep.settingDisable.isEnabled() && ModuleStep.settingDisable.getValue());
    }
    
    @Override
    public void onEnable() {
        this.tick.reset();
        this.counting = false;
    }
    
    @Override
    public void onDisable() {
        this.tick.reset();
        this.counting = false;
        if (NullUtil.isPlayer()) {
            return;
        }
        ModuleStep.mc.player.stepHeight = 0.0f;
    }
    
    @Listener
    public void onListen(final ClientTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        if (ModuleStep.settingVanilla.getValue()) {
            ModuleStep.mc.player.stepHeight = (float)ModuleStep.settingHeight.getValue().intValue();
            return;
        }
        if (this.counting && ModuleStep.settingDisable.getValue() && this.tick.isPassedMS((float)ModuleStep.settingDelayDisable.getValue().intValue())) {
            this.setDisabled();
            return;
        }
        if (!ModuleStep.mc.player.isCollidedHorizontally) {
            return;
        }
        if (!ModuleStep.mc.player.onGround || ModuleStep.mc.player.isOnLadder() || ModuleStep.mc.player.isInWater() || ModuleStep.mc.player.isInLava() || ModuleStep.mc.player.movementInput.jump || ModuleStep.mc.player.noClip) {
            return;
        }
        if (ModuleStep.mc.player.field_191988_bg == 0.0f && ModuleStep.mc.player.moveStrafing == 0.0f) {
            return;
        }
        ModuleStep.mc.player.stepHeight = 0.5f;
        final double step = this.getStepHeight();
        if (step < 0.0 || step > 2.0) {
            return;
        }
        if (step == 2.0 && ModuleStep.settingHeight.getValue().intValue() == 2) {
            ISLClass.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ISLClass.mc.player.posX, ISLClass.mc.player.posY + 0.42, ISLClass.mc.player.posZ, ISLClass.mc.player.onGround));
            ISLClass.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ISLClass.mc.player.posX, ISLClass.mc.player.posY + 0.78, ISLClass.mc.player.posZ, ISLClass.mc.player.onGround));
            ISLClass.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ISLClass.mc.player.posX, ISLClass.mc.player.posY + 0.63, ISLClass.mc.player.posZ, ISLClass.mc.player.onGround));
            ISLClass.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ISLClass.mc.player.posX, ISLClass.mc.player.posY + 0.51, ISLClass.mc.player.posZ, ISLClass.mc.player.onGround));
            ISLClass.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ISLClass.mc.player.posX, ISLClass.mc.player.posY + 0.9, ISLClass.mc.player.posZ, ISLClass.mc.player.onGround));
            ISLClass.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ISLClass.mc.player.posX, ISLClass.mc.player.posY + 1.21, ISLClass.mc.player.posZ, ISLClass.mc.player.onGround));
            ISLClass.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ISLClass.mc.player.posX, ISLClass.mc.player.posY + 1.45, ISLClass.mc.player.posZ, ISLClass.mc.player.onGround));
            ISLClass.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ISLClass.mc.player.posX, ISLClass.mc.player.posY + 1.43, ISLClass.mc.player.posZ, ISLClass.mc.player.onGround));
            ISLClass.mc.player.setPosition(ISLClass.mc.player.posX, ISLClass.mc.player.posY + 2.0, ISLClass.mc.player.posZ);
            this.doRefreshCounter();
        }
        if (step == 1.5 && (ModuleStep.settingHeight.getValue().intValue() == 1 || ModuleStep.settingHeight.getValue().intValue() == 2)) {
            ISLClass.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ISLClass.mc.player.posX, ISLClass.mc.player.posY + 0.41999998688698, ISLClass.mc.player.posZ, ISLClass.mc.player.onGround));
            ISLClass.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ISLClass.mc.player.posX, ISLClass.mc.player.posY + 0.7531999805212, ISLClass.mc.player.posZ, ISLClass.mc.player.onGround));
            ISLClass.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ISLClass.mc.player.posX, ISLClass.mc.player.posY + 1.00133597911214, ISLClass.mc.player.posZ, ISLClass.mc.player.onGround));
            ISLClass.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ISLClass.mc.player.posX, ISLClass.mc.player.posY + 1.16610926093821, ISLClass.mc.player.posZ, ISLClass.mc.player.onGround));
            ISLClass.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ISLClass.mc.player.posX, ISLClass.mc.player.posY + 1.24918707874468, ISLClass.mc.player.posZ, ISLClass.mc.player.onGround));
            ISLClass.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ISLClass.mc.player.posX, ISLClass.mc.player.posY + 1.1707870772188, ISLClass.mc.player.posZ, ISLClass.mc.player.onGround));
            ISLClass.mc.player.setPosition(ISLClass.mc.player.posX, ISLClass.mc.player.posY + 1.0, ISLClass.mc.player.posZ);
            this.doRefreshCounter();
        }
        if (step == 1.0 && (ModuleStep.settingHeight.getValue().intValue() == 1 || ModuleStep.settingHeight.getValue().intValue() == 2)) {
            ISLClass.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ISLClass.mc.player.posX, ISLClass.mc.player.posY + 0.41999998688698, ISLClass.mc.player.posZ, ISLClass.mc.player.onGround));
            ISLClass.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ISLClass.mc.player.posX, ISLClass.mc.player.posY + 0.7531999805212, ISLClass.mc.player.posZ, ISLClass.mc.player.onGround));
            ISLClass.mc.player.setPosition(ISLClass.mc.player.posX, ISLClass.mc.player.posY + 1.0, ISLClass.mc.player.posZ);
            this.doRefreshCounter();
        }
    }
    
    public void doRefreshCounter() {
        if (!this.counting && ModuleStep.settingDisable.getValue() && ModuleStep.settingDisable.isEnabled()) {
            this.counting = true;
        }
    }
    
    public double getStepHeight() {
        double h = -1.0;
        final AxisAlignedBB bb = ISLClass.mc.player.getEntityBoundingBox().offset(0.0, 0.05, 0.0).expandXyz(0.05);
        if (!ModuleStep.mc.world.getCollisionBoxes((Entity)ISLClass.mc.player, bb.offset(0.0, 2.0, 0.0)).isEmpty()) {
            return 100.0;
        }
        for (final AxisAlignedBB aabbs : ISLClass.mc.world.getCollisionBoxes((Entity)ModuleStep.mc.player, bb)) {
            if (aabbs.maxY > h) {
                h = aabbs.maxY;
            }
        }
        return h - ModuleStep.mc.player.posY;
    }
    
    static {
        ModuleStep.settingHeight = new ValueNumber("Height", "Height", "Height for step.", 2, 1, 2);
        ModuleStep.settingVanilla = new ValueBoolean("Vanilla", "Vanilla", "Vanilla step.", false);
        ModuleStep.settingDisable = new ValueBoolean("Disable", "Disable", "Automatically disables step.", false);
        ModuleStep.settingDelayDisable = new ValueNumber("Delay Disable", "DelayDisable", "The delay for disable step.", 250, 0, 1000);
    }
}

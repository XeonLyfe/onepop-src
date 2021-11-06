//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.combat;

import com.onepop.client.event.client.RunTickEvent;
import com.onepop.api.util.render.RenderUtil;
import com.onepop.api.util.world.BlockUtil;
import net.minecraft.util.math.Vec3i;
import com.onepop.api.util.world.BlocksUtil;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.network.play.server.SPacketSpawnObject;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import net.minecraft.network.play.client.CPacketPlayer;
import com.onepop.client.event.network.PacketEvent;
import com.onepop.api.util.item.SlotUtil;
import com.onepop.api.util.entity.EntityUtil;
import java.util.Iterator;
import com.onepop.api.util.world.CrystalUtil;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.init.Items;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import com.onepop.api.util.math.PositionUtil;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.entity.Entity;
import com.onepop.Onepop;
import com.onepop.api.util.client.NullUtil;
import java.util.ArrayList;
import java.awt.Color;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.math.BlockPos;
import me.rina.turok.util.TurokTick;
import net.minecraft.client.network.NetHandlerPlayClient;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.setting.value.ValueNumber;
import com.onepop.api.setting.value.ValueEnum;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Auto-Crystal Custom", tag = "AutoCrystalCustom", description = "Automatically puts crystal on enemy and breaks!", category = ModuleCategory.COMBAT)
public class ModuleCustomCrystalAura extends Module
{
    public static ValueEnum settingCategory;
    public static ValueEnum settingSwitch;
    public static ValueNumber settingSwitchHealth;
    public static ValueBoolean settingAntiNaked;
    public static ValueBoolean settingAutoOffhand;
    public static ValueBoolean settingPreserveSuicide;
    public static ValueBoolean settingFacePlaceCalc;
    public static ValueNumber settingFacePlaceHealth;
    public static ValueNumber settingRangeTrace;
    public static ValueNumber settingWall;
    public static ValueBoolean settingNoSoundDelay;
    public static ValueEnum settingMode;
    public static ValueEnum settingTargetMode;
    public static ValueNumber settingTargetSurroundAlpha;
    public static ValueBoolean settingPlace;
    public static ValueNumber settingSelfDamage;
    public static ValueBoolean settingMultiPlace;
    public static ValueBoolean settingPlace113;
    public static ValueNumber settingPlaceDelay;
    public static ValueNumber settingPlaceRange;
    public static ValueEnum settingPlaceCalculateLoop;
    public static ValueEnum settingPlaceLoop;
    public static ValueBoolean settingPlaceSwing;
    public static ValueNumber settingPlaceOffsetY;
    public static ValueBoolean settingRenderDamage;
    public static ValueBoolean settingRenderRGB;
    public static ValueNumber settingRenderRed;
    public static ValueNumber settingRenderGreen;
    public static ValueNumber settingRenderBlue;
    public static ValueNumber settingRenderAlpha;
    public static ValueNumber settingRenderLineAlpha;
    public static ValueBoolean settingBreak;
    public static ValueBoolean settingBreakWithCrystal;
    public static ValueBoolean settingBreakCooldown;
    public static ValueBoolean settingBreakRetrace;
    public static ValueNumber settingBreakRange;
    public static ValueEnum settingBreakMode;
    public static ValueNumber settingBreakDelay;
    public static ValueNumber settingBreakTick;
    public static ValueEnum settingBreakLoop;
    public static ValueEnum settingBreakCalculateLoop;
    public static ValueEnum settingBreakHand;
    public static ValueBoolean settingRenderBreakSwing;
    public static ValueEnum settingBreakPredict;
    private NetHandlerPlayClient packetSender;
    private int delayBreak;
    private int delayPredict;
    private final TurokTick delayPredictTimer;
    private final TurokTick delayPlaceTimer;
    private final TurokTick delayBreakTimer;
    private boolean withCrystal;
    private boolean withOffhand;
    private boolean handlerSwing;
    private boolean offhandModuleNotifier;
    private boolean isTargetSurrounded;
    private int enderCrystalSlot;
    private int yaw;
    private int pitch;
    private boolean forRotate;
    private BlockPos targetPlace;
    private BlockPos lastPlace;
    private EntityEnderCrystal targetCrystal;
    private EntityEnderCrystal lastHit;
    private EntityPlayer targetPlayer;
    private float currentTargetDamage;
    private final List<BlockPos> lastPlaceList;
    private Color outline;
    private Color solid;
    
    public ModuleCustomCrystalAura() {
        this.delayPredictTimer = new TurokTick();
        this.delayPlaceTimer = new TurokTick();
        this.delayBreakTimer = new TurokTick();
        this.lastPlaceList = new ArrayList<BlockPos>();
        this.outline = new Color(255, 255, 255, 255);
        this.solid = new Color(255, 255, 255, 255);
    }
    
    @Override
    public void onSetting() {
        ModuleCustomCrystalAura.settingSwitch.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.MISC);
        ModuleCustomCrystalAura.settingSwitchHealth.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.MISC && ModuleCustomCrystalAura.settingSwitch.getValue() != Switch.NONE);
        ModuleCustomCrystalAura.settingRangeTrace.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.MISC);
        ModuleCustomCrystalAura.settingAutoOffhand.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.MISC);
        ModuleCustomCrystalAura.settingMode.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.MISC && ModuleCustomCrystalAura.settingPlaceLoop.getValue() == ModuleCustomCrystalAura.settingBreakLoop.getValue());
        ModuleCustomCrystalAura.settingWall.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.MISC);
        ModuleCustomCrystalAura.settingNoSoundDelay.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.MISC);
        ModuleCustomCrystalAura.settingAntiNaked.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.MISC);
        ModuleCustomCrystalAura.settingFacePlaceHealth.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.MISC);
        ModuleCustomCrystalAura.settingSelfDamage.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.MISC);
        ModuleCustomCrystalAura.settingPreserveSuicide.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.MISC);
        ModuleCustomCrystalAura.settingTargetMode.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.MISC);
        ModuleCustomCrystalAura.settingTargetSurroundAlpha.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.MISC);
        ModuleCustomCrystalAura.settingFacePlaceCalc.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.MISC);
        ModuleCustomCrystalAura.settingPlace.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.PLACE);
        ModuleCustomCrystalAura.settingPlaceDelay.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.PLACE && ModuleCustomCrystalAura.settingPlace.getValue());
        ModuleCustomCrystalAura.settingMultiPlace.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.PLACE && ModuleCustomCrystalAura.settingPlace.getValue());
        ModuleCustomCrystalAura.settingPlace113.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.PLACE && ModuleCustomCrystalAura.settingPlace.getValue());
        ModuleCustomCrystalAura.settingPlaceRange.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.PLACE && ModuleCustomCrystalAura.settingPlace.getValue());
        ModuleCustomCrystalAura.settingPlaceCalculateLoop.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.PLACE && ModuleCustomCrystalAura.settingPlace.getValue());
        ModuleCustomCrystalAura.settingPlaceSwing.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.PLACE && ModuleCustomCrystalAura.settingPlace.getValue());
        ModuleCustomCrystalAura.settingPlaceLoop.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.PLACE && ModuleCustomCrystalAura.settingPlace.getValue());
        ModuleCustomCrystalAura.settingRenderRGB.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.PLACE && ModuleCustomCrystalAura.settingPlace.getValue());
        ModuleCustomCrystalAura.settingRenderRed.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.PLACE && ModuleCustomCrystalAura.settingPlace.getValue());
        ModuleCustomCrystalAura.settingRenderGreen.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.PLACE && ModuleCustomCrystalAura.settingPlace.getValue());
        ModuleCustomCrystalAura.settingRenderBlue.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.PLACE && ModuleCustomCrystalAura.settingPlace.getValue());
        ModuleCustomCrystalAura.settingRenderAlpha.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.PLACE && ModuleCustomCrystalAura.settingPlace.getValue());
        ModuleCustomCrystalAura.settingRenderLineAlpha.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.PLACE && ModuleCustomCrystalAura.settingPlace.getValue());
        ModuleCustomCrystalAura.settingPlaceOffsetY.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.PLACE && ModuleCustomCrystalAura.settingPlace.getValue());
        ModuleCustomCrystalAura.settingRenderDamage.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.PLACE && ModuleCustomCrystalAura.settingPlace.getValue());
        ModuleCustomCrystalAura.settingBreak.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.BREAK);
        ModuleCustomCrystalAura.settingBreakTick.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.BREAK && ModuleCustomCrystalAura.settingBreak.getValue());
        ModuleCustomCrystalAura.settingBreakWithCrystal.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.BREAK && ModuleCustomCrystalAura.settingBreak.getValue());
        ModuleCustomCrystalAura.settingBreakRange.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.BREAK && ModuleCustomCrystalAura.settingBreak.getValue());
        ModuleCustomCrystalAura.settingBreakPredict.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.BREAK && ModuleCustomCrystalAura.settingBreak.getValue());
        ModuleCustomCrystalAura.settingBreakLoop.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.BREAK && ModuleCustomCrystalAura.settingBreak.getValue());
        ModuleCustomCrystalAura.settingBreakCalculateLoop.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.BREAK && ModuleCustomCrystalAura.settingBreak.getValue());
        ModuleCustomCrystalAura.settingRenderBreakSwing.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.BREAK && ModuleCustomCrystalAura.settingBreak.getValue());
        ModuleCustomCrystalAura.settingBreakHand.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.BREAK && ModuleCustomCrystalAura.settingBreak.getValue());
        ModuleCustomCrystalAura.settingBreakCooldown.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.BREAK && ModuleCustomCrystalAura.settingBreak.getValue());
        ModuleCustomCrystalAura.settingBreakRetrace.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.BREAK && ModuleCustomCrystalAura.settingBreak.getValue());
        ModuleCustomCrystalAura.settingBreakMode.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.BREAK && ModuleCustomCrystalAura.settingBreak.getValue());
        ModuleCustomCrystalAura.settingBreakTick.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.BREAK && ModuleCustomCrystalAura.settingBreak.getValue() && ModuleCustomCrystalAura.settingBreakMode.getValue() != BreakDelay.MS);
        ModuleCustomCrystalAura.settingBreakDelay.setEnabled(ModuleCustomCrystalAura.settingCategory.getValue() == Category.BREAK && ModuleCustomCrystalAura.settingBreak.getValue() && ModuleCustomCrystalAura.settingBreakMode.getValue() == BreakDelay.MS);
        if (!NullUtil.isPlayerWorld()) {
            ModuleCustomCrystalAura.settingBreakRange.setMaximum(ModuleCustomCrystalAura.mc.playerController.getBlockReachDistance());
            ModuleCustomCrystalAura.settingPlaceRange.setMaximum(ModuleCustomCrystalAura.mc.playerController.getBlockReachDistance());
            ModuleCustomCrystalAura.settingWall.setMaximum(ModuleCustomCrystalAura.mc.playerController.getBlockReachDistance());
        }
        if (ModuleCustomCrystalAura.settingRenderRGB.getValue()) {
            ModuleCustomCrystalAura.settingRenderRed.setValue(Onepop.getClientEventManager().getCurrentRGBColor()[0]);
            ModuleCustomCrystalAura.settingRenderGreen.setValue(Onepop.getClientEventManager().getCurrentRGBColor()[1]);
            ModuleCustomCrystalAura.settingRenderBlue.setValue(Onepop.getClientEventManager().getCurrentRGBColor()[2]);
        }
        this.solid = new Color(ModuleCustomCrystalAura.settingRenderRed.getValue().intValue(), ModuleCustomCrystalAura.settingRenderGreen.getValue().intValue(), ModuleCustomCrystalAura.settingRenderBlue.getValue().intValue(), ModuleCustomCrystalAura.settingRenderAlpha.getValue().intValue());
        this.outline = new Color(ModuleCustomCrystalAura.settingRenderRed.getValue().intValue(), ModuleCustomCrystalAura.settingRenderGreen.getValue().intValue(), ModuleCustomCrystalAura.settingRenderBlue.getValue().intValue(), ModuleCustomCrystalAura.settingRenderLineAlpha.getValue().intValue());
        if (ModuleCustomCrystalAura.mc.player != null) {
            this.packetSender = ModuleCustomCrystalAura.mc.player.connection;
        }
    }
    
    protected void updateOffhand() {
        if (this.isEnabled() && this.withCrystal && this.withOffhand && ModuleOffhand.settingOffhandMode.getValue() != ModuleOffhand.OffhandMode.CRYSTAL && !this.offhandModuleNotifier) {
            if (ModuleCustomCrystalAura.settingAutoOffhand.getValue()) {
                ModuleOffhand.settingOffhandMode.setValue(ModuleOffhand.OffhandMode.CRYSTAL);
                this.print("Crystal offhand active!");
            }
            else {
                this.print("Check offhand module and set to crystal mode!");
            }
            this.offhandModuleNotifier = true;
        }
    }
    
    protected void updateBreak() {
        if (!ModuleCustomCrystalAura.settingBreak.getValue()) {
            return;
        }
        if (!this.isAcceptedToBreakCrystal()) {
            return;
        }
        switch (ModuleCustomCrystalAura.settingBreakMode.getValue()) {
            case MS: {
                if (this.delayBreakTimer.isPassedMS((float)ModuleCustomCrystalAura.settingBreakDelay.getValue().intValue())) {
                    this.updateCrystalBreak();
                    this.delayBreakTimer.reset();
                    break;
                }
                break;
            }
            case TICK: {
                if (this.delayBreak >= ModuleCustomCrystalAura.settingBreakTick.getValue().intValue()) {
                    this.delayBreak = 0;
                    this.updateCrystalBreak();
                }
                ++this.delayBreak;
                break;
            }
            case FAST: {
                if (this.delayBreak == ModuleCustomCrystalAura.settingBreakTick.getValue().intValue()) {
                    this.updateCrystalBreak();
                    this.delayBreak = 0;
                    break;
                }
                ++this.delayBreak;
                break;
            }
        }
    }
    
    protected void updateCrystalBreak() {
        if (ModuleCustomCrystalAura.settingBreakCalculateLoop.getValue() == Calculate.POLL) {
            this.updateBreakCalculate();
        }
        if (this.targetCrystal != null) {
            final EnumHand currentHand = this.getSwingBreak();
            if (ModuleCustomCrystalAura.settingBreakCooldown.getValue()) {
                ModuleCustomCrystalAura.mc.playerController.attackEntity((EntityPlayer)ModuleCustomCrystalAura.mc.player, (Entity)this.targetCrystal);
            }
            else {
                this.packetSender.sendPacket((Packet)new CPacketUseEntity((Entity)this.targetCrystal));
            }
            if (ModuleCustomCrystalAura.settingRenderBreakSwing.getValue()) {
                ModuleCustomCrystalAura.mc.player.swingArm(currentHand);
            }
            else {
                this.packetSender.sendPacket((Packet)new CPacketAnimation(currentHand));
            }
            if (this.lastPlace != null && PositionUtil.collideBlockPos(this.targetCrystal.getPosition().down(), this.lastPlace)) {
                this.lastPlace = null;
            }
            this.lastHit = this.targetCrystal;
        }
    }
    
    protected void updatePlace() {
        if (!ModuleCustomCrystalAura.settingPlace.getValue()) {
            return;
        }
        if (!this.isWithCrystal()) {
            return;
        }
        final int currentSlot = ModuleCustomCrystalAura.mc.player.inventory.currentItem;
        final int oldSlot = -1;
        if (this.delayPlaceTimer.isPassedMS((float)ModuleCustomCrystalAura.settingPlaceDelay.getValue().intValue())) {
            if (ModuleCustomCrystalAura.settingPlaceCalculateLoop.getValue() == Calculate.POLL) {
                this.updatePlaceCalculate();
            }
            if (this.targetPlace != null) {
                final EnumFacing facing = (ModuleCustomCrystalAura.mc.player.getPositionEyes(1.0f).yCoord >= this.targetPlace.y) ? EnumFacing.UP : EnumFacing.DOWN;
                final EnumHand currentHand = this.withOffhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
                if (ModuleCustomCrystalAura.mc.player.getHeldItemMainhand().getItem() != Items.END_CRYSTAL && !this.withOffhand) {
                    return;
                }
                this.packetSender.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.targetPlace, facing, currentHand, 0.5f, 0.5f, 0.5f));
                if (ModuleCustomCrystalAura.settingPlaceSwing.getValue()) {
                    ModuleCustomCrystalAura.mc.player.swingArm(currentHand);
                }
                else {
                    this.packetSender.sendPacket((Packet)new CPacketAnimation(currentHand));
                }
                if (!this.lastPlaceList.contains(this.targetPlace.up())) {
                    this.lastPlaceList.add(this.targetPlace.up());
                }
                this.lastPlace = this.targetPlace;
                this.delayPlaceTimer.reset();
            }
        }
    }
    
    protected void updateBreakCalculate() {
        if (!ModuleCustomCrystalAura.settingBreak.getValue()) {
            return;
        }
        if (!this.isTargetLivingAndNotNull()) {
            this.targetCrystal = null;
            return;
        }
        if (!this.isAcceptedToBreakCrystal()) {
            this.targetCrystal = null;
            return;
        }
        EntityEnderCrystal bestCrystal = null;
        float bestDamage = 1.0f;
        final int crystals = 0;
        for (final Entity entities : ModuleCustomCrystalAura.mc.world.loadedEntityList) {
            if (entities instanceof EntityEnderCrystal) {
                final float factory = ModuleCustomCrystalAura.mc.player.canEntityBeSeen(entities) ? ModuleCustomCrystalAura.settingBreakRange.getValue().floatValue() : ModuleCustomCrystalAura.settingWall.getValue().floatValue();
                final EntityEnderCrystal entityEnderCrystal = (EntityEnderCrystal)entities;
                if (ModuleCustomCrystalAura.mc.player.getDistanceToEntity(entities) > factory) {
                    continue;
                }
                final float targetDamage = CrystalUtil.calculateDamage(entityEnderCrystal, (Entity)this.targetPlayer);
                if (targetDamage <= bestDamage) {
                    continue;
                }
                if (ModuleCustomCrystalAura.settingBreakRetrace.getValue()) {
                    ModuleCustomCrystalAura.mc.playerController.attackEntity((EntityPlayer)ModuleCustomCrystalAura.mc.player, (Entity)entityEnderCrystal);
                    this.packetSender.sendPacket((Packet)new CPacketAnimation(this.getSwingBreak()));
                }
                bestDamage = targetDamage;
                bestCrystal = entityEnderCrystal;
            }
        }
        this.currentTargetDamage = bestDamage;
        this.targetCrystal = bestCrystal;
    }
    
    protected void updatePlaceCalculate() {
        if (!ModuleCustomCrystalAura.settingPlace.getValue()) {
            return;
        }
        if (!this.isTargetLivingAndNotNull()) {
            this.targetPlace = null;
            return;
        }
        if (!this.isWithCrystal()) {
            this.targetPlace = null;
            return;
        }
        BlockPos bestPlace = null;
        float bestDamage = 1.0f;
        for (final BlockPos positions : CrystalUtil.getSphereCrystalPlace(ModuleCustomCrystalAura.settingPlaceRange.getValue().floatValue(), ModuleCustomCrystalAura.settingPlace113.getValue(), !ModuleCustomCrystalAura.settingMultiPlace.getValue())) {
            final float trace = ModuleCustomCrystalAura.settingRangeTrace.getValue().floatValue() * ModuleCustomCrystalAura.settingRangeTrace.getValue().floatValue();
            if (this.targetPlayer.getDistanceSq(positions) >= trace) {
                continue;
            }
            if ((this.isTargetSurrounded || (CrystalUtil.isFacePlace(positions, this.targetPlayer) && ModuleCustomCrystalAura.settingFacePlaceCalc.getValue())) && this.targetPlayer.getHealth() > ModuleCustomCrystalAura.settingFacePlaceHealth.getValue().floatValue()) {
                return;
            }
            final float targetDamage = CrystalUtil.calculateDamage(positions, (Entity)this.targetPlayer);
            if (targetDamage <= bestDamage) {
                continue;
            }
            final float self = CrystalUtil.calculateDamage(positions, (Entity)ModuleCustomCrystalAura.mc.player);
            if (ModuleCustomCrystalAura.settingPreserveSuicide.getValue()) {
                if (CrystalUtil.isSelfDamage(this.targetPlayer, positions, false, ModuleCustomCrystalAura.settingSelfDamage.getValue().intValue())) {
                    continue;
                }
            }
            else if (self > ModuleCustomCrystalAura.settingSelfDamage.getValue().floatValue()) {
                continue;
            }
            bestDamage = targetDamage;
            bestPlace = positions;
        }
        this.targetPlace = bestPlace;
        this.currentTargetDamage = bestDamage;
    }
    
    protected void updateTarget() {
        this.targetPlayer = EntityUtil.getTarget(ModuleCustomCrystalAura.settingRangeTrace.getValue().floatValue(), ModuleCustomCrystalAura.settingTargetMode.getValue() == TargetMode.UNSAFE, ModuleCustomCrystalAura.settingAntiNaked.getValue());
        if (this.targetPlayer != null) {
            this.isTargetSurrounded = EntityUtil.isEntityPlayerSurrounded(this.targetPlayer);
        }
    }
    
    protected void updateHand() {
        this.withOffhand = (ModuleCustomCrystalAura.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL);
        this.withCrystal = (this.withOffhand || ModuleCustomCrystalAura.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL || (this.targetPlayer != null && this.targetPlayer.getHealth() <= ModuleCustomCrystalAura.settingSwitchHealth.getValue().intValue() && SlotUtil.findItemSlotFromHotBar(Items.END_CRYSTAL) != -1 && ModuleCustomCrystalAura.settingSwitch.getValue() == Switch.SWAP));
        if (!this.withCrystal && ModuleCustomCrystalAura.settingSwitch.getValue() == Switch.NORMAL) {
            this.enderCrystalSlot = SlotUtil.findItemSlotFromHotBar(Items.END_CRYSTAL);
            if (this.enderCrystalSlot != -1 && this.targetPlayer != null && this.targetPlayer.getHealth() <= ModuleCustomCrystalAura.settingSwitchHealth.getValue().intValue()) {
                SlotUtil.setCurrentItem(this.enderCrystalSlot);
            }
        }
    }
    
    protected void updateAll(final boolean isWorld) {
        if (!this.isTargetLivingAndNotNull()) {
            return;
        }
        final Calculate calculate = isWorld ? Calculate.WORLD : Calculate.TICK;
        final Loop tick = isWorld ? Loop.WORLD : Loop.TICK;
        this.updatePlaceCalculate();
        this.updateBreakCalculate();
        if (this.targetPlace != null) {
            this.packetSender.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.targetPlace, EnumFacing.UP, this.withOffhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.5f, 0.5f, 0.5f));
        }
        if (this.targetCrystal != null) {
            this.packetSender.sendPacket((Packet)new CPacketUseEntity((Entity)this.targetCrystal));
        }
    }
    
    public void doProcess() {
        switch (ModuleCustomCrystalAura.settingMode.getValue()) {
            case BREAKPLACE: {
                this.updateBreak();
                this.updatePlace();
                break;
            }
            case PLACEBREAK: {
                this.updatePlace();
                this.updateBreak();
                break;
            }
        }
    }
    
    public void doClear() {
        this.delayBreak = 0;
        this.delayPredict = 0;
        this.delayPredictTimer.reset();
        this.withCrystal = false;
        this.withOffhand = false;
        this.enderCrystalSlot = -1;
        this.targetPlace = null;
        this.lastPlace = null;
        this.targetCrystal = null;
        this.lastHit = null;
        this.forRotate = false;
        this.lastPlaceList.clear();
        this.delayPlaceTimer.reset();
        this.isTargetSurrounded = false;
    }
    
    public EnumHand getSwingBreak() {
        EnumHand swing = ((BreakHand)ModuleCustomCrystalAura.settingBreakHand.getValue()).getHand();
        if (ModuleCustomCrystalAura.settingBreakHand.getValue() == BreakHand.AUTO) {
            swing = (this.withOffhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
        }
        else if (ModuleCustomCrystalAura.settingBreakHand.getValue() == BreakHand.HANDLER) {
            swing = (this.handlerSwing ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
            this.handlerSwing = !this.handlerSwing;
        }
        return swing;
    }
    
    public boolean isTargetLivingAndNotNull() {
        return this.targetPlayer != null && this.targetPlayer.isEntityAlive() && this.targetPlayer.getHealth() > 0.0f;
    }
    
    public boolean isWithCrystal() {
        return ModuleCustomCrystalAura.settingPlace.getValue() && this.withCrystal;
    }
    
    public boolean isAcceptedToBreakCrystal() {
        return ModuleCustomCrystalAura.settingBreak.getValue() && (this.withCrystal || !ModuleCustomCrystalAura.settingBreakWithCrystal.getValue());
    }
    
    @Listener
    public void onReceivePacket(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer && this.forRotate) {
            final CPacketPlayer packet = (CPacketPlayer)event.getPacket();
            packet.yaw = (float)this.yaw;
            packet.pitch = (float)this.pitch;
            this.forRotate = false;
        }
    }
    
    @Listener
    public void onReceivePacket(final PacketEvent.Receive event) {
        Label_0215: {
            if (event.getPacket() instanceof SPacketSpawnObject && ModuleCustomCrystalAura.settingBreak.getValue() && ModuleCustomCrystalAura.settingBreakPredict.getValue() != Predict.NORMAL) {
                final SPacketSpawnObject spacketSpawnObject = (SPacketSpawnObject)event.getPacket();
                if (spacketSpawnObject.getType() != 51) {
                    return;
                }
                if (ModuleCustomCrystalAura.settingBreakMode.getValue() == BreakDelay.MS) {
                    if (!this.delayPredictTimer.isPassedMS((float)ModuleCustomCrystalAura.settingBreakDelay.getValue().intValue())) {
                        break Label_0215;
                    }
                }
                else if (this.delayBreak < ModuleCustomCrystalAura.settingBreakTick.getValue().intValue()) {
                    break Label_0215;
                }
                final BlockPos position = new BlockPos(spacketSpawnObject.getX(), spacketSpawnObject.getY(), spacketSpawnObject.getZ());
                boolean predicated = false;
                if (this.lastPlaceList.contains(position)) {
                    predicated = true;
                }
                if (predicated) {
                    final CPacketUseEntity packet = new CPacketUseEntity();
                    packet.entityId = spacketSpawnObject.getEntityID();
                    packet.action = CPacketUseEntity.Action.ATTACK;
                    this.packetSender.sendPacket((Packet)packet);
                    this.packetSender.sendPacket((Packet)new CPacketAnimation(this.getSwingBreak()));
                    this.lastPlaceList.remove(position);
                    this.delayPredictTimer.reset();
                }
            }
        }
        if (event.getPacket() instanceof SPacketSoundEffect && ModuleCustomCrystalAura.settingNoSoundDelay.getValue()) {
            final SPacketSoundEffect packet2 = (SPacketSoundEffect)event.getPacket();
            if (packet2.getCategory() == SoundCategory.BLOCKS && packet2.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                for (final Entity entities : ModuleCustomCrystalAura.mc.world.loadedEntityList) {
                    if (entities instanceof EntityEnderCrystal && entities.getDistance(packet2.getX(), packet2.getY(), packet2.getZ()) <= 6.0) {
                        entities.setDead();
                    }
                }
            }
        }
    }
    
    @Override
    public void onDisable() {
        this.lastPlaceList.clear();
        this.offhandModuleNotifier = false;
        this.doClear();
    }
    
    @Override
    public void onEnable() {
        this.lastPlaceList.clear();
        this.offhandModuleNotifier = false;
        this.doClear();
    }
    
    @Override
    public void onRender3D() {
        if (NullUtil.isPlayerWorld() || this.packetSender == null) {
            return;
        }
        if (this.targetPlayer != null && ModuleCustomCrystalAura.settingTargetSurroundAlpha.getValue().intValue() != 0) {
            final Color colorSafe = new Color(this.isTargetSurrounded ? 0 : 255, 0, this.isTargetSurrounded ? 255 : 0, ModuleCustomCrystalAura.settingTargetSurroundAlpha.getValue().intValue());
            for (final BlockPos add : BlocksUtil.FULL_SURROUND) {
                final BlockPos offset = new BlockPos(Math.floor(this.targetPlayer.posX), Math.floor(this.targetPlayer.posY), Math.floor(this.targetPlayer.posZ)).add((Vec3i)add);
                if (!BlockUtil.isAir(offset)) {
                    RenderUtil.drawSolidBlock(this.camera, offset, colorSafe);
                }
            }
        }
        if (this.targetPlace != null) {
            final float offset2 = ModuleCustomCrystalAura.settingPlaceOffsetY.getValue().intValue() / 100.0f;
            RenderUtil.drawSolidBlock(this.camera, this.targetPlace.x, this.targetPlace.y, this.targetPlace.z, 1.0, offset2, 1.0, this.solid);
            RenderUtil.drawOutlineBlock(this.camera, this.targetPlace.x, this.targetPlace.y, this.targetPlace.z, 1.0, offset2, 1.0, this.outline);
            if (ModuleCustomCrystalAura.settingRenderDamage.getValue()) {
                RenderUtil.drawText(this.targetPlace, "" + this.currentTargetDamage);
            }
        }
    }
    
    @Listener
    public void onTick(final RunTickEvent event) {
        if (NullUtil.isPlayerWorld() || this.packetSender == null) {
            return;
        }
        this.updateOffhand();
        this.updateHand();
        this.updateTarget();
        if (!this.isTargetLivingAndNotNull()) {
            this.doClear();
        }
        this.updateAll(false);
    }
    
    static {
        ModuleCustomCrystalAura.settingCategory = new ValueEnum("", "Category", "Category of auto crystals.", Category.MISC);
        ModuleCustomCrystalAura.settingSwitch = new ValueEnum("Switch", "Switch", "Switchs crystal!", Switch.NORMAL);
        ModuleCustomCrystalAura.settingSwitchHealth = new ValueNumber("Switch Target Health", "SwitchTargetHealth", "Switchs verifying the health target!", 20, 1, 20);
        ModuleCustomCrystalAura.settingAntiNaked = new ValueBoolean("Anti-Naked", "AntiNaked", "Verify if players is with armor to get target.", true);
        ModuleCustomCrystalAura.settingAutoOffhand = new ValueBoolean("Auto-Offhand", "AutoOffhand", "Automatically set offhand crystal if is needed!", false);
        ModuleCustomCrystalAura.settingPreserveSuicide = new ValueBoolean("Preserve Suicide", "PreserveSuicide", "Anti suicide!", true);
        ModuleCustomCrystalAura.settingFacePlaceCalc = new ValueBoolean("Face Place Calc.", "FacePlaceCalculate", "Better calculations to faceplace! if you likes!", false);
        ModuleCustomCrystalAura.settingFacePlaceHealth = new ValueNumber("Face Place Health", "FacePlaceHealth", "Life for start face place.", 20.0f, 1.0f, 20.0f);
        ModuleCustomCrystalAura.settingRangeTrace = new ValueNumber("Range Trace", "RangeTrace", "Maximum distance to get a target and calculate places/break.", 13.0f, 1.0f, 13.0f);
        ModuleCustomCrystalAura.settingWall = new ValueNumber("Wall Range", "WallRange", "Wall ranges...", 4.3f, 1.0f, 6.0f);
        ModuleCustomCrystalAura.settingNoSoundDelay = new ValueBoolean("No Sound Delay", "NoSoundDelay", "Sets dead before all client side confirm! (its already dead, client just remove the delay of the action!)", false);
        ModuleCustomCrystalAura.settingMode = new ValueEnum("Mode", "Mode", "Modes for process crystals actions in auto crystal.", ProcessMode.BREAKPLACE);
        ModuleCustomCrystalAura.settingTargetMode = new ValueEnum("Target Mode", "TargetMode", "Modes to get target.", TargetMode.UNSAFE);
        ModuleCustomCrystalAura.settingTargetSurroundAlpha = new ValueNumber("Target Surround Alpha", "TargetSurroundAlpha", "Renders surround from target!", 100, 0, 255);
        ModuleCustomCrystalAura.settingPlace = new ValueBoolean("Place", "Place", "Enables places crystal around target.", true);
        ModuleCustomCrystalAura.settingSelfDamage = new ValueNumber("Self Damage", "SelfDamage", "Maximum self damage.", 9, 1, 36);
        ModuleCustomCrystalAura.settingMultiPlace = new ValueBoolean("Multi-Place", "MultiPlace", "Multi places crystal.", false);
        ModuleCustomCrystalAura.settingPlace113 = new ValueBoolean("Place 1x1x1", "Place1x1x1", "For new Minecraft version you can place crystals at 1 block height.", false);
        ModuleCustomCrystalAura.settingPlaceDelay = new ValueNumber("Place Delay", "PlaceDelay", "Delay for place.", 50, 0, 50);
        ModuleCustomCrystalAura.settingPlaceRange = new ValueNumber("Place Range", "PlaceRange", "Maximum range for place crystals around target.", 4.3f, 1.0f, 6.0f);
        ModuleCustomCrystalAura.settingPlaceCalculateLoop = new ValueEnum("Place Calc. Loop", "PlaceCalculateLoop", "Sets when you need calculate the place.", Calculate.TICK);
        ModuleCustomCrystalAura.settingPlaceLoop = new ValueEnum("Place Loop", "PlaceLoop", "Sets custom loop for place.", Loop.WORLD);
        ModuleCustomCrystalAura.settingPlaceSwing = new ValueBoolean("Render Place Swing", "RenderPlaceSwing", "Render swing when you places an crystal.", true);
        ModuleCustomCrystalAura.settingPlaceOffsetY = new ValueNumber("Render Offset Y", "RenderOffsetY", "Render block height!", 100, 0, 100);
        ModuleCustomCrystalAura.settingRenderDamage = new ValueBoolean("Render Damage", "RenderDamage", "Render current target damage place!", false);
        ModuleCustomCrystalAura.settingRenderRGB = new ValueBoolean("Render RGB", "RenderRGB", "Render RGB effect.", false);
        ModuleCustomCrystalAura.settingRenderRed = new ValueNumber("Render Red", "RenderRed", "Render places color red.", 190, 0, 255);
        ModuleCustomCrystalAura.settingRenderGreen = new ValueNumber("Render Green", "RenderGreen", "Render places color green.", 190, 0, 255);
        ModuleCustomCrystalAura.settingRenderBlue = new ValueNumber("Render Blue", "RenderBlue", "Render places color blue.", 0, 0, 255);
        ModuleCustomCrystalAura.settingRenderAlpha = new ValueNumber("Render Alpha", "RenderAlpha", "Renders alpha, 0 is disabled.", 100, 0, 255);
        ModuleCustomCrystalAura.settingRenderLineAlpha = new ValueNumber("Render Line Alpha", "RenderLineAlpha", "Render lines alpha.", 200, 0, 255);
        ModuleCustomCrystalAura.settingBreak = new ValueBoolean("Break", "Break", "Enables to break crystal on area.", true);
        ModuleCustomCrystalAura.settingBreakWithCrystal = new ValueBoolean("Break With Crystal", "BreakWithCrystal", "Only break if you are with crystal at both hands.", true);
        ModuleCustomCrystalAura.settingBreakCooldown = new ValueBoolean("Break Cooldown", "BreakCooldown", "Sync breaks with cooldown.", true);
        ModuleCustomCrystalAura.settingBreakRetrace = new ValueBoolean("Break Retrace", "BreakRetrace", "Retrace for first who breaks!", false);
        ModuleCustomCrystalAura.settingBreakRange = new ValueNumber("Break Range", "BreakRange", "The maximum ranges to break crystal on area.", 4.3f, 1.0f, 6.0f);
        ModuleCustomCrystalAura.settingBreakMode = new ValueEnum("Break Delay Mode", "BreakDelayMode", "Modes for delay of break crystals.", BreakDelay.TICK);
        ModuleCustomCrystalAura.settingBreakDelay = new ValueNumber("Break Delay", "Break Delay", "Break delay MS.", 50, 0, 50);
        ModuleCustomCrystalAura.settingBreakTick = new ValueNumber("Break Tick", "BreakTick", "Break tick.", 0, 0, 30);
        ModuleCustomCrystalAura.settingBreakLoop = new ValueEnum("Break Loop", "BreakLoop", "Sets custom loop for break crystals.", Loop.TICK);
        ModuleCustomCrystalAura.settingBreakCalculateLoop = new ValueEnum("Break Calc. Loop", "BreakCalculateLoop", "Sets when you need calc the break.", Calculate.TICK);
        ModuleCustomCrystalAura.settingBreakHand = new ValueEnum("Break Hand", "BreakHand", "The hand for breaks crystal.", BreakHand.AUTO);
        ModuleCustomCrystalAura.settingRenderBreakSwing = new ValueBoolean("Render Break Swing", "RenderBreakSwing", "Render the swing break.", true);
        ModuleCustomCrystalAura.settingBreakPredict = new ValueEnum("Break Predict", "BreakPredict", "Predict break.", Predict.INSANE);
    }
    
    public enum Category
    {
        MISC, 
        PLACE, 
        BREAK;
    }
    
    public enum BreakDelay
    {
        MS, 
        TICK, 
        FAST;
    }
    
    public enum Switch
    {
        SWAP, 
        NORMAL, 
        NONE;
    }
    
    public enum TargetMode
    {
        UNSAFE, 
        CLOSET;
    }
    
    public enum Loop
    {
        TICK, 
        WORLD;
    }
    
    public enum Calculate
    {
        TICK, 
        WORLD, 
        POLL;
    }
    
    public enum Predict
    {
        NORMAL, 
        INSANE, 
        NONE;
    }
    
    public enum ProcessMode
    {
        BREAKPLACE, 
        PLACEBREAK;
    }
    
    public enum BreakHand
    {
        AUTO((EnumHand)null), 
        HANDLER((EnumHand)null), 
        MAINHAND(EnumHand.MAIN_HAND), 
        OFFHAND(EnumHand.OFF_HAND);
        
        private final EnumHand hand;
        
        private BreakHand(final EnumHand hand) {
            this.hand = hand;
        }
        
        public EnumHand getHand() {
            return this.hand;
        }
    }
}

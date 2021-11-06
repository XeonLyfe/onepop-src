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

@Registry(name = "Auto-Crystal", tag = "AutoCrystal", description = "Automatically puts crystal on enemy and breaks!", category = ModuleCategory.COMBAT)
public class ModuleAutoCrystal extends Module
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
    
    public ModuleAutoCrystal() {
        this.delayPredictTimer = new TurokTick();
        this.delayPlaceTimer = new TurokTick();
        this.delayBreakTimer = new TurokTick();
        this.lastPlaceList = new ArrayList<BlockPos>();
        this.outline = new Color(255, 255, 255, 255);
        this.solid = new Color(255, 255, 255, 255);
    }
    
    @Override
    public void onSetting() {
        ModuleAutoCrystal.settingSwitch.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.MISC);
        ModuleAutoCrystal.settingSwitchHealth.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.MISC && ModuleAutoCrystal.settingSwitch.getValue() != Switch.NONE);
        ModuleAutoCrystal.settingRangeTrace.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.MISC);
        ModuleAutoCrystal.settingAutoOffhand.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.MISC);
        ModuleAutoCrystal.settingMode.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.MISC && ModuleAutoCrystal.settingPlaceLoop.getValue() == ModuleAutoCrystal.settingBreakLoop.getValue());
        ModuleAutoCrystal.settingWall.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.MISC);
        ModuleAutoCrystal.settingNoSoundDelay.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.MISC);
        ModuleAutoCrystal.settingAntiNaked.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.MISC);
        ModuleAutoCrystal.settingFacePlaceHealth.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.MISC);
        ModuleAutoCrystal.settingSelfDamage.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.MISC);
        ModuleAutoCrystal.settingPreserveSuicide.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.MISC);
        ModuleAutoCrystal.settingTargetMode.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.MISC);
        ModuleAutoCrystal.settingTargetSurroundAlpha.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.MISC);
        ModuleAutoCrystal.settingFacePlaceCalc.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.MISC);
        ModuleAutoCrystal.settingPlace.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.PLACE);
        ModuleAutoCrystal.settingPlaceDelay.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.PLACE && ModuleAutoCrystal.settingPlace.getValue());
        ModuleAutoCrystal.settingMultiPlace.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.PLACE && ModuleAutoCrystal.settingPlace.getValue());
        ModuleAutoCrystal.settingPlace113.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.PLACE && ModuleAutoCrystal.settingPlace.getValue());
        ModuleAutoCrystal.settingPlaceRange.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.PLACE && ModuleAutoCrystal.settingPlace.getValue());
        ModuleAutoCrystal.settingPlaceCalculateLoop.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.PLACE && ModuleAutoCrystal.settingPlace.getValue());
        ModuleAutoCrystal.settingPlaceSwing.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.PLACE && ModuleAutoCrystal.settingPlace.getValue());
        ModuleAutoCrystal.settingPlaceLoop.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.PLACE && ModuleAutoCrystal.settingPlace.getValue());
        ModuleAutoCrystal.settingRenderRGB.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.PLACE && ModuleAutoCrystal.settingPlace.getValue());
        ModuleAutoCrystal.settingRenderRed.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.PLACE && ModuleAutoCrystal.settingPlace.getValue());
        ModuleAutoCrystal.settingRenderGreen.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.PLACE && ModuleAutoCrystal.settingPlace.getValue());
        ModuleAutoCrystal.settingRenderBlue.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.PLACE && ModuleAutoCrystal.settingPlace.getValue());
        ModuleAutoCrystal.settingRenderAlpha.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.PLACE && ModuleAutoCrystal.settingPlace.getValue());
        ModuleAutoCrystal.settingRenderLineAlpha.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.PLACE && ModuleAutoCrystal.settingPlace.getValue());
        ModuleAutoCrystal.settingPlaceOffsetY.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.PLACE && ModuleAutoCrystal.settingPlace.getValue());
        ModuleAutoCrystal.settingRenderDamage.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.PLACE && ModuleAutoCrystal.settingPlace.getValue());
        ModuleAutoCrystal.settingBreak.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.BREAK);
        ModuleAutoCrystal.settingBreakTick.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.BREAK && ModuleAutoCrystal.settingBreak.getValue());
        ModuleAutoCrystal.settingBreakWithCrystal.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.BREAK && ModuleAutoCrystal.settingBreak.getValue());
        ModuleAutoCrystal.settingBreakRange.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.BREAK && ModuleAutoCrystal.settingBreak.getValue());
        ModuleAutoCrystal.settingBreakPredict.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.BREAK && ModuleAutoCrystal.settingBreak.getValue());
        ModuleAutoCrystal.settingBreakLoop.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.BREAK && ModuleAutoCrystal.settingBreak.getValue());
        ModuleAutoCrystal.settingBreakCalculateLoop.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.BREAK && ModuleAutoCrystal.settingBreak.getValue());
        ModuleAutoCrystal.settingRenderBreakSwing.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.BREAK && ModuleAutoCrystal.settingBreak.getValue());
        ModuleAutoCrystal.settingBreakHand.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.BREAK && ModuleAutoCrystal.settingBreak.getValue());
        ModuleAutoCrystal.settingBreakCooldown.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.BREAK && ModuleAutoCrystal.settingBreak.getValue());
        ModuleAutoCrystal.settingBreakRetrace.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.BREAK && ModuleAutoCrystal.settingBreak.getValue());
        ModuleAutoCrystal.settingBreakMode.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.BREAK && ModuleAutoCrystal.settingBreak.getValue());
        ModuleAutoCrystal.settingBreakTick.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.BREAK && ModuleAutoCrystal.settingBreak.getValue() && ModuleAutoCrystal.settingBreakMode.getValue() != BreakDelay.MS);
        ModuleAutoCrystal.settingBreakDelay.setEnabled(ModuleAutoCrystal.settingCategory.getValue() == Category.BREAK && ModuleAutoCrystal.settingBreak.getValue() && ModuleAutoCrystal.settingBreakMode.getValue() == BreakDelay.MS);
        if (!NullUtil.isPlayerWorld()) {
            ModuleAutoCrystal.settingBreakRange.setMaximum(ModuleAutoCrystal.mc.playerController.getBlockReachDistance());
            ModuleAutoCrystal.settingPlaceRange.setMaximum(ModuleAutoCrystal.mc.playerController.getBlockReachDistance());
            ModuleAutoCrystal.settingWall.setMaximum(ModuleAutoCrystal.mc.playerController.getBlockReachDistance());
        }
        if (ModuleAutoCrystal.settingRenderRGB.getValue()) {
            ModuleAutoCrystal.settingRenderRed.setValue(Onepop.getClientEventManager().getCurrentRGBColor()[0]);
            ModuleAutoCrystal.settingRenderGreen.setValue(Onepop.getClientEventManager().getCurrentRGBColor()[1]);
            ModuleAutoCrystal.settingRenderBlue.setValue(Onepop.getClientEventManager().getCurrentRGBColor()[2]);
        }
        this.solid = new Color(ModuleAutoCrystal.settingRenderRed.getValue().intValue(), ModuleAutoCrystal.settingRenderGreen.getValue().intValue(), ModuleAutoCrystal.settingRenderBlue.getValue().intValue(), ModuleAutoCrystal.settingRenderAlpha.getValue().intValue());
        this.outline = new Color(ModuleAutoCrystal.settingRenderRed.getValue().intValue(), ModuleAutoCrystal.settingRenderGreen.getValue().intValue(), ModuleAutoCrystal.settingRenderBlue.getValue().intValue(), ModuleAutoCrystal.settingRenderLineAlpha.getValue().intValue());
        if (ModuleAutoCrystal.mc.player != null) {
            this.packetSender = ModuleAutoCrystal.mc.player.connection;
        }
    }
    
    protected void updateOffhand() {
        if (this.isEnabled() && this.withCrystal && this.withOffhand && ModuleOffhand.settingOffhandMode.getValue() != ModuleOffhand.OffhandMode.CRYSTAL && !this.offhandModuleNotifier) {
            if (ModuleAutoCrystal.settingAutoOffhand.getValue()) {
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
        if (!ModuleAutoCrystal.settingBreak.getValue()) {
            return;
        }
        if (!this.isAcceptedToBreakCrystal()) {
            return;
        }
        switch (ModuleAutoCrystal.settingBreakMode.getValue()) {
            case MS: {
                if (this.delayBreakTimer.isPassedMS((float)ModuleAutoCrystal.settingBreakDelay.getValue().intValue())) {
                    this.updateCrystalBreak();
                    this.delayBreakTimer.reset();
                    break;
                }
                break;
            }
            case TICK: {
                if (this.delayBreak >= ModuleAutoCrystal.settingBreakTick.getValue().intValue()) {
                    this.delayBreak = 0;
                    this.updateCrystalBreak();
                }
                ++this.delayBreak;
                break;
            }
            case FAST: {
                if (this.delayBreak == ModuleAutoCrystal.settingBreakTick.getValue().intValue()) {
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
        if (ModuleAutoCrystal.settingBreakCalculateLoop.getValue() == Calculate.POLL) {
            this.updateBreakCalculate();
        }
        if (this.targetCrystal != null) {
            final EnumHand currentHand = this.getSwingBreak();
            if (ModuleAutoCrystal.settingBreakCooldown.getValue()) {
                ModuleAutoCrystal.mc.playerController.attackEntity((EntityPlayer)ModuleAutoCrystal.mc.player, (Entity)this.targetCrystal);
            }
            else {
                this.packetSender.sendPacket((Packet)new CPacketUseEntity((Entity)this.targetCrystal));
            }
            if (ModuleAutoCrystal.settingRenderBreakSwing.getValue()) {
                ModuleAutoCrystal.mc.player.swingArm(currentHand);
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
        if (!ModuleAutoCrystal.settingPlace.getValue()) {
            return;
        }
        if (!this.isWithCrystal()) {
            return;
        }
        final int currentSlot = ModuleAutoCrystal.mc.player.inventory.currentItem;
        final int oldSlot = -1;
        if (this.delayPlaceTimer.isPassedMS((float)ModuleAutoCrystal.settingPlaceDelay.getValue().intValue())) {
            if (ModuleAutoCrystal.settingPlaceCalculateLoop.getValue() == Calculate.POLL) {
                this.updatePlaceCalculate();
            }
            if (this.targetPlace != null) {
                final EnumFacing facing = (ModuleAutoCrystal.mc.player.getPositionEyes(1.0f).yCoord >= this.targetPlace.y) ? EnumFacing.UP : EnumFacing.DOWN;
                final EnumHand currentHand = this.withOffhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
                if (ModuleAutoCrystal.mc.player.getHeldItemMainhand().getItem() != Items.END_CRYSTAL && !this.withOffhand) {
                    return;
                }
                this.packetSender.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.targetPlace, facing, currentHand, 0.5f, 0.5f, 0.5f));
                if (ModuleAutoCrystal.settingPlaceSwing.getValue()) {
                    ModuleAutoCrystal.mc.player.swingArm(currentHand);
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
        if (!ModuleAutoCrystal.settingBreak.getValue()) {
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
        final EntityEnderCrystal bestCrystal = null;
        float bestDamage = 1.0f;
        final int crystals = 0;
        for (final Entity entities : ModuleAutoCrystal.mc.world.loadedEntityList) {
            if (entities instanceof EntityEnderCrystal) {
                final float factory = ModuleAutoCrystal.mc.player.canEntityBeSeen(entities) ? ModuleAutoCrystal.settingBreakRange.getValue().floatValue() : ModuleAutoCrystal.settingWall.getValue().floatValue();
                final EntityEnderCrystal entityEnderCrystal = (EntityEnderCrystal)entities;
                if (ModuleAutoCrystal.mc.player.getDistanceToEntity(entities) > factory) {
                    continue;
                }
                final float targetDamage = CrystalUtil.calculateDamage(entityEnderCrystal, (Entity)this.targetPlayer);
                if (targetDamage <= bestDamage) {
                    continue;
                }
                if (ModuleAutoCrystal.settingBreakRetrace.getValue()) {
                    ModuleAutoCrystal.mc.playerController.attackEntity((EntityPlayer)ModuleAutoCrystal.mc.player, (Entity)entityEnderCrystal);
                    this.packetSender.sendPacket((Packet)new CPacketAnimation(this.getSwingBreak()));
                }
                bestDamage = targetDamage;
                this.targetCrystal = entityEnderCrystal;
            }
        }
        this.targetCrystal = bestCrystal;
    }
    
    protected void updatePlaceCalculate() {
        if (!ModuleAutoCrystal.settingPlace.getValue()) {
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
        for (final BlockPos positions : CrystalUtil.getSphereCrystalPlace(ModuleAutoCrystal.settingPlaceRange.getValue().floatValue(), ModuleAutoCrystal.settingPlace113.getValue(), !ModuleAutoCrystal.settingMultiPlace.getValue())) {
            final float trace = ModuleAutoCrystal.settingRangeTrace.getValue().floatValue() * ModuleAutoCrystal.settingRangeTrace.getValue().floatValue();
            if (this.targetPlayer.getDistanceSq(positions) >= trace) {
                continue;
            }
            if ((this.isTargetSurrounded || (CrystalUtil.isFacePlace(positions, this.targetPlayer) && ModuleAutoCrystal.settingFacePlaceCalc.getValue())) && this.targetPlayer.getHealth() > ModuleAutoCrystal.settingFacePlaceHealth.getValue().floatValue()) {
                return;
            }
            final float targetDamage = CrystalUtil.calculateDamage(positions, (Entity)this.targetPlayer);
            final float selfDamage = CrystalUtil.calculateDamage(positions, (Entity)ModuleAutoCrystal.mc.player);
            if (targetDamage <= bestDamage || selfDamage >= ModuleAutoCrystal.settingSelfDamage.getValue().floatValue()) {
                continue;
            }
            bestDamage = targetDamage;
            bestPlace = positions;
        }
        this.targetPlace = bestPlace;
        this.currentTargetDamage = bestDamage;
    }
    
    protected void updateTarget() {
        this.targetPlayer = EntityUtil.getTarget(ModuleAutoCrystal.settingRangeTrace.getValue().floatValue(), ModuleAutoCrystal.settingTargetMode.getValue() == TargetMode.UNSAFE, ModuleAutoCrystal.settingAntiNaked.getValue());
        if (this.targetPlayer != null) {
            this.isTargetSurrounded = EntityUtil.isEntityPlayerSurrounded(this.targetPlayer);
        }
    }
    
    protected void updateHand() {
        this.withOffhand = (ModuleAutoCrystal.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL);
        this.withCrystal = (this.withOffhand || ModuleAutoCrystal.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL || (this.targetPlayer != null && this.targetPlayer.getHealth() <= ModuleAutoCrystal.settingSwitchHealth.getValue().intValue() && SlotUtil.findItemSlotFromHotBar(Items.END_CRYSTAL) != -1 && ModuleAutoCrystal.settingSwitch.getValue() == Switch.SWAP));
        if (!this.withCrystal && ModuleAutoCrystal.settingSwitch.getValue() == Switch.NORMAL) {
            this.enderCrystalSlot = SlotUtil.findItemSlotFromHotBar(Items.END_CRYSTAL);
            if (this.enderCrystalSlot != -1 && this.targetPlayer != null && this.targetPlayer.getHealth() <= ModuleAutoCrystal.settingSwitchHealth.getValue().intValue()) {
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
        if (ModuleAutoCrystal.settingBreakCalculateLoop.getValue() == calculate && this.isAcceptedToBreakCrystal()) {
            this.updateBreakCalculate();
        }
        if (ModuleAutoCrystal.settingPlaceCalculateLoop.getValue() == calculate) {
            this.updatePlaceCalculate();
        }
        if (ModuleAutoCrystal.settingPlaceLoop.getValue() == tick && ModuleAutoCrystal.settingBreakLoop.getValue() == tick) {
            this.doProcess();
        }
        else {
            if (ModuleAutoCrystal.settingBreakLoop.getValue() == tick && this.isAcceptedToBreakCrystal()) {
                this.updateBreak();
            }
            if (ModuleAutoCrystal.settingPlaceLoop.getValue() == tick) {
                this.updatePlace();
            }
        }
    }
    
    public void doProcess() {
        switch (ModuleAutoCrystal.settingMode.getValue()) {
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
        EnumHand swing = ((BreakHand)ModuleAutoCrystal.settingBreakHand.getValue()).getHand();
        if (ModuleAutoCrystal.settingBreakHand.getValue() == BreakHand.AUTO) {
            swing = (this.withOffhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
        }
        else if (ModuleAutoCrystal.settingBreakHand.getValue() == BreakHand.HANDLER) {
            swing = (this.handlerSwing ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
            this.handlerSwing = !this.handlerSwing;
        }
        return swing;
    }
    
    public boolean isTargetLivingAndNotNull() {
        return this.targetPlayer != null && this.targetPlayer.isEntityAlive() && this.targetPlayer.getHealth() > 0.0f;
    }
    
    public boolean isWithCrystal() {
        return ModuleAutoCrystal.settingPlace.getValue() && this.withCrystal;
    }
    
    public boolean isAcceptedToBreakCrystal() {
        return ModuleAutoCrystal.settingBreak.getValue() && (this.withCrystal || !ModuleAutoCrystal.settingBreakWithCrystal.getValue());
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
            if (event.getPacket() instanceof SPacketSpawnObject && ModuleAutoCrystal.settingBreak.getValue() && ModuleAutoCrystal.settingBreakPredict.getValue() != Predict.NORMAL) {
                final SPacketSpawnObject spacketSpawnObject = (SPacketSpawnObject)event.getPacket();
                if (spacketSpawnObject.getType() != 51) {
                    return;
                }
                if (ModuleAutoCrystal.settingBreakMode.getValue() == BreakDelay.MS) {
                    if (!this.delayPredictTimer.isPassedMS((float)ModuleAutoCrystal.settingBreakDelay.getValue().intValue())) {
                        break Label_0215;
                    }
                }
                else if (this.delayBreak < ModuleAutoCrystal.settingBreakTick.getValue().intValue()) {
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
        if (event.getPacket() instanceof SPacketSoundEffect && ModuleAutoCrystal.settingNoSoundDelay.getValue()) {
            final SPacketSoundEffect packet2 = (SPacketSoundEffect)event.getPacket();
            if (packet2.getCategory() == SoundCategory.BLOCKS && packet2.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                for (final Entity entities : ModuleAutoCrystal.mc.world.loadedEntityList) {
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
        this.updateAll(true);
        if (this.targetPlayer != null && ModuleAutoCrystal.settingTargetSurroundAlpha.getValue().intValue() != 0) {
            final Color colorSafe = new Color(this.isTargetSurrounded ? 0 : 255, 0, this.isTargetSurrounded ? 255 : 0, ModuleAutoCrystal.settingTargetSurroundAlpha.getValue().intValue());
            for (final BlockPos add : BlocksUtil.FULL_SURROUND) {
                final BlockPos offset = new BlockPos(Math.floor(this.targetPlayer.posX), Math.floor(this.targetPlayer.posY), Math.floor(this.targetPlayer.posZ)).add((Vec3i)add);
                if (!BlockUtil.isAir(offset)) {
                    RenderUtil.drawSolidBlock(this.camera, offset, colorSafe);
                }
            }
        }
        if (this.targetPlace != null) {
            final float offset2 = ModuleAutoCrystal.settingPlaceOffsetY.getValue().intValue() / 100.0f;
            RenderUtil.drawSolidBlock(this.camera, this.targetPlace.x, this.targetPlace.y, this.targetPlace.z, 1.0, offset2, 1.0, this.solid);
            RenderUtil.drawOutlineBlock(this.camera, this.targetPlace.x, this.targetPlace.y, this.targetPlace.z, 1.0, offset2, 1.0, this.outline);
            if (ModuleAutoCrystal.settingRenderDamage.getValue()) {
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
        ModuleAutoCrystal.settingCategory = new ValueEnum("", "Category", "Category of auto crystals.", Category.MISC);
        ModuleAutoCrystal.settingSwitch = new ValueEnum("Switch", "Switch", "Switchs crystal!", Switch.NORMAL);
        ModuleAutoCrystal.settingSwitchHealth = new ValueNumber("Switch Target Health", "SwitchTargetHealth", "Switchs verifying the health target!", 20, 1, 20);
        ModuleAutoCrystal.settingAntiNaked = new ValueBoolean("Anti-Naked", "AntiNaked", "Verify if players is with armor to get target.", true);
        ModuleAutoCrystal.settingAutoOffhand = new ValueBoolean("Auto-Offhand", "AutoOffhand", "Automatically set offhand crystal if is needed!", false);
        ModuleAutoCrystal.settingPreserveSuicide = new ValueBoolean("Preserve Suicide", "PreserveSuicide", "Anti suicide!", true);
        ModuleAutoCrystal.settingFacePlaceCalc = new ValueBoolean("Face Place Calc.", "FacePlaceCalculate", "Better calculations to faceplace! if you likes!", false);
        ModuleAutoCrystal.settingFacePlaceHealth = new ValueNumber("Face Place Health", "FacePlaceHealth", "Life for start face place.", 20.0f, 1.0f, 20.0f);
        ModuleAutoCrystal.settingRangeTrace = new ValueNumber("Range Trace", "RangeTrace", "Maximum distance to get a target and calculate places/break.", 13.0f, 1.0f, 13.0f);
        ModuleAutoCrystal.settingWall = new ValueNumber("Wall Range", "WallRange", "Wall ranges...", 4.3f, 1.0f, 6.0f);
        ModuleAutoCrystal.settingNoSoundDelay = new ValueBoolean("No Sound Delay", "NoSoundDelay", "Sets dead before all client side confirm! (its already dead, client just remove the delay of the action!)", false);
        ModuleAutoCrystal.settingMode = new ValueEnum("Mode", "Mode", "Modes for process crystals actions in auto crystal.", ProcessMode.BREAKPLACE);
        ModuleAutoCrystal.settingTargetMode = new ValueEnum("Target Mode", "TargetMode", "Modes to get target.", TargetMode.UNSAFE);
        ModuleAutoCrystal.settingTargetSurroundAlpha = new ValueNumber("Target Surround Alpha", "TargetSurroundAlpha", "Renders surround from target!", 100, 0, 255);
        ModuleAutoCrystal.settingPlace = new ValueBoolean("Place", "Place", "Enables places crystal around target.", true);
        ModuleAutoCrystal.settingSelfDamage = new ValueNumber("Self Damage", "SelfDamage", "Maximum self damage.", 9, 1, 36);
        ModuleAutoCrystal.settingMultiPlace = new ValueBoolean("Multi-Place", "MultiPlace", "Multi places crystal.", false);
        ModuleAutoCrystal.settingPlace113 = new ValueBoolean("Place 1x1x1", "Place1x1x1", "For new Minecraft version you can place crystals at 1 block height.", false);
        ModuleAutoCrystal.settingPlaceDelay = new ValueNumber("Place Delay", "PlaceDelay", "Delay for place.", 50, 0, 50);
        ModuleAutoCrystal.settingPlaceRange = new ValueNumber("Place Range", "PlaceRange", "Maximum range for place crystals around target.", 4.3f, 1.0f, 6.0f);
        ModuleAutoCrystal.settingPlaceCalculateLoop = new ValueEnum("Place Calc. Loop", "PlaceCalculateLoop", "Sets when you need calculate the place.", Calculate.TICK);
        ModuleAutoCrystal.settingPlaceLoop = new ValueEnum("Place Loop", "PlaceLoop", "Sets custom loop for place.", Loop.WORLD);
        ModuleAutoCrystal.settingPlaceSwing = new ValueBoolean("Render Place Swing", "RenderPlaceSwing", "Render swing when you places an crystal.", true);
        ModuleAutoCrystal.settingPlaceOffsetY = new ValueNumber("Render Offset Y", "RenderOffsetY", "Render block height!", 100, 0, 100);
        ModuleAutoCrystal.settingRenderDamage = new ValueBoolean("Render Damage", "RenderDamage", "Render current target damage place!", false);
        ModuleAutoCrystal.settingRenderRGB = new ValueBoolean("Render RGB", "RenderRGB", "Render RGB effect.", false);
        ModuleAutoCrystal.settingRenderRed = new ValueNumber("Render Red", "RenderRed", "Render places color red.", 190, 0, 255);
        ModuleAutoCrystal.settingRenderGreen = new ValueNumber("Render Green", "RenderGreen", "Render places color green.", 190, 0, 255);
        ModuleAutoCrystal.settingRenderBlue = new ValueNumber("Render Blue", "RenderBlue", "Render places color blue.", 0, 0, 255);
        ModuleAutoCrystal.settingRenderAlpha = new ValueNumber("Render Alpha", "RenderAlpha", "Renders alpha, 0 is disabled.", 100, 0, 255);
        ModuleAutoCrystal.settingRenderLineAlpha = new ValueNumber("Render Line Alpha", "RenderLineAlpha", "Render lines alpha.", 200, 0, 255);
        ModuleAutoCrystal.settingBreak = new ValueBoolean("Break", "Break", "Enables to break crystal on area.", true);
        ModuleAutoCrystal.settingBreakWithCrystal = new ValueBoolean("Break With Crystal", "BreakWithCrystal", "Only break if you are with crystal at both hands.", true);
        ModuleAutoCrystal.settingBreakCooldown = new ValueBoolean("Break Cooldown", "BreakCooldown", "Sync breaks with cooldown.", true);
        ModuleAutoCrystal.settingBreakRetrace = new ValueBoolean("Break Retrace", "BreakRetrace", "Retrace for first who breaks!", false);
        ModuleAutoCrystal.settingBreakRange = new ValueNumber("Break Range", "BreakRange", "The maximum ranges to break crystal on area.", 4.3f, 1.0f, 6.0f);
        ModuleAutoCrystal.settingBreakMode = new ValueEnum("Break Delay Mode", "BreakDelayMode", "Modes for delay of break crystals.", BreakDelay.TICK);
        ModuleAutoCrystal.settingBreakDelay = new ValueNumber("Break Delay", "Break Delay", "Break delay MS.", 50, 0, 50);
        ModuleAutoCrystal.settingBreakTick = new ValueNumber("Break Tick", "BreakTick", "Break tick.", 0, 0, 30);
        ModuleAutoCrystal.settingBreakLoop = new ValueEnum("Break Loop", "BreakLoop", "Sets custom loop for break crystals.", Loop.TICK);
        ModuleAutoCrystal.settingBreakCalculateLoop = new ValueEnum("Break Calc. Loop", "BreakCalculateLoop", "Sets when you need calc the break.", Calculate.TICK);
        ModuleAutoCrystal.settingBreakHand = new ValueEnum("Break Hand", "BreakHand", "The hand for breaks crystal.", BreakHand.AUTO);
        ModuleAutoCrystal.settingRenderBreakSwing = new ValueBoolean("Render Break Swing", "RenderBreakSwing", "Render the swing break.", true);
        ModuleAutoCrystal.settingBreakPredict = new ValueEnum("Break Predict", "BreakPredict", "Predict break.", Predict.INSANE);
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

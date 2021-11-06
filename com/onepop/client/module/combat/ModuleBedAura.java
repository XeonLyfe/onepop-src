//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.combat;

import com.onepop.api.util.entity.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;
import java.util.Iterator;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumHand;
import com.onepop.client.manager.network.RotationManager;
import com.onepop.api.util.math.RotationUtil;
import com.onepop.api.util.math.PositionUtil;
import net.minecraft.entity.Entity;
import com.onepop.api.util.world.CrystalUtil;
import net.minecraft.init.Blocks;
import com.onepop.api.util.world.BlockUtil;
import com.onepop.api.util.world.BlocksUtil;
import com.onepop.api.util.item.SlotUtil;
import net.minecraft.init.Items;
import com.onepop.api.util.entity.PlayerUtil;
import com.onepop.client.event.client.ClientTickEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import com.onepop.client.event.network.PacketEvent;
import com.onepop.api.util.render.RenderUtil;
import com.onepop.api.util.client.NullUtil;
import com.onepop.Onepop;
import java.awt.Color;
import net.minecraft.util.EnumFacing;
import net.minecraft.entity.player.EntityPlayer;
import java.util.ArrayList;
import me.rina.turok.util.TurokTick;
import net.minecraft.util.math.BlockPos;
import com.onepop.api.tool.CounterTool;
import com.onepop.api.setting.value.ValueEnum;
import com.onepop.api.setting.value.ValueNumber;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Bed Aura", tag = "BedAura", description = "Makes you automatically places and click on bed!", category = ModuleCategory.COMBAT)
public class ModuleBedAura extends Module
{
    public static ValueBoolean settingAntiNaked;
    public static ValueBoolean settingAutoSwitch;
    public static ValueBoolean settingAutoOffhand;
    public static ValueNumber settingRange;
    public static ValueEnum settingTargetMode;
    public static ValueBoolean settingSuicide;
    public static ValueNumber settingSelfDamage;
    public static ValueNumber settingMinimumDamageTarget;
    public static ValueBoolean settingPlace;
    public static ValueBoolean settingPlaceSync;
    public static ValueBoolean settingPlaceWorldTick;
    public static ValueBoolean settingAirPlace;
    public static ValueNumber settingPlaceRange;
    public static ValueNumber settingPlaceDelay;
    public static ValueEnum settingPlaceRotate;
    public static ValueBoolean settingClick;
    public static ValueBoolean settingClickPredict;
    public static ValueBoolean settingClickSync;
    public static ValueBoolean settingClickOnlyWhenEquippedBed;
    public static ValueBoolean settingClickWorldTick;
    public static ValueEnum settingClickHand;
    public static ValueNumber settingClickRange;
    public static ValueNumber settingClickDelay;
    public static ValueEnum settingClickRotate;
    public static ValueBoolean settingRenderSwing;
    public static ValueBoolean settingRGB;
    public static ValueNumber settingRed;
    public static ValueNumber settingGreen;
    public static ValueNumber settingBlue;
    public static ValueNumber settingAlpha;
    public static ValueNumber settingOutlineLineSize;
    public static ValueNumber settingOutlineAlpha;
    private final CounterTool<BlockPos> counterClickAntiPlace;
    private final CounterTool<BlockPos> counterClickPacket;
    private final CounterTool<BlockPos> counterClickAntiClick;
    private final CounterTool<BlockPos> counterPlacePacket;
    private final TurokTick delayPlace;
    private final TurokTick delayClick;
    private final TurokTick delayPredict;
    private final ArrayList<BlockPos> placeList;
    private int bedSlot;
    private int currentSlot;
    private EntityPlayer targetPlayer;
    private boolean withOffhand;
    private BlockPos targetPlace;
    private BlockPos targetClick;
    private BlockPos lastPlace;
    private BlockPos lastClick;
    private EnumFacing targetDirection;
    private boolean offhandModuleNotifier;
    private Color outline;
    private Color solid;
    
    public ModuleBedAura() {
        this.counterClickAntiPlace = new CounterTool<BlockPos>();
        this.counterClickPacket = new CounterTool<BlockPos>();
        this.counterClickAntiClick = new CounterTool<BlockPos>();
        this.counterPlacePacket = new CounterTool<BlockPos>();
        this.delayPlace = new TurokTick();
        this.delayClick = new TurokTick();
        this.delayPredict = new TurokTick();
        this.placeList = new ArrayList<BlockPos>();
        this.outline = new Color(255, 255, 255, 255);
        this.solid = new Color(255, 255, 255, 255);
    }
    
    @Override
    public void onSetting() {
        ModuleBedAura.settingSelfDamage.setEnabled(!ModuleBedAura.settingSuicide.getValue());
        ModuleBedAura.settingPlaceSync.setEnabled(ModuleBedAura.settingPlace.getValue());
        ModuleBedAura.settingPlaceDelay.setEnabled(ModuleBedAura.settingPlace.getValue());
        ModuleBedAura.settingAirPlace.setEnabled(ModuleBedAura.settingPlace.getValue());
        ModuleBedAura.settingPlaceRange.setEnabled(ModuleBedAura.settingPlace.getValue());
        ModuleBedAura.settingPlaceWorldTick.setEnabled(ModuleBedAura.settingPlace.getValue());
        ModuleBedAura.settingPlaceRotate.setEnabled(ModuleBedAura.settingPlace.getValue());
        ModuleBedAura.settingClickRange.setEnabled(ModuleBedAura.settingClick.getValue());
        ModuleBedAura.settingClickDelay.setEnabled(ModuleBedAura.settingClick.getValue());
        ModuleBedAura.settingClickSync.setEnabled(ModuleBedAura.settingClick.getValue());
        ModuleBedAura.settingClickWorldTick.setEnabled(ModuleBedAura.settingClick.getValue());
        ModuleBedAura.settingClickRotate.setEnabled(ModuleBedAura.settingClick.getValue());
        ModuleBedAura.settingClickHand.setEnabled(ModuleBedAura.settingClick.getValue());
        ModuleBedAura.settingClickOnlyWhenEquippedBed.setEnabled(ModuleBedAura.settingClick.getValue());
        if (ModuleBedAura.settingRGB.getValue()) {
            ModuleBedAura.settingRed.setValue(Onepop.getClientEventManager().getCurrentRGBColor()[0]);
            ModuleBedAura.settingGreen.setValue(Onepop.getClientEventManager().getCurrentRGBColor()[1]);
            ModuleBedAura.settingBlue.setValue(Onepop.getClientEventManager().getCurrentRGBColor()[2]);
        }
        this.solid = new Color(ModuleBedAura.settingRed.getValue().intValue(), ModuleBedAura.settingGreen.getValue().intValue(), ModuleBedAura.settingBlue.getValue().intValue(), ModuleBedAura.settingAlpha.getValue().intValue());
        this.outline = new Color(ModuleBedAura.settingRed.getValue().intValue(), ModuleBedAura.settingGreen.getValue().intValue(), ModuleBedAura.settingBlue.getValue().intValue(), ModuleBedAura.settingOutlineAlpha.getValue().intValue());
    }
    
    protected void updateOffhand() {
        if (this.isEnabled() && this.withOffhand && ModuleOffhand.settingOffhandMode.getValue() != ModuleOffhand.OffhandMode.BED && !this.offhandModuleNotifier) {
            if (ModuleBedAura.settingAutoOffhand.getValue()) {
                ModuleOffhand.settingOffhandMode.setValue(ModuleOffhand.OffhandMode.BED);
                this.print("Bed offhand active!");
            }
            else {
                this.print("Check offhand module and set to bed mode!");
            }
            this.offhandModuleNotifier = true;
        }
    }
    
    @Override
    public void onEnable() {
        this.counterClickAntiPlace.clear();
        this.counterClickPacket.clear();
        this.counterClickAntiClick.clear();
        this.counterPlacePacket.clear();
        this.placeList.clear();
        this.delayPredict.reset();
        this.delayPlace.reset();
        this.delayClick.reset();
        this.offhandModuleNotifier = false;
    }
    
    @Override
    public void onDisable() {
        this.lastPlace = null;
        this.lastClick = null;
        this.targetPlace = null;
        this.targetClick = null;
        this.placeList.clear();
        this.delayPredict.reset();
        this.delayPlace.reset();
        this.delayClick.reset();
        this.offhandModuleNotifier = false;
    }
    
    @Override
    public void onRender3D() {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        if (ModuleBedAura.settingPlaceWorldTick.getValue()) {
            this.doPlace();
        }
        if (ModuleBedAura.settingClickWorldTick.getValue()) {
            this.doClick();
        }
        if (this.targetPlace != null && this.targetDirection != null) {
            final BlockPos firstBed = this.targetPlace.up();
            final BlockPos secondBed = this.targetPlace.offset(this.targetDirection).up();
            RenderUtil.drawSolidBlock(this.camera, firstBed.x, firstBed.y, firstBed.z, 1.0, 0.5, 1.0, this.solid);
            RenderUtil.drawSolidBlock(this.camera, secondBed.x, secondBed.y, secondBed.z, 1.0, 0.5, 1.0, this.solid);
            RenderUtil.drawOutlineBlock(this.camera, firstBed.x, firstBed.y, firstBed.z, 1.0, 0.5, 1.0, this.outline);
            RenderUtil.drawOutlineBlock(this.camera, secondBed.x, secondBed.y, secondBed.z, 1.0, 0.5, 1.0, this.outline);
        }
    }
    
    @Listener
    public void onReceivePacket(final PacketEvent.Receive event) {
    }
    
    @Listener
    public void onTick(final ClientTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        this.updateOffhand();
        if (PlayerUtil.getCurrentDimension() == PlayerUtil.Dimension.WORLD) {
            this.print("You're not in nether/end dimension!");
            this.setDisabled();
            return;
        }
        this.withOffhand = (ModuleBedAura.mc.player.getHeldItemOffhand().getItem() == Items.BED);
        this.bedSlot = SlotUtil.findItemSlotFromHotBar(Items.BED);
        this.doFindTarget();
        if (this.targetPlayer == null) {
            this.targetPlace = null;
            this.targetClick = null;
            return;
        }
        if (!ModuleBedAura.settingClick.getValue() || !this.isClickable() || !this.isTargetAliveAndNotNull()) {
            this.targetClick = null;
        }
        if (ModuleBedAura.settingPlace.getValue() && this.isBedFound() && this.isTargetAliveAndNotNull()) {
            if (!this.withOffhand && ModuleBedAura.mc.player.inventory.currentItem != this.currentSlot && ModuleBedAura.mc.player.inventory.currentItem != this.bedSlot) {
                SlotUtil.setCurrentItem(this.currentSlot = ModuleBedAura.mc.player.inventory.currentItem);
            }
        }
        else {
            this.targetDirection = null;
            this.targetPlace = null;
        }
        if (!ModuleBedAura.settingPlaceWorldTick.getValue()) {
            this.doPlace();
        }
        if (!ModuleBedAura.settingClickWorldTick.getValue()) {
            this.doClick();
        }
    }
    
    public void doPlace() {
        if (!ModuleBedAura.settingPlace.getValue() || !this.isBedFound() || !this.isTargetAliveAndNotNull()) {
            return;
        }
        float bestDamage = 1.0f;
        final float range = ModuleBedAura.settingPlaceRange.getValue().floatValue();
        BlockPos bestPlace = null;
        EnumFacing direction = null;
        for (final BlockPos positions : BlocksUtil.getSphereList(range)) {
            if (this.lastPlace != null && BlockUtil.getBlock(this.lastPlace.up()) == Blocks.BED && BlockUtil.getBedPlaceableFaces(this.lastPlace, ModuleBedAura.settingAirPlace.getValue()) != null) {
                bestPlace = this.lastPlace;
                direction = BlockUtil.getBedPlaceableFaces(this.lastPlace, ModuleBedAura.settingAirPlace.getValue());
                break;
            }
            if (BlockUtil.getBlock(positions) == Blocks.BED) {
                continue;
            }
            final EnumFacing bedDirection = BlockUtil.getBedPlaceableFaces(positions, ModuleBedAura.settingAirPlace.getValue());
            if (bedDirection == null) {
                continue;
            }
            if (this.counterClickAntiClick.getCount(positions) != null && this.counterClickAntiClick.getCount(positions) > 2) {
                this.counterClickAntiClick.remove(positions);
            }
            if (this.counterPlacePacket.getCount(positions) != null && this.counterPlacePacket.getCount(positions) > 4) {
                this.counterPlacePacket.remove(positions);
            }
            final double trace = this.targetPlayer.getDistanceSq(positions);
            if (trace >= ModuleBedAura.settingRange.getValue().floatValue() * ModuleBedAura.settingRange.getValue().floatValue()) {
                continue;
            }
            final float targetDamage = Math.max(CrystalUtil.calculateDamage(positions.offset(bedDirection), (Entity)this.targetPlayer), CrystalUtil.calculateDamage(positions, (Entity)this.targetPlayer));
            if (targetDamage <= bestDamage && BlockUtil.getBlock(positions.up()) != Blocks.BED) {
                continue;
            }
            final float selfDamage = Math.max(CrystalUtil.calculateDamage(positions.offset(bedDirection), (Entity)ModuleBedAura.mc.player), CrystalUtil.calculateDamage(positions, (Entity)ModuleBedAura.mc.player));
            if (selfDamage > ModuleBedAura.settingSelfDamage.getValue().intValue() && !ModuleBedAura.settingSuicide.getValue()) {
                continue;
            }
            if (targetDamage < ModuleBedAura.settingMinimumDamageTarget.getValue().floatValue()) {
                continue;
            }
            bestDamage = targetDamage;
            direction = bedDirection;
            bestPlace = positions;
        }
        if (ModuleBedAura.settingAutoSwitch.getValue() && !this.withOffhand && bestPlace != null) {
            SlotUtil.setCurrentItem(this.bedSlot);
        }
        this.targetPlace = (this.isBedAtHand() ? bestPlace : null);
        this.targetDirection = ((this.targetPlace != null) ? direction : null);
        if (this.delayPlace.isPassedMS((float)ModuleBedAura.settingPlaceDelay.getValue().intValue()) && this.targetPlace != null) {
            if (BlockUtil.getState(this.targetPlace).getBlock() == Blocks.BED) {
                return;
            }
            final EnumFacing facing = EnumFacing.UP;
            final Vec3d hitLook = PositionUtil.calculateHitPlace(PlayerUtil.getBlockPos().offset(direction).up(), direction);
            final float[] rotates = RotationUtil.getPlaceRotation(hitLook);
            if (ModuleBedAura.settingPlaceRotate.getValue() == RotationManager.Rotation.REL || ModuleBedAura.settingPlaceRotate.getValue() == RotationManager.Rotation.MANUAL || (this.counterPlacePacket.getCount(this.targetPlace) == null && ModuleBedAura.settingPlaceRotate.getValue() == RotationManager.Rotation.SEND) || !ModuleBedAura.settingPlaceSync.getValue()) {
                RotationManager.task(ModuleBedAura.settingPlaceRotate.getValue(), rotates);
            }
            final EnumHand hand = this.withOffhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
            if (this.counterClickAntiClick.getCount(this.targetPlace) == null || !ModuleBedAura.settingPlaceSync.getValue()) {
                ModuleBedAura.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.targetPlace, facing, hand, 0.0f, 0.0f, 0.0f));
                if (ModuleBedAura.settingRenderSwing.getValue()) {
                    ModuleBedAura.mc.player.swingArm(hand);
                }
                else {
                    ModuleBedAura.mc.player.connection.sendPacket((Packet)new CPacketAnimation(hand));
                }
                if (!this.placeList.contains(this.targetPlace)) {
                    this.placeList.add(this.targetPlace);
                }
            }
            if (ModuleBedAura.settingClick.getValue() && ModuleBedAura.settingClickPredict.getValue() && this.delayPredict.isPassedMS((float)ModuleBedAura.settingClickDelay.getValue().intValue())) {
                final EnumHand handBreak = (ModuleBedAura.settingClickHand.getValue() == ClickHand.AUTO) ? (this.withOffhand ? EnumHand.OFF_HAND : EnumHand.OFF_HAND) : ((ClickHand)ModuleBedAura.settingClickHand.getValue()).getHand();
                ModuleBedAura.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.targetPlace.up(), EnumFacing.UP, hand, 0.0f, 0.0f, 0.0f));
                ModuleBedAura.mc.player.connection.sendPacket((Packet)new CPacketAnimation(handBreak));
                this.delayPredict.reset();
            }
            this.counterPlacePacket.dispatch(this.targetPlace);
            this.counterClickAntiClick.dispatch(this.targetPlace);
            this.lastPlace = this.targetPlace;
            this.delayPlace.reset();
        }
    }
    
    public void doClick() {
        if (!ModuleBedAura.settingClick.getValue() || !this.isClickable() || !this.isTargetAliveAndNotNull()) {
            return;
        }
        BlockPos targetBed = null;
        final float clickRange = ModuleBedAura.settingClickRange.getValue().floatValue();
        float bestDamage = 1.0f;
        for (final BlockPos positions : BlocksUtil.getSphereList(PlayerUtil.getBlockPos(), clickRange, (int)clickRange, false, true)) {
            if (BlockUtil.isAir(positions)) {
                continue;
            }
            if (ModuleBedAura.mc.world.getBlockState(positions).getBlock() != Blocks.BED) {
                continue;
            }
            if (this.counterClickAntiPlace.getCount(positions) != null && this.counterClickAntiPlace.getCount(positions) > 5) {
                this.counterClickAntiPlace.remove(positions);
            }
            else if (this.counterClickPacket.getCount(positions) != null && this.counterClickPacket.getCount(positions) > 5) {
                this.counterClickPacket.remove(positions);
            }
            else {
                final float targetDamage = CrystalUtil.calculateDamage(positions, (Entity)this.targetPlayer);
                if (targetDamage <= bestDamage) {
                    continue;
                }
                bestDamage = targetDamage;
                targetBed = positions;
            }
        }
        this.targetClick = targetBed;
        if (this.delayClick.isPassedMS((float)ModuleBedAura.settingClickDelay.getValue().intValue()) && this.targetClick != null) {
            final EnumFacing facing = EnumFacing.func_190914_a(this.targetClick, (EntityLivingBase)ModuleBedAura.mc.player);
            final Vec3d hit = PositionUtil.calculateHitPlace(this.targetClick, facing);
            final float[] rotates = RotationUtil.getPlaceRotation(hit);
            if (ModuleBedAura.settingClickRotate.getValue() == RotationManager.Rotation.REL || ModuleBedAura.settingClickRotate.getValue() == RotationManager.Rotation.MANUAL || (this.counterPlacePacket.getCount(this.targetClick) == null && ModuleBedAura.settingClickRotate.getValue() == RotationManager.Rotation.SEND)) {
                RotationManager.task(ModuleBedAura.settingClickRotate.getValue(), rotates);
            }
            final EnumHand hand = (ModuleBedAura.settingClickHand.getValue() == ClickHand.AUTO) ? (this.withOffhand ? EnumHand.OFF_HAND : EnumHand.OFF_HAND) : ((ClickHand)ModuleBedAura.settingClickHand.getValue()).getHand();
            if (this.counterClickAntiPlace.getCount(this.targetClick) == null || !ModuleBedAura.settingClickSync.getValue()) {
                ModuleBedAura.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.targetClick, facing, hand, 0.5f, 0.5f, 0.5f));
                if (ModuleBedAura.settingRenderSwing.getValue()) {
                    ModuleBedAura.mc.player.swingArm(hand);
                }
                else {
                    ModuleBedAura.mc.player.connection.sendPacket((Packet)new CPacketAnimation(hand));
                }
                this.lastClick = this.targetClick;
            }
            this.counterClickAntiPlace.dispatch(this.targetClick);
            this.counterClickPacket.dispatch(this.targetClick);
            this.delayClick.reset();
        }
    }
    
    public void doFindTarget() {
        this.targetPlayer = EntityUtil.getTarget(ModuleBedAura.settingRange.getValue().floatValue(), ModuleBedAura.settingTargetMode.getValue() == ModuleAutoCrystal.TargetMode.UNSAFE, ModuleBedAura.settingAntiNaked.getValue());
    }
    
    public boolean isClickable() {
        return (this.isBedFound() && this.isBedAtHand()) || !ModuleBedAura.settingClickOnlyWhenEquippedBed.getValue();
    }
    
    public boolean isBedFound() {
        return this.bedSlot != -1 || this.withOffhand;
    }
    
    public boolean isBedAtHand() {
        return this.withOffhand || ModuleBedAura.mc.player.inventory.currentItem == this.bedSlot || ModuleBedAura.mc.player.getHeldItemMainhand().getItem() == Items.BED;
    }
    
    public boolean isTargetAliveAndNotNull() {
        return this.targetPlayer != null && this.targetPlayer.getHealth() > 0.0f && this.targetPlayer.isEntityAlive();
    }
    
    static {
        ModuleBedAura.settingAntiNaked = new ValueBoolean("Anti-Naked", "AntiNaked", "Verify if players is with armor to get target.", true);
        ModuleBedAura.settingAutoSwitch = new ValueBoolean("Auto-Switch", "AutoSwitch", "Automatically switch the slot for bed!", true);
        ModuleBedAura.settingAutoOffhand = new ValueBoolean("Auto-Offhand", "AutoOffhand", "Automatically set offhand bed if is needed!", false);
        ModuleBedAura.settingRange = new ValueNumber("Range", "Range", "Range for trace!", 6.0f, 1.0f, 13.0f);
        ModuleBedAura.settingTargetMode = new ValueEnum("Target Mode", "TargetMode", "Modes to get target.", ModuleAutoCrystal.TargetMode.UNSAFE);
        ModuleBedAura.settingSuicide = new ValueBoolean("Suicide", "Suicide", "Ignore self damage!", false);
        ModuleBedAura.settingSelfDamage = new ValueNumber("Self Damage", "SelfDamage", "The self place damage!", 8, 1, 36);
        ModuleBedAura.settingMinimumDamageTarget = new ValueNumber("Min. Target Dmg.", "MinimumTargetDamage", "The minimum damage for target!", 2.0f, 1.0f, 36.0f);
        ModuleBedAura.settingPlace = new ValueBoolean("Place", "Place", "Makes you place!", true);
        ModuleBedAura.settingPlaceSync = new ValueBoolean("Place Sync", "PlaceSync", "Sync places.", true);
        ModuleBedAura.settingPlaceWorldTick = new ValueBoolean("Place World Tick", "PlaceWorldTick", "Place with world tick!", false);
        ModuleBedAura.settingAirPlace = new ValueBoolean("Air Place", "Air-Place", "Air place for new Minecraft for 1.13+", false);
        ModuleBedAura.settingPlaceRange = new ValueNumber("Place Range", "PlaceRange", "The place ranges!", 4.0f, 1.0f, 6.0f);
        ModuleBedAura.settingPlaceDelay = new ValueNumber("Place Delay", "PlaceDelay", "The MS delay for place.", 50, 0, 100);
        ModuleBedAura.settingPlaceRotate = new ValueEnum("Place Rotate", "Place Rotate", "The rotations for place!", RotationManager.Rotation.SEND);
        ModuleBedAura.settingClick = new ValueBoolean("Click", "Click", "You clicks!", true);
        ModuleBedAura.settingClickPredict = new ValueBoolean("Click Predict", "ClickPredict", "Predictes clicks!", false);
        ModuleBedAura.settingClickSync = new ValueBoolean("Click Sync", "ClickSync", "Sync clicks.", true);
        ModuleBedAura.settingClickOnlyWhenEquippedBed = new ValueBoolean("Click With Bed", "ClickWithBed", "Only click if you are equipped with bed in hand!", false);
        ModuleBedAura.settingClickWorldTick = new ValueBoolean("Click World Tick", "ClickWorldTick", "Clicks with world tick!", false);
        ModuleBedAura.settingClickHand = new ValueEnum("Click Hand", "ClickHand", "The mode of click!", ClickHand.AUTO);
        ModuleBedAura.settingClickRange = new ValueNumber("Click Range", "ClickRange", "The range for click in beds!", 4.0f, 1.0f, 6.0f);
        ModuleBedAura.settingClickDelay = new ValueNumber("Click Delay", "ClickDelay", "The MS delay for click.", 50, 0, 100);
        ModuleBedAura.settingClickRotate = new ValueEnum("Click Rotate", "ClickRotate", "The click rotates!", RotationManager.Rotation.SEND);
        ModuleBedAura.settingRenderSwing = new ValueBoolean("Render Swing", "RenderSwing", "Render swing player.", true);
        ModuleBedAura.settingRGB = new ValueBoolean("RGB", "RGB", "RGB effect.", false);
        ModuleBedAura.settingRed = new ValueNumber("Red", "Red", "Color line range red.", 255, 0, 255);
        ModuleBedAura.settingGreen = new ValueNumber("Green", "Green", "Color line range green.", 0, 0, 255);
        ModuleBedAura.settingBlue = new ValueNumber("Blue", "Blue", "Color line range blue.", 255, 0, 255);
        ModuleBedAura.settingAlpha = new ValueNumber("Alpha", "Alpha", "Color line range alpha.", 255, 0, 255);
        ModuleBedAura.settingOutlineLineSize = new ValueNumber("Outline Line Size", "OutlineLineSize", "Line size.", 1.0f, 1.0f, 3.0f);
        ModuleBedAura.settingOutlineAlpha = new ValueNumber("Outline Alpha", "OutlineAlpha", "Color line range alpha.", 255, 0, 255);
    }
    
    public enum ClickHand
    {
        AUTO((EnumHand)null), 
        OFF(EnumHand.OFF_HAND), 
        MAIN(EnumHand.MAIN_HAND);
        
        EnumHand hand;
        
        private ClickHand(final EnumHand hand) {
            this.hand = hand;
        }
        
        public EnumHand getHand() {
            return this.hand;
        }
    }
}

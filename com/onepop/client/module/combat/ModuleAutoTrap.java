//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.combat;

import net.minecraft.util.math.Vec3d;
import net.minecraft.block.Block;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.EnumHand;
import com.onepop.client.manager.network.RotationManager;
import com.onepop.api.util.math.RotationUtil;
import com.onepop.api.util.math.PositionUtil;
import net.minecraft.util.EnumFacing;
import com.onepop.api.util.world.BlockUtil;
import net.minecraft.util.math.Vec3i;
import com.onepop.api.util.entity.EntityUtil;
import com.onepop.api.util.item.SlotUtil;
import com.onepop.api.util.client.NullUtil;
import com.onepop.client.event.client.RunTickEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import com.onepop.client.event.network.PacketEvent;
import net.minecraft.init.Blocks;
import net.minecraft.entity.player.EntityPlayer;
import com.onepop.api.tool.CounterTool;
import me.rina.turok.util.TurokTick;
import net.minecraft.item.Item;
import com.onepop.api.setting.value.ValueEnum;
import com.onepop.api.setting.value.ValueNumber;
import com.onepop.api.setting.value.ValueBoolean;
import net.minecraft.util.math.BlockPos;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Auto-Trap", tag = "AutoTrap", description = "Automatically places block to trap a player!", category = ModuleCategory.COMBAT)
public class ModuleAutoTrap extends Module
{
    public static final BlockPos[] MASK_FULLY;
    public static final BlockPos[] MASK_CITY;
    public static ValueBoolean settingAntiNaked;
    public static ValueBoolean settingRenderSwing;
    public static ValueBoolean settingNoForceRotate;
    public static ValueNumber settingTimeOut;
    public static ValueNumber settingRotationsCooldown;
    public static ValueNumber settingPlacesPerTick;
    public static ValueNumber settingPlaceRange;
    public static ValueEnum settingMode;
    public static ValueEnum settingRotate;
    private final Item obsidian;
    private final TurokTick out;
    private final TurokTick delay;
    private boolean withOffhand;
    private int obsidianSlot;
    private boolean placed;
    private int placesTick;
    private final CounterTool<BlockPos> counter;
    private EntityPlayer targetPlayer;
    
    public ModuleAutoTrap() {
        this.obsidian = Item.getItemFromBlock(Blocks.OBSIDIAN);
        this.out = new TurokTick();
        this.delay = new TurokTick();
        this.counter = new CounterTool<BlockPos>();
    }
    
    @Override
    public void onDisable() {
        this.out.reset();
        this.delay.reset();
    }
    
    @Override
    public void onEnable() {
        this.out.reset();
        this.delay.reset();
    }
    
    @Listener
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketPlayerPosLook && ModuleAutoTrap.settingNoForceRotate.getValue()) {
            event.setCanceled(true);
        }
    }
    
    @Listener
    public void onTick(final RunTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        this.withOffhand = (ModuleAutoTrap.mc.player.getHeldItemOffhand().getItem() == this.obsidian);
        this.obsidianSlot = SlotUtil.findItemSlotFromHotBar(this.obsidian);
        final BlockPos[] mask = ((Mode)ModuleAutoTrap.settingMode.getValue()).getMask();
        if ((!this.withOffhand && this.obsidianSlot == -1) || this.out.isPassedMS((float)ModuleAutoTrap.settingTimeOut.getValue().intValue())) {
            this.setDisabled();
            return;
        }
        this.targetPlayer = EntityUtil.getTarget(ModuleAutoTrap.settingPlaceRange.getValue().floatValue(), false, ModuleAutoTrap.settingAntiNaked.getValue());
        this.placed = false;
        if (this.targetPlayer == null) {
            return;
        }
        final BlockPos targetPosition = new BlockPos(Math.floor(this.targetPlayer.posX), Math.floor(this.targetPlayer.posY), Math.floor(this.targetPlayer.posZ));
        int runningTicks = 0;
        while (runningTicks < ModuleAutoTrap.settingPlacesPerTick.getValue().intValue()) {
            if (this.placesTick >= mask.length) {
                this.placesTick = 0;
                break;
            }
            final BlockPos added = targetPosition.add((Vec3i)mask[this.placesTick]);
            if (ModuleAutoTrap.mc.player.getDistance((double)added.x, (double)added.y, (double)added.z) < ModuleAutoTrap.settingPlaceRange.getValue().floatValue() && BlockUtil.isAir(added) && BlockUtil.isPlaceableExcludingEntity(added)) {
                if (this.counter.getCount(added) != null && this.counter.getCount(added) > ModuleAutoTrap.settingRotationsCooldown.getValue().intValue()) {
                    this.counter.remove(added);
                }
                else {
                    this.doPlace(added);
                    if (this.placed) {
                        ++runningTicks;
                    }
                }
            }
            ++this.placesTick;
        }
    }
    
    public void doPlace(final BlockPos place) {
        if (place == null) {
            return;
        }
        if (!this.withOffhand && this.obsidianSlot == -1) {
            return;
        }
        for (final EnumFacing faces : EnumFacing.values()) {
            final BlockPos offset = place.offset(faces);
            final Block block = ModuleAutoTrap.mc.world.getBlockState(offset).getBlock();
            if (block != Blocks.AIR && !BlockUtil.BLACK_LIST.contains(block)) {
                final EnumFacing facing = faces.getOpposite();
                final Vec3d hit = PositionUtil.calculateHitPlace(offset, facing);
                final float facingX = (float)(offset.getX() - hit.xCoord);
                final float facingY = (float)(offset.getY() - hit.yCoord);
                final float facingZ = (float)(offset.getZ() - hit.zCoord);
                final float[] rotates = RotationUtil.getPlaceRotation(hit);
                if (ModuleAutoTrap.settingRotate.getValue() == RotationManager.Rotation.REL || ModuleAutoTrap.settingRotate.getValue() == RotationManager.Rotation.MANUAL || (this.counter.getCount(place) == null && ModuleAutoTrap.settingRotate.getValue() == RotationManager.Rotation.SEND)) {
                    RotationManager.task(ModuleAutoTrap.settingRotate.getValue(), rotates);
                }
                final EnumHand hand = this.withOffhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
                if (!this.withOffhand) {
                    ModuleAutoTrap.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.obsidianSlot));
                }
                ModuleAutoTrap.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(offset, facing, hand, facingX, facingY, facingZ));
                if (ModuleAutoTrap.settingRenderSwing.getValue()) {
                    ModuleAutoTrap.mc.player.swingArm(hand);
                }
                else {
                    ModuleAutoTrap.mc.player.connection.sendPacket((Packet)new CPacketAnimation(hand));
                }
                if (!this.withOffhand) {
                    ModuleAutoTrap.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(ModuleAutoTrap.mc.player.inventory.currentItem));
                }
                this.counter.dispatch(place);
                this.placed = true;
                return;
            }
        }
    }
    
    static {
        MASK_FULLY = new BlockPos[] { new BlockPos(-1, -1, 0), new BlockPos(1, -1, 0), new BlockPos(0, -1, -1), new BlockPos(0, -1, 1), new BlockPos(0, -1, 0), new BlockPos(-1, 0, 0), new BlockPos(1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, 0, 1), new BlockPos(1, 1, 0), new BlockPos(-1, 1, 0), new BlockPos(0, 1, 1), new BlockPos(0, 1, -1), new BlockPos(1, 2, 0), new BlockPos(-1, 2, 0), new BlockPos(0, 2, 1), new BlockPos(0, 2, -1), new BlockPos(0, 2, 0) };
        MASK_CITY = new BlockPos[] { new BlockPos(-1, -1, 1), new BlockPos(1, -1, 1), new BlockPos(1, -1, -1), new BlockPos(-1, -1, -1), new BlockPos(0, -1, 0), new BlockPos(-1, 0, 1), new BlockPos(1, 0, 1), new BlockPos(1, 0, -1), new BlockPos(-1, 0, -1), new BlockPos(-1, 0, 1), new BlockPos(-1, 1, 1), new BlockPos(1, 1, 1), new BlockPos(1, 1, -1), new BlockPos(-1, 1, -1), new BlockPos(1, 1, 1), new BlockPos(1, 1, 0), new BlockPos(-1, 1, 0), new BlockPos(0, 1, 1), new BlockPos(0, 1, -1), new BlockPos(1, 2, 0), new BlockPos(-1, 2, 0), new BlockPos(0, 2, 1), new BlockPos(0, 2, -1), new BlockPos(0, 2, 0) };
        ModuleAutoTrap.settingAntiNaked = new ValueBoolean("Anti-Naked", "AntiNaked", "Preserve naked players.", true);
        ModuleAutoTrap.settingRenderSwing = new ValueBoolean("Render Swing", "RenderSwing", "Render swing place.", false);
        ModuleAutoTrap.settingNoForceRotate = new ValueBoolean("No Force Rotate", "NoForceRotate", "Prevents server rotation.", false);
        ModuleAutoTrap.settingTimeOut = new ValueNumber("Time Out", "TimeOut", "Time out for anti stuck.", 3000, 0, 5000);
        ModuleAutoTrap.settingRotationsCooldown = new ValueNumber("Rotations Cooldown", "RotationsCooldown", "Cooldown for rotations .", 4, 1, 6);
        ModuleAutoTrap.settingPlacesPerTick = new ValueNumber("Places per Tick", "PlacesPerTick", "Places per tick.", 4, 1, 50);
        ModuleAutoTrap.settingPlaceRange = new ValueNumber("Place Range", "PlaceRange", "Maximum place range.", 4.0f, 1.0f, 6.0f);
        ModuleAutoTrap.settingMode = new ValueEnum("Mode", "Mode", "Mode of auto trap.", Mode.FULLY);
        ModuleAutoTrap.settingRotate = new ValueEnum("Rotate", "Rotate", "Modes for rotate.", RotationManager.Rotation.REL);
    }
    
    public enum Mode
    {
        FULLY(ModuleAutoTrap.MASK_FULLY), 
        CITY(ModuleAutoTrap.MASK_CITY);
        
        BlockPos[] mask;
        
        private Mode(final BlockPos[] mask) {
            this.mask = mask;
        }
        
        public BlockPos[] getMask() {
            return this.mask;
        }
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.combat;

import net.minecraft.util.math.Vec3d;
import net.minecraft.block.Block;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumHand;
import com.onepop.client.manager.network.RotationManager;
import com.onepop.api.util.math.RotationUtil;
import com.onepop.api.util.math.PositionUtil;
import net.minecraft.util.EnumFacing;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import com.onepop.api.util.entity.PlayerUtil;
import com.onepop.api.util.item.SlotUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.init.Blocks;
import com.onepop.api.util.world.BlockUtil;
import net.minecraft.util.math.BlockPos;
import com.onepop.client.event.client.RunTickEvent;
import com.onepop.api.util.client.NullUtil;
import net.minecraft.item.Item;
import com.onepop.api.setting.value.ValueNumber;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.setting.value.ValueEnum;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Burrow", tag = "Burrow", description = "Self place block!", category = ModuleCategory.COMBAT)
public class ModuleBurrow extends Module
{
    public static ModuleBurrow INSTANCE;
    public static ValueEnum settingMode;
    public static ValueBoolean settingEnchest;
    public static ValueBoolean settingOffhand;
    public static ValueEnum settingRotate;
    public static ValueBoolean settingExtraRubberband;
    public static ValueNumber settingRubberbandAmount;
    public static ValueNumber settingPackets;
    public static ValueNumber settingPreJumpPackets;
    public static ValueBoolean settingPostJumpPacket;
    public static ValueNumber settingPostJumpPackets;
    public static final double JUMP_INCREASE_1 = 0.41999998688698;
    public static final double JUMP_INCREASE_2 = 0.7531999805211997;
    public static final double JUMP_INCREASE_3 = 1.00133597911214;
    public static final double JUMP_INCREASE_4 = 1.16610926093821;
    public static final Item OBSIDIAN_BLOCK;
    public static final Item ENCHEST_BLOCK;
    private boolean withBlock;
    private boolean withOffhand;
    
    public ModuleBurrow() {
        ModuleBurrow.INSTANCE = this;
    }
    
    @Override
    public void onSetting() {
        ModuleBurrow.settingPackets.setEnabled(ModuleBurrow.settingMode.getValue() == Mode.STRICT);
        ModuleBurrow.settingPreJumpPackets.setEnabled(ModuleBurrow.settingMode.getValue() == Mode.STRICT);
        ModuleBurrow.settingPostJumpPacket.setEnabled(ModuleBurrow.settingMode.getValue() == Mode.STRICT);
        ModuleBurrow.settingPostJumpPackets.setEnabled(ModuleBurrow.settingMode.getValue() == Mode.STRICT && ModuleBurrow.settingPostJumpPacket.getValue());
        ModuleBurrow.settingRubberbandAmount.setEnabled(ModuleBurrow.settingExtraRubberband.getValue());
    }
    
    @Override
    public void onEnable() {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
    }
    
    @Override
    public void onDisable() {
    }
    
    @Listener
    public void onTick(final RunTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        this.withOffhand = (ModuleBurrow.settingOffhand.getValue() && this.getItemBlock() != null && ModuleBurrow.mc.player.getHeldItemOffhand().getItem() == this.getItemBlock());
        this.withBlock = (this.withOffhand || this.getItemBlock() != null);
        if (this.getItemBlock() == null) {
            this.setDisabled();
            return;
        }
        if (ModuleBurrow.settingMode.getValue() == Mode.TP && !ModuleBurrow.mc.player.onGround) {
            return;
        }
        final int currentItem = ModuleBurrow.mc.player.inventory.currentItem;
        final BlockPos offset = new BlockPos(Math.floor(ModuleBurrow.mc.player.posX), Math.floor(ModuleBurrow.mc.player.posY + 0.30000001192092896), Math.floor(ModuleBurrow.mc.player.posZ));
        if (BlockUtil.getBlock(offset) != Blocks.AIR) {
            this.setDisabled();
            return;
        }
        if (ModuleBurrow.settingMode.getValue() == Mode.TP) {
            ModuleBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ModuleBurrow.mc.player.posX, ModuleBurrow.mc.player.posY + 0.41999998688698, ModuleBurrow.mc.player.posZ, true));
            ModuleBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ModuleBurrow.mc.player.posX, ModuleBurrow.mc.player.posY + 0.7531999805211997, ModuleBurrow.mc.player.posZ, true));
            ModuleBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ModuleBurrow.mc.player.posX, ModuleBurrow.mc.player.posY + 1.00133597911214, ModuleBurrow.mc.player.posZ, true));
            ModuleBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ModuleBurrow.mc.player.posX, ModuleBurrow.mc.player.posY + 1.16610926093821, ModuleBurrow.mc.player.posZ, true));
            this.doPlace(offset);
            SlotUtil.setServerCurrentItem(currentItem);
            this.doRubberband();
            this.setDisabled();
        }
        else if (ModuleBurrow.settingMode.getValue() == Mode.JUMP) {
            final BlockPos selfPos = PlayerUtil.getBlockPos();
            if (ModuleBurrow.mc.player.onGround) {
                ModuleBurrow.mc.player.jump();
            }
            if (ModuleBurrow.mc.player.fallDistance > 0.0f) {
                this.doPlace(selfPos.down());
                ModuleBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ModuleBurrow.mc.player.posX, ModuleBurrow.mc.player.posY + 50.0, ModuleBurrow.mc.player.posZ, true));
                SlotUtil.setServerCurrentItem(currentItem);
                this.doRubberband();
                this.setDisabled();
            }
        }
        else {
            for (int i = 0; i < ModuleBurrow.settingPreJumpPackets.getValue().intValue(); ++i) {
                if (ModuleBurrow.settingPackets.getValue().intValue() >= 1) {
                    ModuleBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(0.0f, 0.0f, true));
                }
                if (ModuleBurrow.settingPackets.getValue().intValue() >= 2) {
                    ModuleBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(ModuleBurrow.mc.player.rotationYaw, ModuleBurrow.mc.player.rotationPitch, true));
                }
                if (ModuleBurrow.settingPackets.getValue().intValue() >= 3) {
                    ModuleBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(0.0f, 0.0f, true));
                }
                if (ModuleBurrow.settingPackets.getValue().intValue() >= 4) {
                    ModuleBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(ModuleBurrow.mc.player.rotationYaw, ModuleBurrow.mc.player.rotationPitch, true));
                }
                if (ModuleBurrow.settingPackets.getValue().intValue() >= 5) {
                    ModuleBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(0.0f, 0.0f, true));
                }
                if (ModuleBurrow.settingPackets.getValue().intValue() >= 6) {
                    ModuleBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(ModuleBurrow.mc.player.rotationYaw, ModuleBurrow.mc.player.rotationPitch, true));
                }
            }
            final BlockPos selfPos = PlayerUtil.getBlockPos();
            if (ModuleBurrow.mc.player.onGround) {
                ModuleBurrow.mc.player.jump();
            }
            if (ModuleBurrow.mc.player.fallDistance > 0.0f) {
                this.doPlace(selfPos.down());
                if (ModuleBurrow.settingPostJumpPacket.getValue()) {
                    for (int j = 0; j < ModuleBurrow.settingPostJumpPackets.getValue().intValue(); ++j) {
                        if (ModuleBurrow.settingPackets.getValue().intValue() >= 1) {
                            ModuleBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(0.0f, 0.0f, true));
                        }
                        if (ModuleBurrow.settingPackets.getValue().intValue() >= 2) {
                            ModuleBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(ModuleBurrow.mc.player.rotationYaw, ModuleBurrow.mc.player.rotationPitch, true));
                        }
                        if (ModuleBurrow.settingPackets.getValue().intValue() >= 3) {
                            ModuleBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(0.0f, 0.0f, true));
                        }
                        if (ModuleBurrow.settingPackets.getValue().intValue() >= 4) {
                            ModuleBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(ModuleBurrow.mc.player.rotationYaw, ModuleBurrow.mc.player.rotationPitch, true));
                        }
                        if (ModuleBurrow.settingPackets.getValue().intValue() >= 5) {
                            ModuleBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(0.0f, 0.0f, true));
                        }
                        if (ModuleBurrow.settingPackets.getValue().intValue() >= 6) {
                            ModuleBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(ModuleBurrow.mc.player.rotationYaw, ModuleBurrow.mc.player.rotationPitch, true));
                        }
                    }
                }
                SlotUtil.setServerCurrentItem(currentItem);
                this.setDisabled();
            }
        }
    }
    
    public void doRubberband() {
        if (ModuleBurrow.settingExtraRubberband.getValue()) {
            ModuleBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ModuleBurrow.mc.player.posX, ModuleBurrow.mc.player.posY + ModuleBurrow.settingRubberbandAmount.getValue().floatValue(), ModuleBurrow.mc.player.posZ, true));
        }
    }
    
    public void doPlace(final BlockPos place) {
        if (place == null) {
            return;
        }
        if (BlockUtil.getBlock(place) != Blocks.AIR) {
            return;
        }
        for (final EnumFacing faces : EnumFacing.values()) {
            final BlockPos offset = place.offset(faces);
            final Block block = ModuleBurrow.mc.world.getBlockState(offset).getBlock();
            if (block != Blocks.AIR && !BlockUtil.BLACK_LIST.contains(block)) {
                final EnumFacing facing = faces.getOpposite();
                final Vec3d hit = PositionUtil.calculateHitPlace(offset, facing);
                final float facingX = (float)(offset.getX() - hit.xCoord);
                final float facingY = (float)(offset.getY() - hit.yCoord);
                final float facingZ = (float)(offset.getZ() - hit.zCoord);
                final float[] rotates = RotationUtil.getPlaceRotation(hit);
                if (!this.withOffhand && this.withBlock && this.getItemBlock() != null) {
                    SlotUtil.setServerCurrentItem(SlotUtil.findItemSlotFromHotBar(this.getItemBlock()));
                }
                RotationManager.task(ModuleBurrow.settingRotate.getValue(), rotates);
                final EnumHand hand = this.withOffhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
                ModuleBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(offset, facing, hand, facingX, facingY, facingZ));
                ModuleBurrow.mc.player.connection.sendPacket((Packet)new CPacketAnimation(hand));
                return;
            }
        }
    }
    
    public Item getItemBlock() {
        final int obsidian = SlotUtil.findItemSlotFromHotBar(ModuleBurrow.OBSIDIAN_BLOCK);
        if (obsidian != -1 || (ModuleBurrow.mc.player.getHeldItemOffhand().getItem() == ModuleBurrow.OBSIDIAN_BLOCK && ModuleBurrow.settingOffhand.getValue())) {
            return ModuleBurrow.OBSIDIAN_BLOCK;
        }
        final int enchest = SlotUtil.findItemSlotFromHotBar(ModuleBurrow.ENCHEST_BLOCK);
        if ((enchest != -1 || (ModuleBurrow.mc.player.getHeldItemOffhand().getItem() == ModuleBurrow.ENCHEST_BLOCK && ModuleBurrow.settingOffhand.getValue())) && ModuleBurrow.settingEnchest.getValue()) {
            return ModuleBurrow.ENCHEST_BLOCK;
        }
        return null;
    }
    
    static {
        ModuleBurrow.settingMode = new ValueEnum("Mode", "Mode", "Modes for burrow!", Mode.TP);
        ModuleBurrow.settingEnchest = new ValueBoolean("Enchest", "Enchest", "Choose enchenst also.", true);
        ModuleBurrow.settingOffhand = new ValueBoolean("Offhand", "Offhand", "Checks also in offhand.", true);
        ModuleBurrow.settingRotate = new ValueEnum("Rotate", "Rotate", "Rotate modes!", RotationManager.Rotation.REL);
        ModuleBurrow.settingExtraRubberband = new ValueBoolean("Extra Rubberband", "ExtraRubberband", "Apply extra rubberband for preserve fall!", true);
        ModuleBurrow.settingRubberbandAmount = new ValueNumber("Rubberband Amount", "RubberbandAmount", "Rubberband amount!", 5.0f, -5.0f, 5.0f);
        ModuleBurrow.settingPackets = new ValueNumber("Packets", "Packet", "Packets you will send!", 2, 1, 6);
        ModuleBurrow.settingPreJumpPackets = new ValueNumber("Pre Jump Packets", "PreJumpPackets", "Pre jump packets!", 4, 1, 10);
        ModuleBurrow.settingPostJumpPacket = new ValueBoolean("Post Jump Packet", "PostJumpPacket", "Post jump packet!", false);
        ModuleBurrow.settingPostJumpPackets = new ValueNumber("Post Jump Packets", "PostJumpPackets", "Post jump packets!!!", 2, 1, 6);
        OBSIDIAN_BLOCK = Item.getItemFromBlock(Blocks.OBSIDIAN);
        ENCHEST_BLOCK = Item.getItemFromBlock(Blocks.ENDER_CHEST);
    }
    
    public enum Mode
    {
        TP, 
        JUMP, 
        STRICT;
    }
}

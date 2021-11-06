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
import com.onepop.api.util.world.BlockUtil;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import com.onepop.api.util.entity.PlayerUtil;
import net.minecraft.entity.Entity;
import com.onepop.Onepop;
import com.onepop.api.util.entity.EntityUtil;
import com.onepop.api.util.item.SlotUtil;
import com.onepop.api.util.client.NullUtil;
import com.onepop.client.event.client.RunTickEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import com.onepop.api.setting.value.ValueEnum;
import com.onepop.api.setting.value.ValueNumber;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Self Web", tag = "SelfWeb", description = "Self places web.", category = ModuleCategory.COMBAT)
public class ModuleSelfWeb extends Module
{
    public static ValueBoolean settingAntiNaked;
    public static ValueBoolean settingRenderSwing;
    public static ValueBoolean settingDisableAfter;
    public static ValueBoolean settingOffhand;
    public static ValueNumber settingMinimumRange;
    public static ValueEnum settingMode;
    public static ValueEnum settingRotate;
    private static final Item WEB;
    private EntityPlayer targetPlayer;
    private boolean placed;
    private boolean withOffhand;
    private int obsidianSlot;
    
    @Override
    public void onSetting() {
        ModuleSelfWeb.settingAntiNaked.setEnabled(ModuleSelfWeb.settingMode.getValue() == Mode.SMART);
        ModuleSelfWeb.settingMinimumRange.setEnabled(ModuleSelfWeb.settingMode.getValue() == Mode.SMART);
        ModuleSelfWeb.settingDisableAfter.setEnabled(ModuleSelfWeb.settingMode.getValue() == Mode.SMART);
    }
    
    @Override
    public void onEnable() {
        this.targetPlayer = null;
    }
    
    @Override
    public void onDisable() {
        this.targetPlayer = null;
    }
    
    @Listener
    public void onTick(final RunTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        this.obsidianSlot = SlotUtil.findItemSlotFromHotBar(ModuleSelfWeb.WEB);
        this.withOffhand = (ModuleSelfWeb.mc.player.getHeldItemOffhand().getItem() == ModuleSelfWeb.WEB && ModuleSelfWeb.settingOffhand.getValue());
        if (this.obsidianSlot == -1 && !this.withOffhand) {
            this.setDisabled();
            return;
        }
        if (!ModuleSelfWeb.mc.player.onGround) {
            return;
        }
        if (ModuleSelfWeb.settingMode.getValue() == Mode.SMART) {
            this.targetPlayer = EntityUtil.getTarget(4.0f, false, ModuleSelfWeb.settingAntiNaked.getValue());
        }
        this.placed = false;
        switch (ModuleSelfWeb.settingMode.getValue()) {
            case SMART: {
                if (this.targetPlayer == null || !Onepop.getBlockManager().getPlayerSurroundBlockList().isEmpty() || ModuleSelfWeb.mc.player.getDistanceToEntity((Entity)this.targetPlayer) >= ModuleSelfWeb.settingMinimumRange.getValue().floatValue()) {
                    break;
                }
                this.doPlace(PlayerUtil.getBlockPos());
                if (this.placed && ModuleSelfWeb.settingDisableAfter.getValue()) {
                    this.setDisabled();
                    break;
                }
                break;
            }
            case TOGGLE: {
                this.doPlace(PlayerUtil.getBlockPos());
                if (this.placed) {
                    this.setDisabled();
                    break;
                }
                break;
            }
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
            final Block block = ModuleSelfWeb.mc.world.getBlockState(offset).getBlock();
            if (block != Blocks.AIR && !BlockUtil.BLACK_LIST.contains(block)) {
                final EnumFacing facing = faces.getOpposite();
                final Vec3d hit = PositionUtil.calculateHitPlace(offset, facing);
                final float facingX = (float)(offset.getX() - hit.xCoord);
                final float facingY = (float)(offset.getY() - hit.yCoord);
                final float facingZ = (float)(offset.getZ() - hit.zCoord);
                final float[] rotates = RotationUtil.getPlaceRotation(hit);
                RotationManager.task(ModuleSelfWeb.settingRotate.getValue(), rotates);
                final EnumHand hand = this.withOffhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
                if (!this.withOffhand) {
                    ModuleSelfWeb.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.obsidianSlot));
                }
                ModuleSelfWeb.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(offset, facing, hand, facingX, facingY, facingZ));
                if (ModuleSelfWeb.settingRenderSwing.getValue()) {
                    ModuleSelfWeb.mc.player.swingArm(hand);
                }
                else {
                    ModuleSelfWeb.mc.player.connection.sendPacket((Packet)new CPacketAnimation(hand));
                }
                if (!this.withOffhand) {
                    ModuleSelfWeb.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(ModuleSelfWeb.mc.player.inventory.currentItem));
                }
                this.placed = true;
                return;
            }
        }
    }
    
    static {
        ModuleSelfWeb.settingAntiNaked = new ValueBoolean("Anti-Naked", "AntiNaked", "Anti naked option to smart mode.", true);
        ModuleSelfWeb.settingRenderSwing = new ValueBoolean("Render Swing", "RenderSwing", "Render swing.", false);
        ModuleSelfWeb.settingDisableAfter = new ValueBoolean("Disable After", "DisableAfter", "Disable after places web.", true);
        ModuleSelfWeb.settingOffhand = new ValueBoolean("Offhand", "Offhand", "Checks offhand.", true);
        ModuleSelfWeb.settingMinimumRange = new ValueNumber("Minimum Range", "MinimumRange", "Minimum range for self webs.", 1.0f, 0.5f, 4.0f);
        ModuleSelfWeb.settingMode = new ValueEnum("Mode", "Mode", "Modes for webs logic.", Mode.SMART);
        ModuleSelfWeb.settingRotate = new ValueEnum("Rotate", "Rotate", "Modes for rotates.", RotationManager.Rotation.SEND);
        WEB = Item.getItemFromBlock(Blocks.WEB);
    }
    
    public enum Mode
    {
        TOGGLE, 
        SMART;
    }
}

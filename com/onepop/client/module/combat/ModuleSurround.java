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
import net.minecraft.network.play.client.CPacketHeldItemChange;
import com.onepop.api.util.math.PositionUtil;
import com.onepop.api.util.world.BlockUtil;
import net.minecraft.util.EnumFacing;
import java.util.List;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import com.onepop.api.util.entity.PlayerUtil;
import com.onepop.api.util.item.SlotUtil;
import net.minecraft.item.Item;
import net.minecraft.init.Blocks;
import com.onepop.client.event.client.RunTickEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import com.onepop.client.event.network.PacketEvent;
import java.util.Iterator;
import com.onepop.api.util.render.RenderUtil;
import java.awt.Color;
import com.onepop.client.manager.world.BlockManager;
import com.onepop.api.util.client.NullUtil;
import java.util.ArrayList;
import net.minecraft.util.math.BlockPos;
import com.onepop.api.tool.CounterTool;
import me.rina.turok.util.TurokTick;
import com.onepop.api.setting.value.ValueEnum;
import com.onepop.api.setting.value.ValueNumber;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Surround", tag = "Surround", description = "Automatically places block around of you.", category = ModuleCategory.COMBAT)
public class ModuleSurround extends Module
{
    public static ValueBoolean settingGround;
    public static ValueBoolean settingAutoCenter;
    public static ValueBoolean settingOffhand;
    public static ValueBoolean settingNoForceRotate;
    public static ValueBoolean settingRetrace;
    public static ValueNumber settingRotationsCooldown;
    public static ValueNumber settingTimeOut;
    public static ValueEnum settingRotate;
    public static ValueBoolean settingRenderSwing;
    public static ValueEnum settingRender;
    public static ValueNumber settingRenderAlpha;
    private final TurokTick delay;
    private final TurokTick out;
    private final CounterTool<BlockPos> counter;
    private boolean centered;
    private boolean withOffhand;
    private int obsidianSlot;
    private final ArrayList<BlockPos> confirmPlace;
    
    public ModuleSurround() {
        this.delay = new TurokTick();
        this.out = new TurokTick();
        this.counter = new CounterTool<BlockPos>();
        this.confirmPlace = new ArrayList<BlockPos>();
    }
    
    @Override
    public void onSetting() {
        ModuleSurround.settingRenderAlpha.setEnabled(ModuleSurround.settingRender.getValue() != Render.NONE);
    }
    
    @Override
    public void onRender3D() {
        if (NullUtil.isPlayerWorld() && ModuleSurround.settingRender.getValue() != Render.NONE) {
            return;
        }
        final int alpha = ModuleSurround.settingRenderAlpha.getValue().intValue();
        for (final BlockPos surround : BlockManager.getAirSurroundPlayer()) {
            switch (ModuleSurround.settingRender.getValue()) {
                case SOLID: {
                    RenderUtil.drawSolidBlock(this.camera, surround, new Color(0, 255, 0, alpha));
                    continue;
                }
                case OUTLINE: {
                    RenderUtil.drawOutlineBlock(this.camera, surround, new Color(0, 255, 0, alpha));
                    continue;
                }
            }
        }
    }
    
    @Override
    public void onEnable() {
        this.out.reset();
        this.delay.reset();
        this.centered = false;
        this.counter.clear();
        this.confirmPlace.clear();
    }
    
    @Override
    public void onDisable() {
        this.out.reset();
        this.delay.reset();
        this.counter.clear();
        this.confirmPlace.clear();
    }
    
    @Listener
    public void onListen(final PacketEvent event) {
        if (event.getPacket() instanceof SPacketPlayerPosLook && ModuleSurround.settingNoForceRotate.getValue()) {
            event.setCanceled(true);
        }
    }
    
    @Listener
    public void onListen(final RunTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        this.obsidianSlot = SlotUtil.findItemSlotFromHotBar(Item.getItemFromBlock(Blocks.OBSIDIAN));
        this.withOffhand = (ModuleSurround.mc.player.getHeldItemOffhand().getItem() == Item.getItemFromBlock(Blocks.OBSIDIAN) && ModuleSurround.settingOffhand.getValue());
        if ((this.obsidianSlot == -1 && !this.withOffhand) || BlockManager.getAirSurroundPlayer().isEmpty() || this.out.isPassedMS((float)ModuleSurround.settingTimeOut.getValue().intValue())) {
            this.setDisabled();
            return;
        }
        if (!this.centered && ModuleSurround.mc.player.onGround && ModuleSurround.settingAutoCenter.getValue()) {
            final BlockPos selfPos = PlayerUtil.getBlockPos();
            ModuleSurround.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position((double)(selfPos.x + 0.5f), (double)selfPos.y, (double)(selfPos.z + 0.5f), ModuleSurround.mc.player.onGround));
            ModuleSurround.mc.player.setPosition((double)(selfPos.x + 0.5f), (double)selfPos.y, (double)(selfPos.z + 0.5f));
            this.centered = true;
        }
        final List<BlockPos> maskSurround = BlockManager.getAirSurroundPlayer();
        for (final BlockPos places : maskSurround) {
            if (this.confirmPlace.contains(places)) {
                continue;
            }
            if (this.counter.getCount(places) != null && this.counter.getCount(places) > ModuleSurround.settingRotationsCooldown.getValue().intValue()) {
                this.counter.remove(places);
            }
            else {
                this.doPlace(places);
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
        if (!ModuleSurround.mc.player.onGround && ModuleSurround.settingGround.getValue()) {
            return;
        }
        for (final EnumFacing faces : EnumFacing.values()) {
            final BlockPos offset = place.offset(faces);
            final Block block = ModuleSurround.mc.world.getBlockState(offset).getBlock();
            if (block != Blocks.AIR && !BlockUtil.BLACK_LIST.contains(block)) {
                final EnumFacing facing = faces.getOpposite();
                final Vec3d hit = PositionUtil.calculateHitPlace(offset, facing);
                final float facingX = (float)(offset.getX() - hit.xCoord);
                final float facingY = (float)(offset.getY() - hit.yCoord);
                final float facingZ = (float)(offset.getZ() - hit.zCoord);
                if (!this.withOffhand) {
                    ModuleSurround.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.obsidianSlot));
                }
                final float[] rotates = RotationUtil.getPlaceRotation(hit);
                if (ModuleSurround.settingRotate.getValue() == RotationManager.Rotation.REL || ModuleSurround.settingRotate.getValue() == RotationManager.Rotation.MANUAL || (this.counter.getCount(place) == null && ModuleSurround.settingRotate.getValue() == RotationManager.Rotation.SEND)) {
                    RotationManager.task(ModuleSurround.settingRotate.getValue(), rotates);
                }
                final EnumHand hand = this.withOffhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
                ModuleSurround.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(offset, facing, hand, facingX, facingY, facingZ));
                if (ModuleSurround.settingRetrace.getValue()) {
                    ModuleSurround.mc.playerController.processRightClickBlock(ModuleSurround.mc.player, ModuleSurround.mc.world, offset, facing, hit, hand);
                }
                if (ModuleSurround.settingRenderSwing.getValue()) {
                    ModuleSurround.mc.player.swingArm(hand);
                }
                else {
                    ModuleSurround.mc.player.connection.sendPacket((Packet)new CPacketAnimation(hand));
                }
                if (!this.withOffhand) {
                    ModuleSurround.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(ModuleSurround.mc.player.inventory.currentItem));
                }
                this.counter.dispatch(place);
                this.confirmPlace.add(place);
                return;
            }
        }
    }
    
    static {
        ModuleSurround.settingGround = new ValueBoolean("Ground", "Ground", "Only ground places.", true);
        ModuleSurround.settingAutoCenter = new ValueBoolean("Auto-Center", "AutoCenter", "Auto center players to correct place!", false);
        ModuleSurround.settingOffhand = new ValueBoolean("Offhand", "Offhand", "Take obsidian from offhand also!", true);
        ModuleSurround.settingNoForceRotate = new ValueBoolean("No Force Rotate", "NoForceRotate", "Prevents server rotation.", false);
        ModuleSurround.settingRetrace = new ValueBoolean("Retrace", "Retrace", "Retrace for fast place.", true);
        ModuleSurround.settingRotationsCooldown = new ValueNumber("Rotations Cooldown", "RotationsCooldown", "Cooldown for rotations.", 4, 1, 6);
        ModuleSurround.settingTimeOut = new ValueNumber("Time Out", "TimeOut", "The time out delay for disable modules.", 3000, 0, 5000);
        ModuleSurround.settingRotate = new ValueEnum("Rotate", "Rotate", "Rotates for you!", RotationManager.Rotation.SEND);
        ModuleSurround.settingRenderSwing = new ValueBoolean("Render Swing", "RenderSwing", "Render swing when places block!", true);
        ModuleSurround.settingRender = new ValueEnum("Render", "Render", "Render blocks around you before places.", Render.OUTLINE);
        ModuleSurround.settingRenderAlpha = new ValueNumber("Render Alpha", "RenderAlpha", "The alpha of render blocks.", 50, 0, 255);
    }
    
    public enum Render
    {
        SOLID, 
        OUTLINE, 
        NONE;
    }
}

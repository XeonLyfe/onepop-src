//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.player;

import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import com.onepop.api.util.item.SlotUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import com.onepop.client.event.client.ClientTickEvent;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import com.onepop.client.event.network.PacketEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import net.minecraft.init.Blocks;
import com.onepop.client.event.entity.PlayerDamageBlockEvent;
import java.util.Iterator;
import com.onepop.api.util.math.PositionUtil;
import com.onepop.api.util.render.RenderUtil;
import java.awt.Color;
import com.onepop.api.util.client.NullUtil;
import java.util.ArrayList;
import me.rina.turok.util.TurokTick;
import com.onepop.api.util.world.BlockUtil;
import java.util.List;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import com.onepop.api.setting.value.ValueNumber;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Better Mine", tag = "BetterMine", description = "Make your mining better!", category = ModuleCategory.PLAYER)
public class ModuleBetterMine extends Module
{
    public static ValueBoolean settingPerfectTiming;
    public static ValueBoolean settingInstantMine;
    public static ValueBoolean settingQueue;
    public static ValueNumber settingQueueSize;
    public static ValueNumber settingBlockAlpha;
    public static ValueNumber settingLineDamageAlpha;
    public static ValueNumber settingLineSize;
    private BlockPos currentBlock;
    private EnumFacing currentEnumFacing;
    private boolean isBreaking;
    private boolean isCancelled;
    private final List<BlockUtil.BlockDamage> blockList;
    private final TurokTick timerBreak;
    
    public ModuleBetterMine() {
        this.blockList = new ArrayList<BlockUtil.BlockDamage>();
        this.timerBreak = new TurokTick();
    }
    
    @Override
    public void onSetting() {
        ModuleBetterMine.settingQueueSize.setEnabled(ModuleBetterMine.settingQueue.getValue());
        ModuleBetterMine.settingInstantMine.setEnabled(false);
    }
    
    @Override
    public void onRender3D() {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        if (ModuleBetterMine.settingQueue.getValue()) {
            for (final BlockUtil.BlockDamage damage : this.blockList) {
                RenderUtil.drawSolidBlock(this.camera, damage.getPos(), new Color(190, 0, 0, ModuleBetterMine.settingBlockAlpha.getValue().intValue()));
                if (this.currentBlock != null && PositionUtil.collideBlockPos(damage.getPos(), this.currentBlock)) {
                    RenderUtil.drawOutlineBlock(this.camera, damage.getPos(), ModuleBetterMine.settingLineSize.getValue().floatValue(), new Color(255, 0, 0, ModuleBetterMine.settingLineDamageAlpha.getValue().intValue()));
                }
            }
        }
        else if (this.isBreaking) {
            RenderUtil.drawSolidBlock(this.camera, this.currentBlock, new Color(190, 0, 0, ModuleBetterMine.settingBlockAlpha.getValue().intValue()));
            RenderUtil.drawOutlineBlock(this.camera, this.currentBlock, ModuleBetterMine.settingLineSize.getValue().floatValue(), new Color(255, 0, 0, ModuleBetterMine.settingLineDamageAlpha.getValue().intValue()));
        }
    }
    
    @Listener
    public void onDamageBlock(final PlayerDamageBlockEvent event) {
        final EnumFacing facing = event.getFacing();
        final BlockPos position = event.getPos();
        if (BlockUtil.getHardness(position) == -1.0f || BlockUtil.getBlock(position) == Blocks.WEB || ModuleBetterMine.mc.player.isCreative()) {
            return;
        }
        boolean cancel = false;
        if (ModuleBetterMine.settingQueue.getValue()) {
            if (this.blockList.size() < ModuleBetterMine.settingQueueSize.getValue().intValue() && !this.contains(position)) {
                this.blockList.add(new BlockUtil.BlockDamage(position, facing));
                cancel = true;
            }
        }
        else {
            this.blockList.clear();
            this.isBreaking = true;
            this.currentBlock = position;
            this.currentEnumFacing = facing;
            this.timerBreak.reset();
            cancel = true;
        }
        event.setCanceled(cancel);
    }
    
    @Listener
    public void onListenSend(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayerDigging && ModuleBetterMine.settingInstantMine.getValue()) {
            final CPacketPlayerDigging packet = (CPacketPlayerDigging)event.getPacket();
            if (packet.getAction() == CPacketPlayerDigging.Action.START_DESTROY_BLOCK && this.isCancelled) {
                event.setCanceled(true);
            }
        }
    }
    
    @Listener
    public void onRunTick(final ClientTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        if (ModuleBetterMine.mc.player.isCreative()) {
            this.blockList.clear();
            this.currentBlock = null;
            this.isBreaking = false;
            return;
        }
        final int firstPickaxe = this.findFirstItemPickaxe();
        if (ModuleBetterMine.settingQueue.getValue()) {
            if (!this.blockList.isEmpty()) {
                final BlockUtil.BlockDamage damage = this.blockList.get(0);
                final boolean skip = ModuleBetterMine.mc.player.getDistance((double)damage.getPos().x, (double)damage.getPos().y, (double)damage.getPos().z) >= 13.0 || BlockUtil.getBlock(damage.getPos()) == Blocks.AIR;
                if (skip) {
                    if (ModuleBetterMine.settingPerfectTiming.getValue()) {
                        ModuleBetterMine.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(ModuleBetterMine.mc.player.inventory.currentItem));
                    }
                    this.currentBlock = null;
                    this.timerBreak.reset();
                    this.blockList.remove(0);
                }
                else {
                    ModuleBetterMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, damage.getPos(), damage.getFacing()));
                    ModuleBetterMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, damage.getPos(), damage.getFacing()));
                    this.currentBlock = damage.getPos();
                    if (ModuleBetterMine.settingPerfectTiming.getValue() && firstPickaxe != -1 && this.timerBreak.isPassedMS(BlockUtil.getHardness(damage.getPos()) * 10.0f) && ModuleBetterMine.mc.player.getDistance((double)this.currentBlock.x, (double)this.currentBlock.y, (double)this.currentBlock.z) <= ModuleBetterMine.mc.playerController.getBlockReachDistance()) {
                        ModuleBetterMine.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(firstPickaxe));
                    }
                }
            }
            else {
                this.currentBlock = null;
            }
        }
        else if (this.currentBlock != null) {
            if (BlockUtil.getBlock(this.currentBlock) == Blocks.AIR || ModuleBetterMine.mc.player.getDistance((double)this.currentBlock.x, (double)this.currentBlock.y, (double)this.currentBlock.z) >= 13.0) {
                if (ModuleBetterMine.settingPerfectTiming.getValue()) {
                    ModuleBetterMine.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(ModuleBetterMine.mc.player.inventory.currentItem));
                }
                this.timerBreak.reset();
                this.isBreaking = false;
                this.currentBlock = null;
            }
            else {
                ModuleBetterMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.currentBlock, this.currentEnumFacing));
                ModuleBetterMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.currentBlock, this.currentEnumFacing));
                if (ModuleBetterMine.settingPerfectTiming.getValue() && firstPickaxe != -1 && this.timerBreak.isPassedMS(BlockUtil.getHardness(this.currentBlock) * 10.0f) && ModuleBetterMine.mc.player.getDistance((double)this.currentBlock.x, (double)this.currentBlock.y, (double)this.currentBlock.z) <= ModuleBetterMine.mc.playerController.getBlockReachDistance()) {
                    ModuleBetterMine.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(firstPickaxe));
                }
            }
        }
        else {
            this.timerBreak.reset();
        }
    }
    
    public int findFirstItemPickaxe() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final Item item = SlotUtil.getItem(i);
            if (item instanceof ItemPickaxe) {
                slot = i;
                break;
            }
        }
        return slot;
    }
    
    public boolean contains(final BlockPos pos) {
        for (final BlockUtil.BlockDamage damage : this.blockList) {
            if (PositionUtil.collideBlockPos(damage.getPos(), pos)) {
                return true;
            }
        }
        return false;
    }
    
    static {
        ModuleBetterMine.settingPerfectTiming = new ValueBoolean("Perfect Timing", "PerfectTiming", "Sets pickaxe at perfect time for break more fast!", false);
        ModuleBetterMine.settingInstantMine = new ValueBoolean("Instant", "Instant", "Instant mine!", true);
        ModuleBetterMine.settingQueue = new ValueBoolean("Queue", "Queue", "Queue mode!", false);
        ModuleBetterMine.settingQueueSize = new ValueNumber("Size", "Size", "Maximum queue size!", 4, 2, 10);
        ModuleBetterMine.settingBlockAlpha = new ValueNumber("Block Alpha", "BlockAlpha", "Block alpha!", 100, 0, 255);
        ModuleBetterMine.settingLineDamageAlpha = new ValueNumber("Line Damage Alpha", "LineDamageAlpha", "The alpha of current block damage!", 255, 0, 255);
        ModuleBetterMine.settingLineSize = new ValueNumber("Line Size", "LineSize", "LineSize", 1.0f, 1.0f, 5.0f);
    }
}

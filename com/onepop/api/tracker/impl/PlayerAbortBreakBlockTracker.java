// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.tracker.impl;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import com.onepop.api.util.world.BlockUtil;
import com.onepop.api.util.network.PacketUtil;

public class PlayerAbortBreakBlockTracker extends PacketUtil.PacketTracker
{
    private BlockUtil.BlockDamage block;
    
    public PlayerAbortBreakBlockTracker(final BlockUtil.BlockDamage block) {
        super("Abort Break Tracker", (Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, block.getPos(), block.getFacing()));
        this.block = block;
    }
    
    public void setBlock(final BlockUtil.BlockDamage block) {
        this.block = block;
    }
    
    public BlockUtil.BlockDamage getBlock() {
        return this.block;
    }
    
    @Override
    public void onPre() {
    }
    
    @Override
    public void onPost() {
    }
}

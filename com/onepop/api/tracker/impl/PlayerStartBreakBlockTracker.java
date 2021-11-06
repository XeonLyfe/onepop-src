//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.tracker.impl;

import com.onepop.Onepop;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumHand;
import com.onepop.api.util.world.BlockUtil;
import com.onepop.api.util.network.PacketUtil;

public class PlayerStartBreakBlockTracker extends PacketUtil.PacketTracker
{
    private BlockUtil.BlockDamage block;
    private EnumHand hand;
    
    public PlayerStartBreakBlockTracker(final EnumHand hand, final BlockUtil.BlockDamage block) {
        super("Break Block", (Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, block.getPos(), block.getFacing()));
        this.block = block;
        this.hand = hand;
    }
    
    public void setBlock(final BlockUtil.BlockDamage block) {
        this.block = block;
    }
    
    public BlockUtil.BlockDamage getBlock() {
        return this.block;
    }
    
    public void setHand(final EnumHand hand) {
        this.hand = hand;
    }
    
    public EnumHand getHand() {
        return this.hand;
    }
    
    @Override
    public void onPre() {
        if (this.hand != null) {
            Onepop.MC.player.swingArm(this.hand);
        }
    }
    
    @Override
    public void onPost() {
    }
}

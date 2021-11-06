//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.tracker.impl;

import com.onepop.Onepop;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import com.onepop.api.util.network.PacketUtil;

public class RightMouseClickOnBlockTracker extends PacketUtil.PacketTracker
{
    private BlockPos pos;
    private EnumFacing facing;
    private EnumHand hand;
    private float facingX;
    private float facingY;
    private float facingZ;
    
    public RightMouseClickOnBlockTracker(final BlockPos pos, final EnumFacing facing, final EnumHand hand, final float facingX, final float facingY, final float facingZ) {
        super("Right Mouse Click On Block Tracker", (Packet)new CPacketPlayerTryUseItemOnBlock(pos, facing, (hand == null) ? EnumHand.MAIN_HAND : hand, facingX, facingY, facingZ));
        this.pos = pos;
        this.facing = facing;
        this.hand = hand;
        this.facingX = facingX;
        this.facingY = facingY;
        this.facingZ = facingZ;
    }
    
    public void setPos(final BlockPos pos) {
        this.pos = pos;
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
    
    public void setFacing(final EnumFacing facing) {
        this.facing = facing;
    }
    
    public EnumFacing getFacing() {
        return this.facing;
    }
    
    public void setFacingX(final float facingX) {
        this.facingX = facingX;
    }
    
    public float getFacingX() {
        return this.facingX;
    }
    
    public void setFacingY(final float facingY) {
        this.facingY = facingY;
    }
    
    public float getFacingY() {
        return this.facingY;
    }
    
    public void setFacingZ(final float facingZ) {
        this.facingZ = facingZ;
    }
    
    public float getFacingZ() {
        return this.facingZ;
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

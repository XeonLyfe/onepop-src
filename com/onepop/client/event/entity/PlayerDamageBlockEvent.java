// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.event.entity;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import com.onepop.api.event.Event;

public class PlayerDamageBlockEvent extends Event
{
    private BlockPos pos;
    private EnumFacing facing;
    
    public PlayerDamageBlockEvent(final BlockPos pos, final EnumFacing facing) {
        this.pos = pos;
        this.facing = facing;
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
    
    public EnumFacing getFacing() {
        return this.facing;
    }
}

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.event.entity;

import com.onepop.api.event.impl.EventStage;
import net.minecraft.entity.MoverType;
import com.onepop.api.event.Event;

public class PlayerMoveEvent extends Event
{
    private MoverType type;
    public double x;
    public double y;
    public double z;
    
    public PlayerMoveEvent(final MoverType type, final double x, final double y, final double z) {
        super(EventStage.PRE);
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void setType(final MoverType type) {
        this.type = type;
    }
    
    public MoverType getType() {
        return this.type;
    }
    
    public void setX(final double x) {
        this.x = x;
    }
    
    public double getX() {
        return this.x;
    }
    
    public void setY(final double y) {
        this.y = y;
    }
    
    public double getY() {
        return this.y;
    }
    
    public void setZ(final double z) {
        this.z = z;
    }
    
    public double getZ() {
        return this.z;
    }
}

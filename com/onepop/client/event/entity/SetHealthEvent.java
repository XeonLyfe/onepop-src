// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.event.entity;

import com.onepop.api.event.impl.EventStage;
import net.minecraft.entity.player.EntityPlayer;
import com.onepop.api.event.Event;

public class SetHealthEvent extends Event
{
    private EntityPlayer player;
    private float health;
    
    public SetHealthEvent(final float health) {
        super(EventStage.PRE);
        this.health = health;
    }
    
    public void setPlayer(final EntityPlayer player) {
        this.player = player;
    }
    
    public EntityPlayer getPlayer() {
        return this.player;
    }
    
    public void setHealth(final float health) {
        this.health = health;
    }
    
    public float getHealth() {
        return this.health;
    }
}

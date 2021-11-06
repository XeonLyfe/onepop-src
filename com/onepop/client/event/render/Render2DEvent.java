// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.event.render;

import com.onepop.api.event.impl.EventStage;
import com.onepop.api.event.Event;

public class Render2DEvent extends Event
{
    private float partialTicks;
    
    public Render2DEvent(final float partialTicks) {
        super(EventStage.PRE);
        this.partialTicks = partialTicks;
    }
    
    protected void setPartialTicks(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
}

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.event.render;

import com.onepop.api.event.Event;

public class PerspectiveEvent extends Event
{
    private float aspect;
    
    public PerspectiveEvent(final float aspect) {
        this.aspect = aspect;
    }
    
    public float getAspect() {
        return this.aspect;
    }
    
    public void setAspect(final float aspect) {
        this.aspect = aspect;
    }
}

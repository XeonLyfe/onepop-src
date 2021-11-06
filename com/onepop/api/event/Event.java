// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.event;

import com.onepop.api.event.impl.EventStage;

public class Event
{
    private EventStage stage;
    private boolean isCanceled;
    
    public Event() {
        this.stage = EventStage.PRE;
    }
    
    public Event(final EventStage stage) {
        this.stage = stage;
    }
    
    public void setStage(final EventStage stage) {
        this.stage = stage;
    }
    
    public EventStage getStage() {
        return this.stage;
    }
    
    public void setCanceled(final boolean canceled) {
        this.isCanceled = canceled;
    }
    
    public boolean isCanceled() {
        return this.isCanceled;
    }
}

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.event.client;

import com.onepop.api.module.Module;
import com.onepop.api.event.Event;

public class ModuleStatusEvent extends Event
{
    private Module module;
    private boolean newStatus;
    
    public ModuleStatusEvent(final Module module, final boolean enabled) {
        this.module = module;
        this.newStatus = enabled;
    }
    
    public boolean isNewStatus() {
        return this.newStatus;
    }
    
    public Module getModule() {
        return this.module;
    }
}

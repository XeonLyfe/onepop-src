// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.event.client;

import com.onepop.api.event.impl.EventStage;
import com.onepop.api.event.Event;

public class ClientTickEvent extends Event
{
    public ClientTickEvent() {
        super(EventStage.PRE);
    }
}

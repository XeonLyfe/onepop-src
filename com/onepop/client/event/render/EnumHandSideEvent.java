// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.event.render;

import net.minecraft.util.EnumHandSide;
import com.onepop.api.event.Event;

public class EnumHandSideEvent extends Event
{
    private final EnumHandSide handSide;
    
    public EnumHandSideEvent(final EnumHandSide handSide) {
        this.handSide = handSide;
    }
    
    public EnumHandSide getHandSide() {
        return this.handSide;
    }
}

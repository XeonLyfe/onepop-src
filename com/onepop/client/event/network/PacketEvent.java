// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.event.network;

import com.onepop.api.event.impl.EventStage;
import net.minecraft.network.Packet;
import com.onepop.api.event.Event;

public class PacketEvent extends Event
{
    private Packet<?> packet;
    
    public PacketEvent(final Packet<?> packet, final EventStage stage) {
        super(stage);
        this.packet = packet;
    }
    
    public void setPacket(final Packet<?> packet) {
        this.packet = packet;
    }
    
    public Packet<?> getPacket() {
        return this.packet;
    }
    
    public static class Send extends PacketEvent
    {
        public Send(final Packet<?> packet) {
            super(packet, EventStage.PRE);
        }
    }
    
    public static class Receive extends PacketEvent
    {
        public Receive(final Packet<?> packet) {
            super(packet, EventStage.POST);
        }
    }
}

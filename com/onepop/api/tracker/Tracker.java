// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.tracker;

import java.util.Iterator;
import java.util.Collection;
import com.onepop.Onepop;
import me.rina.turok.util.TurokTick;
import com.onepop.api.util.network.PacketUtil;
import java.util.ArrayList;

public class Tracker
{
    private String name;
    private ArrayList<PacketUtil.PacketTracker> queue;
    private TurokTick tick;
    private float delay;
    private boolean isNext;
    private boolean isRegistry;
    
    public Tracker(final String name) {
        this.name = name;
        this.queue = new ArrayList<PacketUtil.PacketTracker>();
        this.tick = new TurokTick();
        this.delay = 250.0f;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setQueue(final ArrayList<PacketUtil.PacketTracker> queue) {
        this.queue = queue;
    }
    
    public ArrayList<PacketUtil.PacketTracker> getQueue() {
        return this.queue;
    }
    
    public void setTick(final TurokTick tick) {
        this.tick = tick;
    }
    
    public TurokTick getTick() {
        return this.tick;
    }
    
    public void setDelay(final float delay) {
        this.delay = delay;
    }
    
    public float getDelay() {
        return this.delay;
    }
    
    public void setNext(final boolean next) {
        this.isNext = next;
    }
    
    public boolean isNext() {
        return this.isNext;
    }
    
    public void setRegistry(final boolean registry) {
        this.isRegistry = registry;
    }
    
    public boolean isRegistry() {
        return this.isRegistry;
    }
    
    public Tracker inject() {
        if (Onepop.getTrackerManager() == null) {
            return this;
        }
        Onepop.getTrackerManager().registry(this);
        return this;
    }
    
    public void clear() {
        this.queue.clear();
    }
    
    public void register() {
        this.isRegistry = true;
        if (!this.queue.isEmpty()) {
            this.queue.clear();
        }
    }
    
    public void unregister() {
        this.isRegistry = false;
        if (!this.queue.isEmpty()) {
            this.queue.clear();
        }
    }
    
    public void send(final PacketUtil.PacketTracker packetTracker) {
        packetTracker.onPre();
        PacketUtil.send(packetTracker.getPacket());
        packetTracker.onPost();
    }
    
    public void join(final PacketUtil.PacketTracker packetTracker) {
        this.queue.add(packetTracker);
    }
    
    public void onUpdate() {
        if (this.queue.isEmpty()) {
            this.tick.reset();
        }
        for (final PacketUtil.PacketTracker packets : new ArrayList(this.queue)) {
            if (this.tick.isPassedMS(this.delay)) {
                if (!packets.isCanceled()) {
                    this.send(packets);
                }
                this.queue.remove(packets);
                this.isNext = true;
                this.tick.reset();
            }
            else {
                this.isNext = false;
            }
        }
    }
}

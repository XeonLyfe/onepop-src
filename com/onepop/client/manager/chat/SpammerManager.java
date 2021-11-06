// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.manager.chat;

import com.onepop.api.util.chat.ChatUtil;
import java.util.Collection;
import java.util.Iterator;
import me.rina.turok.util.TurokTick;
import java.util.ArrayList;
import com.onepop.api.manager.Manager;

public class SpammerManager extends Manager
{
    private ArrayList<String> queue;
    private ArrayList<String> lastQueue;
    private TurokTick tick;
    private float delay;
    private int limit;
    private boolean isNext;
    
    public SpammerManager() {
        super("Spammer", "Manage");
        this.tick = new TurokTick();
        this.queue = new ArrayList<String>();
        this.lastQueue = new ArrayList<String>();
    }
    
    public void setQueue(final ArrayList<String> queue) {
        this.queue = queue;
    }
    
    public ArrayList<String> getQueue() {
        return this.queue;
    }
    
    public void setLastQueue(final ArrayList<String> lastQueue) {
        this.lastQueue = lastQueue;
    }
    
    public ArrayList<String> getLastQueue() {
        return this.lastQueue;
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
    
    public void setLimit(final int limit) {
        this.limit = limit;
    }
    
    public int getLimit() {
        return this.limit;
    }
    
    public void setNext(final boolean next) {
        this.isNext = next;
    }
    
    public boolean isNext() {
        return this.isNext;
    }
    
    public void send(final String message) {
        boolean isAcceptToJoinQueue = true;
        for (final String messages : this.queue) {
            if (messages.equalsIgnoreCase(message)) {
                isAcceptToJoinQueue = false;
            }
        }
        if (isAcceptToJoinQueue) {
            this.queue.add(message);
        }
    }
    
    @Override
    public void onUpdateAll() {
        if (this.queue.size() >= this.limit) {
            this.queue.clear();
        }
        if (this.queue.isEmpty()) {
            this.tick.reset();
        }
        for (final String messages : new ArrayList(this.queue)) {
            if (this.tick.isPassedMS(this.delay * 1000.0f)) {
                this.isNext = true;
                ChatUtil.message(messages);
                this.lastQueue.add(messages);
                this.queue.remove(messages);
                this.tick.reset();
            }
            else {
                this.isNext = false;
            }
        }
    }
}

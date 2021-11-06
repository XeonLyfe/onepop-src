// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.tool;

import java.util.HashMap;

public class CounterTool<T>
{
    private final HashMap<T, Integer> node;
    
    public CounterTool() {
        this.node = new HashMap<T, Integer>();
    }
    
    public void dispatch(final T key) {
        this.node.put(key, (this.getCount(key) == null) ? 1 : (this.getCount(key) + 1));
    }
    
    public void remove(final T key) {
        this.node.remove(key);
    }
    
    public Integer getCount(final T key) {
        return this.node.get(key);
    }
    
    public HashMap<T, Integer> getNode() {
        return this.node;
    }
    
    public void clear() {
        this.node.clear();
    }
}

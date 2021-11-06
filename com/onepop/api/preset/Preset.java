// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.preset;

import me.rina.turok.util.TurokGeneric;
import com.onepop.api.preset.impl.PresetState;

public class Preset
{
    private String name;
    private String data;
    private String path;
    private PresetState state;
    private TurokGeneric<Boolean> current;
    
    public Preset(final String name, final String path, final String data) {
        this.name = name;
        this.data = data;
        this.path = path.toLowerCase();
        this.state = PresetState.OPERABLE;
        this.current = new TurokGeneric<Boolean>(false);
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setData(final String data) {
        this.data = data;
    }
    
    public String getData() {
        return this.data;
    }
    
    public void setPath(final String path) {
        this.path = path;
    }
    
    public String getPath() {
        return this.path;
    }
    
    public void setState(final PresetState state) {
        this.state = state;
    }
    
    public PresetState getState() {
        return this.state;
    }
    
    public void setCurrent(final TurokGeneric<Boolean> current) {
        this.current = current;
    }
    
    public TurokGeneric<Boolean> getCurrent() {
        return this.current;
    }
    
    public void setCurrent(final boolean is) {
        this.current.setValue(is);
    }
    
    public boolean isCurrent() {
        return this.current.getValue();
    }
}

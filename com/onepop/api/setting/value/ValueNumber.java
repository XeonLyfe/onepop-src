// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.setting.value;

import com.onepop.api.setting.Setting;

public class ValueNumber extends Setting
{
    private Number value;
    private Number minimum;
    private Number maximum;
    private Smooth smooth;
    
    public ValueNumber(final String name, final String tag, final String description, final int value, final int minimum, final int maximum) {
        super(name, tag, description);
        this.smooth = Smooth.PRIMITIVE;
        this.value = value;
        this.minimum = minimum;
        this.maximum = maximum;
        this.smooth = Smooth.INTEGER;
    }
    
    public ValueNumber(final String name, final String tag, final String description, final double value, final double minimum, final double maximum) {
        super(name, tag, description);
        this.smooth = Smooth.PRIMITIVE;
        this.value = value;
        this.minimum = minimum;
        this.maximum = maximum;
    }
    
    public ValueNumber(final String name, final String tag, final String description, final float value, final float minimum, final float maximum) {
        super(name, tag, description);
        this.smooth = Smooth.PRIMITIVE;
        this.value = value;
        this.minimum = minimum;
        this.maximum = maximum;
    }
    
    public void setValue(final Number value) {
        this.value = value;
    }
    
    public Number getValue() {
        return this.value;
    }
    
    public void setMinimum(final Number minimum) {
        this.minimum = minimum;
    }
    
    public Number getMinimum() {
        return this.minimum;
    }
    
    public void setMaximum(final Number maximum) {
        this.maximum = maximum;
    }
    
    public Number getMaximum() {
        return this.maximum;
    }
    
    public void setSmooth(final Smooth smooth) {
        this.smooth = smooth;
    }
    
    public Smooth getSmooth() {
        return this.smooth;
    }
    
    public enum Smooth
    {
        PRIMITIVE, 
        INTEGER;
    }
}

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.component.impl;

public class ComponentSetting<T>
{
    private String name;
    private String tag;
    private String description;
    private T value;
    private T minimum;
    private T maximum;
    
    public ComponentSetting(final String name, final String tag, final String description, final T value) {
        this.name = name;
        this.tag = tag;
        this.description = description;
        this.value = value;
    }
    
    public ComponentSetting(final String name, final String tag, final String description, final T value, final T minimum, final T maximum) {
        this.name = name;
        this.tag = tag;
        this.description = description;
        this.value = value;
        this.minimum = minimum;
        this.maximum = maximum;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setTag(final String tag) {
        this.tag = tag;
    }
    
    public String getTag() {
        return this.tag;
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setValue(final T value) {
        this.value = value;
    }
    
    public T getValue() {
        return this.value;
    }
    
    public T getMinimum() {
        return this.minimum;
    }
    
    public T getMaximum() {
        return this.maximum;
    }
}

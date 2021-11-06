// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.setting.value;

import com.onepop.api.setting.Setting;

public class ValueBoolean extends Setting
{
    private boolean value;
    
    public ValueBoolean(final String name, final String tag, final String description, final boolean value) {
        super(name, tag, description);
        this.value = value;
    }
    
    public void setValue(final boolean value) {
        this.value = value;
    }
    
    public boolean getValue() {
        return this.value;
    }
}

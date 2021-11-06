// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.setting.value;

import com.onepop.api.setting.Setting;

public class ValueString extends Setting
{
    private String format;
    private String value;
    
    public ValueString(final String name, final String tag, final String description, final String value) {
        super(name, tag, description);
        this.format = "";
        this.value = value;
    }
    
    public ValueString addFormat(final String format) {
        this.format = format;
        return this;
    }
    
    public void setFormat(final String format) {
        this.format = format;
    }
    
    public String getFormat() {
        return this.format;
    }
    
    public void setValue(final String value) {
        this.value = value;
    }
    
    public String getValue() {
        return this.value;
    }
}

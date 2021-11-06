// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.setting;

import com.onepop.api.setting.impl.SettingStructure;

public class Setting implements SettingStructure
{
    private String name;
    private String tag;
    private String description;
    private boolean enabled;
    private boolean old;
    
    public Setting(final String name, final String tag, final String description) {
        this.enabled = true;
        this.old = true;
        this.name = name;
        this.tag = tag;
        this.description = description;
    }
    
    @Override
    public void setName(final String name) {
        this.name = name;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public void setTag(final String tag) {
        this.tag = tag;
    }
    
    @Override
    public String getTag() {
        return this.tag;
    }
    
    @Override
    public void setDescription(final String description) {
        this.description = description;
    }
    
    @Override
    public String getDescription() {
        return this.description;
    }
    
    @Override
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
    
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
    
    @Override
    public void setOld(final boolean enabled) {
        this.old = enabled;
    }
    
    @Override
    public boolean getOld() {
        return this.old;
    }
    
    public void updateSetting() {
        this.old = this.isEnabled();
    }
}

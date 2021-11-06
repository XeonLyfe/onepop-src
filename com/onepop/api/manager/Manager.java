// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.manager;

import com.onepop.api.manager.impl.ManageStructure;

public class Manager implements ManageStructure
{
    private String name;
    private String description;
    
    public Manager(final String name, final String description) {
        this.name = name;
        this.description = description;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    @Override
    public void onUpdateAll() {
    }
}

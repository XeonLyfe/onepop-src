// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.module.impl;

public enum ModuleCategory
{
    COMBAT("Combat"), 
    PLAYER("Player"), 
    RENDER("Render"), 
    EXPLOIT("Exploit"), 
    MISC("Misc"), 
    CLIENT("Client");
    
    private final String tag;
    
    private ModuleCategory(final String tag) {
        this.tag = tag;
    }
    
    public String getTag() {
        return this.tag;
    }
}

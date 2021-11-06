// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.social;

import com.onepop.api.social.type.SocialType;

public class Social
{
    public String name;
    public SocialType type;
    
    public Social(final String name) {
        this.name = name;
        this.type = SocialType.UNKNOWN;
    }
    
    public Social(final String name, final SocialType type) {
        this.name = name;
        this.type = type;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setType(final SocialType type) {
        this.type = type;
    }
    
    public SocialType getType() {
        return this.type;
    }
}

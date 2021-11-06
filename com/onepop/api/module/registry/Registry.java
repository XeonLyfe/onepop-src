// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.module.registry;

import com.onepop.api.module.impl.ModuleCategory;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

@Retention(RetentionPolicy.RUNTIME)
public @interface Registry {
    String name();
    
    String tag();
    
    String description() default "";
    
    ModuleCategory category();
}

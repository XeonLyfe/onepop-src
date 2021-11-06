// 
// Decompiled by Procyon v0.6-prerelease
// 

package org.spongepowered.asm.mixin.injection;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

@Retention(RetentionPolicy.RUNTIME)
public @interface Slice {
    String id() default "";
    
    At from() default @At("HEAD");
    
    At to() default @At("TAIL");
}

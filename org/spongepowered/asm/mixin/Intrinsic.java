// 
// Decompiled by Procyon v0.6-prerelease
// 

package org.spongepowered.asm.mixin;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.CLASS)
public @interface Intrinsic {
    boolean displace() default false;
}

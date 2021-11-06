// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.annotation.concurrent;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.CLASS)
public @interface GuardedBy {
    String value();
}

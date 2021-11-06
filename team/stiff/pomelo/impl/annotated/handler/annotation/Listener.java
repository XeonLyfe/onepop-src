// 
// Decompiled by Procyon v0.6-prerelease
// 

package team.stiff.pomelo.impl.annotated.handler.annotation;

import team.stiff.pomelo.handler.ListenerPriority;
import team.stiff.pomelo.filter.EventFilter;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Listener {
    Class<? extends EventFilter>[] filters() default {};
    
    ListenerPriority priority() default ListenerPriority.NORMAL;
}

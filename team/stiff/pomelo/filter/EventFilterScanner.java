// 
// Decompiled by Procyon v0.6-prerelease
// 

package team.stiff.pomelo.filter;

import java.util.Set;

public interface EventFilterScanner<T>
{
    Set<EventFilter> scan(final T p0);
}

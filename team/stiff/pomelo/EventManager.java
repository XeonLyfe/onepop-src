// 
// Decompiled by Procyon v0.6-prerelease
// 

package team.stiff.pomelo;

public interface EventManager
{
     <E> E dispatchEvent(final E p0);
    
    boolean isRegisteredListener(final Object p0);
    
    boolean addEventListener(final Object p0);
    
    boolean removeEventListener(final Object p0);
}

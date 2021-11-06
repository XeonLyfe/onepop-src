// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.driver.unix.solaris.kstat;

import com.sun.jna.platform.unix.solaris.LibKstat;
import oshi.util.platform.unix.solaris.KstatUtil;
import oshi.util.tuples.Pair;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class SystemPages
{
    private SystemPages() {
    }
    
    public static Pair<Long, Long> queryAvailableTotal() {
        long memAvailable = 0L;
        long memTotal = 0L;
        try (final KstatUtil.KstatChain kc = KstatUtil.openChain()) {
            final LibKstat.Kstat ksp = KstatUtil.KstatChain.lookup(null, -1, "system_pages");
            if (ksp != null && KstatUtil.KstatChain.read(ksp)) {
                memAvailable = KstatUtil.dataLookupLong(ksp, "availrmem");
                memTotal = KstatUtil.dataLookupLong(ksp, "physmem");
            }
        }
        return new Pair<Long, Long>(memAvailable, memTotal);
    }
}

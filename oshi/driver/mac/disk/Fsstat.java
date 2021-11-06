// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.driver.mac.disk;

import com.sun.jna.Native;
import java.nio.charset.StandardCharsets;
import com.sun.jna.platform.mac.SystemB;
import java.util.HashMap;
import java.util.Map;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class Fsstat
{
    private Fsstat() {
    }
    
    public static Map<String, String> queryPartitionToMountMap() {
        final Map<String, String> mountPointMap = new HashMap<String, String>();
        final int numfs = SystemB.INSTANCE.getfsstat64((SystemB.Statfs[])null, 0, 0);
        final SystemB.Statfs[] fs = new SystemB.Statfs[numfs];
        SystemB.INSTANCE.getfsstat64(fs, numfs * new SystemB.Statfs().size(), 16);
        for (final SystemB.Statfs f : fs) {
            final String mntFrom = Native.toString(f.f_mntfromname, StandardCharsets.UTF_8);
            mountPointMap.put(mntFrom.replace("/dev/", ""), Native.toString(f.f_mntonname, StandardCharsets.UTF_8));
        }
        return mountPointMap;
    }
}

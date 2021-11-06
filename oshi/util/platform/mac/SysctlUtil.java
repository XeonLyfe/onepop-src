// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.util.platform.mac;

import org.slf4j.LoggerFactory;
import com.sun.jna.Structure;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Memory;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.platform.mac.SystemB;
import org.slf4j.Logger;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class SysctlUtil
{
    private static final Logger LOG;
    private static final String SYSCTL_FAIL = "Failed syctl call: {}, Error code: {}";
    
    private SysctlUtil() {
    }
    
    public static int sysctl(final String name, final int def) {
        final IntByReference size = new IntByReference(SystemB.INT_SIZE);
        final Pointer p = (Pointer)new Memory((long)size.getValue());
        if (0 != SystemB.INSTANCE.sysctlbyname(name, p, size, (Pointer)null, 0)) {
            SysctlUtil.LOG.error("Failed sysctl call: {}, Error code: {}", (Object)name, (Object)Native.getLastError());
            return def;
        }
        return p.getInt(0L);
    }
    
    public static long sysctl(final String name, final long def) {
        final IntByReference size = new IntByReference(SystemB.UINT64_SIZE);
        final Pointer p = (Pointer)new Memory((long)size.getValue());
        if (0 != SystemB.INSTANCE.sysctlbyname(name, p, size, (Pointer)null, 0)) {
            SysctlUtil.LOG.error("Failed syctl call: {}, Error code: {}", (Object)name, (Object)Native.getLastError());
            return def;
        }
        return p.getLong(0L);
    }
    
    public static String sysctl(final String name, final String def) {
        final IntByReference size = new IntByReference();
        if (0 != SystemB.INSTANCE.sysctlbyname(name, (Pointer)null, size, (Pointer)null, 0)) {
            SysctlUtil.LOG.error("Failed syctl call: {}, Error code: {}", (Object)name, (Object)Native.getLastError());
            return def;
        }
        final Pointer p = (Pointer)new Memory(size.getValue() + 1L);
        if (0 != SystemB.INSTANCE.sysctlbyname(name, p, size, (Pointer)null, 0)) {
            SysctlUtil.LOG.error("Failed syctl call: {}, Error code: {}", (Object)name, (Object)Native.getLastError());
            return def;
        }
        return p.getString(0L);
    }
    
    public static boolean sysctl(final String name, final Structure struct) {
        if (0 != SystemB.INSTANCE.sysctlbyname(name, struct.getPointer(), new IntByReference(struct.size()), (Pointer)null, 0)) {
            SysctlUtil.LOG.error("Failed syctl call: {}, Error code: {}", (Object)name, (Object)Native.getLastError());
            return false;
        }
        struct.read();
        return true;
    }
    
    public static Memory sysctl(final String name) {
        final IntByReference size = new IntByReference();
        if (0 != SystemB.INSTANCE.sysctlbyname(name, (Pointer)null, size, (Pointer)null, 0)) {
            SysctlUtil.LOG.error("Failed syctl call: {}, Error code: {}", (Object)name, (Object)Native.getLastError());
            return null;
        }
        final Memory m = new Memory((long)size.getValue());
        if (0 != SystemB.INSTANCE.sysctlbyname(name, (Pointer)m, size, (Pointer)null, 0)) {
            SysctlUtil.LOG.error("Failed syctl call: {}, Error code: {}", (Object)name, (Object)Native.getLastError());
            return null;
        }
        return m;
    }
    
    static {
        LOG = LoggerFactory.getLogger((Class)SysctlUtil.class);
    }
}

// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.util.platform.unix.openbsd;

import org.slf4j.LoggerFactory;
import oshi.util.ParseUtil;
import oshi.util.ExecutingCommand;
import com.sun.jna.Structure;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Memory;
import com.sun.jna.ptr.IntByReference;
import oshi.jna.platform.unix.openbsd.OpenBsdLibc;
import org.slf4j.Logger;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class OpenBsdSysctlUtil
{
    private static final Logger LOG;
    private static final String SYSCTL_FAIL = "Failed sysctl call: {}, Error code: {}";
    
    private OpenBsdSysctlUtil() {
    }
    
    public static int sysctl(final int[] name, final int def) {
        final IntByReference size = new IntByReference(OpenBsdLibc.INT_SIZE);
        final Pointer p = (Pointer)new Memory((long)size.getValue());
        if (0 != OpenBsdLibc.INSTANCE.sysctl(name, name.length, p, size, null, 0)) {
            OpenBsdSysctlUtil.LOG.error("Failed sysctl call: {}, Error code: {}", (Object)name, (Object)Native.getLastError());
            return def;
        }
        return p.getInt(0L);
    }
    
    public static long sysctl(final int[] name, final long def) {
        final IntByReference size = new IntByReference(OpenBsdLibc.UINT64_SIZE);
        final Pointer p = (Pointer)new Memory((long)size.getValue());
        if (0 != OpenBsdLibc.INSTANCE.sysctl(name, name.length, p, size, null, 0)) {
            OpenBsdSysctlUtil.LOG.warn("Failed sysctl call: {}, Error code: {}", (Object)name, (Object)Native.getLastError());
            return def;
        }
        return p.getLong(0L);
    }
    
    public static String sysctl(final int[] name, final String def) {
        final IntByReference size = new IntByReference();
        if (0 != OpenBsdLibc.INSTANCE.sysctl(name, name.length, null, size, null, 0)) {
            OpenBsdSysctlUtil.LOG.warn("Failed sysctl call: {}, Error code: {}", (Object)name, (Object)Native.getLastError());
            return def;
        }
        final Pointer p = (Pointer)new Memory(size.getValue() + 1L);
        if (0 != OpenBsdLibc.INSTANCE.sysctl(name, name.length, p, size, null, 0)) {
            OpenBsdSysctlUtil.LOG.warn("Failed sysctl call: {}, Error code: {}", (Object)name, (Object)Native.getLastError());
            return def;
        }
        return p.getString(0L);
    }
    
    public static boolean sysctl(final int[] name, final Structure struct) {
        if (0 != OpenBsdLibc.INSTANCE.sysctl(name, name.length, struct.getPointer(), new IntByReference(struct.size()), null, 0)) {
            OpenBsdSysctlUtil.LOG.error("Failed sysctl call: {}, Error code: {}", (Object)name, (Object)Native.getLastError());
            return false;
        }
        struct.read();
        return true;
    }
    
    public static Memory sysctl(final int[] name) {
        final IntByReference size = new IntByReference();
        if (0 != OpenBsdLibc.INSTANCE.sysctl(name, name.length, null, size, null, 0)) {
            OpenBsdSysctlUtil.LOG.error("Failed sysctl call: {}, Error code: {}", (Object)name, (Object)Native.getLastError());
            return null;
        }
        final Memory m = new Memory((long)size.getValue());
        if (0 != OpenBsdLibc.INSTANCE.sysctl(name, name.length, (Pointer)m, size, null, 0)) {
            OpenBsdSysctlUtil.LOG.error("Failed sysctl call: {}, Error code: {}", (Object)name, (Object)Native.getLastError());
            return null;
        }
        return m;
    }
    
    public static int sysctl(final String name, final int def) {
        return ParseUtil.parseIntOrDefault(ExecutingCommand.getFirstAnswer("sysctl -n " + name), def);
    }
    
    public static long sysctl(final String name, final long def) {
        return ParseUtil.parseLongOrDefault(ExecutingCommand.getFirstAnswer("sysctl -n " + name), def);
    }
    
    public static String sysctl(final String name, final String def) {
        final String v = ExecutingCommand.getFirstAnswer("sysctl -n " + name);
        if (null == v || v.isEmpty()) {
            return def;
        }
        return v;
    }
    
    static {
        LOG = LoggerFactory.getLogger((Class)OpenBsdSysctlUtil.class);
    }
}

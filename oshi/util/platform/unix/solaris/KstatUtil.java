// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.util.platform.unix.solaris;

import java.util.ArrayList;
import java.util.List;
import oshi.util.Util;
import org.slf4j.LoggerFactory;
import com.sun.jna.Pointer;
import oshi.util.FormatUtil;
import com.sun.jna.Native;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.locks.ReentrantLock;
import com.sun.jna.platform.unix.solaris.LibKstat;
import org.slf4j.Logger;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class KstatUtil
{
    private static final Logger LOG;
    private static final LibKstat KS;
    private static final LibKstat.KstatCtl KC;
    private static final ReentrantLock CHAIN;
    
    private KstatUtil() {
    }
    
    public static KstatChain openChain() {
        return new KstatChain();
    }
    
    public static String dataLookupString(final LibKstat.Kstat ksp, final String name) {
        if (ksp.ks_type != 1 && ksp.ks_type != 4) {
            throw new IllegalArgumentException("Not a kstat_named or kstat_timer kstat.");
        }
        final Pointer p = KstatUtil.KS.kstat_data_lookup(ksp, name);
        if (p == null) {
            KstatUtil.LOG.debug("Failed to lookup kstat value for key {}", (Object)name);
            return "";
        }
        final LibKstat.KstatNamed data = new LibKstat.KstatNamed(p);
        switch (data.data_type) {
            case 0: {
                return Native.toString(data.value.charc, StandardCharsets.UTF_8);
            }
            case 1: {
                return Integer.toString(data.value.i32);
            }
            case 2: {
                return FormatUtil.toUnsignedString(data.value.ui32);
            }
            case 3: {
                return Long.toString(data.value.i64);
            }
            case 4: {
                return FormatUtil.toUnsignedString(data.value.ui64);
            }
            case 9: {
                return data.value.str.addr.getString(0L);
            }
            default: {
                KstatUtil.LOG.error("Unimplemented kstat data type {}", (Object)data.data_type);
                return "";
            }
        }
    }
    
    public static long dataLookupLong(final LibKstat.Kstat ksp, final String name) {
        if (ksp.ks_type != 1 && ksp.ks_type != 4) {
            throw new IllegalArgumentException("Not a kstat_named or kstat_timer kstat.");
        }
        final Pointer p = KstatUtil.KS.kstat_data_lookup(ksp, name);
        if (p == null) {
            if (KstatUtil.LOG.isErrorEnabled()) {
                KstatUtil.LOG.error("Failed lo lookup kstat value on {}:{}:{} for key {}", new Object[] { Native.toString(ksp.ks_module, StandardCharsets.US_ASCII), ksp.ks_instance, Native.toString(ksp.ks_name, StandardCharsets.US_ASCII), name });
            }
            return 0L;
        }
        final LibKstat.KstatNamed data = new LibKstat.KstatNamed(p);
        switch (data.data_type) {
            case 1: {
                return data.value.i32;
            }
            case 2: {
                return FormatUtil.getUnsignedInt(data.value.ui32);
            }
            case 3: {
                return data.value.i64;
            }
            case 4: {
                return data.value.ui64;
            }
            default: {
                KstatUtil.LOG.error("Unimplemented or non-numeric kstat data type {}", (Object)data.data_type);
                return 0L;
            }
        }
    }
    
    static {
        LOG = LoggerFactory.getLogger((Class)KstatUtil.class);
        KS = LibKstat.INSTANCE;
        KC = KstatUtil.KS.kstat_open();
        CHAIN = new ReentrantLock();
    }
    
    public static final class KstatChain implements AutoCloseable
    {
        private KstatChain() {
            KstatUtil.CHAIN.lock();
            update();
        }
        
        public static boolean read(final LibKstat.Kstat ksp) {
            int retry = 0;
            while (0 > KstatUtil.KS.kstat_read(KstatUtil.KC, ksp, (Pointer)null)) {
                if (11 != Native.getLastError() || 5 <= ++retry) {
                    if (KstatUtil.LOG.isDebugEnabled()) {
                        KstatUtil.LOG.debug("Failed to read kstat {}:{}:{}", new Object[] { Native.toString(ksp.ks_module, StandardCharsets.US_ASCII), ksp.ks_instance, Native.toString(ksp.ks_name, StandardCharsets.US_ASCII) });
                    }
                    return false;
                }
                Util.sleep(8 << retry);
            }
            return true;
        }
        
        public static LibKstat.Kstat lookup(final String module, final int instance, final String name) {
            return KstatUtil.KS.kstat_lookup(KstatUtil.KC, module, instance, name);
        }
        
        public static List<LibKstat.Kstat> lookupAll(final String module, final int instance, final String name) {
            final List<LibKstat.Kstat> kstats = new ArrayList<LibKstat.Kstat>();
            for (LibKstat.Kstat ksp = KstatUtil.KS.kstat_lookup(KstatUtil.KC, module, instance, name); ksp != null; ksp = ksp.next()) {
                if ((module == null || module.equals(Native.toString(ksp.ks_module, StandardCharsets.US_ASCII))) && (instance < 0 || instance == ksp.ks_instance) && (name == null || name.equals(Native.toString(ksp.ks_name, StandardCharsets.US_ASCII)))) {
                    kstats.add(ksp);
                }
            }
            return kstats;
        }
        
        public static int update() {
            return KstatUtil.KS.kstat_chain_update(KstatUtil.KC);
        }
        
        @Override
        public void close() {
            KstatUtil.CHAIN.unlock();
        }
    }
}

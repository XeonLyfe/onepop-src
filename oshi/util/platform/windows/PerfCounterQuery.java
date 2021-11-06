// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.util.platform.windows;

import java.util.HashSet;
import org.slf4j.LoggerFactory;
import com.sun.jna.platform.win32.Win32Exception;
import com.sun.jna.platform.win32.PdhUtil;
import com.sun.jna.platform.win32.COM.WbemcliUtil;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import oshi.annotation.concurrent.GuardedBy;
import java.util.Set;
import org.slf4j.Logger;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class PerfCounterQuery
{
    private static final Logger LOG;
    @GuardedBy("failedQueryCacheLock")
    private static final Set<String> failedQueryCache;
    private static final ReentrantLock failedQueryCacheLock;
    private static final ConcurrentHashMap<String, String> localizeCache;
    public static final String TOTAL_INSTANCE = "_Total";
    public static final String TOTAL_INSTANCES = "*_Total";
    public static final String NOT_TOTAL_INSTANCE = "^_Total";
    public static final String NOT_TOTAL_INSTANCES = "^*_Total";
    
    private PerfCounterQuery() {
    }
    
    public static <T extends Enum<T>> Map<T, Long> queryValues(final Class<T> propertyEnum, final String perfObject, final String perfWmiClass) {
        if (!PerfCounterQuery.failedQueryCache.contains(perfObject)) {
            PerfCounterQuery.failedQueryCacheLock.lock();
            try {
                if (!PerfCounterQuery.failedQueryCache.contains(perfObject)) {
                    final Map<T, Long> valueMap = queryValuesFromPDH(propertyEnum, perfObject);
                    if (!valueMap.isEmpty()) {
                        return valueMap;
                    }
                    PerfCounterQuery.LOG.warn("Disabling further attempts to query {}.", (Object)perfObject);
                    PerfCounterQuery.failedQueryCache.add(perfObject);
                }
            }
            finally {
                PerfCounterQuery.failedQueryCacheLock.unlock();
            }
        }
        return queryValuesFromWMI(propertyEnum, perfWmiClass);
    }
    
    public static <T extends Enum<T>> Map<T, Long> queryValuesFromPDH(final Class<T> propertyEnum, final String perfObject) {
        final T[] props = propertyEnum.getEnumConstants();
        final String perfObjectLocalized = localize(perfObject);
        final EnumMap<T, PerfDataUtil.PerfCounter> counterMap = new EnumMap<T, PerfDataUtil.PerfCounter>(propertyEnum);
        final EnumMap<T, Long> valueMap = new EnumMap<T, Long>(propertyEnum);
        try (final PerfCounterQueryHandler pdhQueryHandler = new PerfCounterQueryHandler()) {
            for (final T prop : props) {
                final PerfDataUtil.PerfCounter counter = PerfDataUtil.createCounter(perfObjectLocalized, ((PdhCounterProperty)prop).getInstance(), ((PdhCounterProperty)prop).getCounter());
                counterMap.put(prop, counter);
                if (!pdhQueryHandler.addCounterToQuery(counter)) {
                    final EnumMap<T, Long> enumMap = valueMap;
                    pdhQueryHandler.close();
                    return enumMap;
                }
            }
            if (0L < pdhQueryHandler.updateQuery()) {
                for (final T prop : props) {
                    valueMap.put(prop, pdhQueryHandler.queryCounter(counterMap.get(prop)));
                }
            }
        }
        return valueMap;
    }
    
    public static <T extends Enum<T>> Map<T, Long> queryValuesFromWMI(final Class<T> propertyEnum, final String wmiClass) {
        final WbemcliUtil.WmiQuery<T> query = (WbemcliUtil.WmiQuery<T>)new WbemcliUtil.WmiQuery(wmiClass, (Class)propertyEnum);
        final WbemcliUtil.WmiResult<T> result = WmiQueryHandler.createInstance().queryWMI(query);
        final EnumMap<T, Long> valueMap = new EnumMap<T, Long>(propertyEnum);
        if (result.getResultCount() > 0) {
            for (final T prop : propertyEnum.getEnumConstants()) {
                switch (result.getCIMType((Enum)prop)) {
                    case 18: {
                        valueMap.put(prop, (long)WmiUtil.getUint16(result, prop, 0));
                        break;
                    }
                    case 19: {
                        valueMap.put(prop, WmiUtil.getUint32asLong(result, prop, 0));
                        break;
                    }
                    case 21: {
                        valueMap.put(prop, WmiUtil.getUint64(result, prop, 0));
                        break;
                    }
                    case 101: {
                        valueMap.put(prop, WmiUtil.getDateTime(result, prop, 0).toInstant().toEpochMilli());
                        break;
                    }
                    default: {
                        throw new ClassCastException("Unimplemented CIM Type Mapping.");
                    }
                }
            }
        }
        return valueMap;
    }
    
    public static String localize(final String perfObject) {
        return PerfCounterQuery.localizeCache.computeIfAbsent(perfObject, k -> localizeUsingPerfIndex(k));
    }
    
    private static String localizeUsingPerfIndex(final String perfObject) {
        String localized = perfObject;
        try {
            localized = PdhUtil.PdhLookupPerfNameByIndex((String)null, PdhUtil.PdhLookupPerfIndexByEnglishName(perfObject));
        }
        catch (Win32Exception e) {
            PerfCounterQuery.LOG.warn("Unable to locate English counter names in registry Perflib 009. Assuming English counters. Error {}. {}", (Object)String.format("0x%x", e.getHR().intValue()), (Object)"See https://support.microsoft.com/en-us/help/300956/how-to-manually-rebuild-performance-counter-library-values");
        }
        catch (PdhUtil.PdhException e2) {
            PerfCounterQuery.LOG.warn("Unable to localize {} performance counter.  Error {}.", (Object)perfObject, (Object)String.format("0x%x", e2.getErrorCode()));
        }
        if (localized.isEmpty()) {
            return perfObject;
        }
        PerfCounterQuery.LOG.debug("Localized {} to {}", (Object)perfObject, (Object)localized);
        return localized;
    }
    
    static {
        LOG = LoggerFactory.getLogger((Class)PerfCounterQuery.class);
        failedQueryCache = new HashSet<String>();
        failedQueryCacheLock = new ReentrantLock();
        localizeCache = new ConcurrentHashMap<String, String>();
    }
    
    public interface PdhCounterProperty
    {
        String getInstance();
        
        String getCounter();
    }
}

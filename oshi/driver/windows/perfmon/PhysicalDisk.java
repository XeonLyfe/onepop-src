// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.driver.windows.perfmon;

import oshi.util.platform.windows.PerfCounterWildcardQuery;
import java.util.Map;
import java.util.List;
import oshi.util.tuples.Pair;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class PhysicalDisk
{
    private static final String PHYSICAL_DISK = "PhysicalDisk";
    private static final String WIN32_PERF_RAW_DATA_PERF_DISK_PHYSICAL_DISK_WHERE_NOT_NAME_TOTAL = "Win32_PerfRawData_PerfDisk_PhysicalDisk WHERE NOT Name=\"_Total\"";
    
    private PhysicalDisk() {
    }
    
    public static Pair<List<String>, Map<PhysicalDiskProperty, List<Long>>> queryDiskCounters() {
        return PerfCounterWildcardQuery.queryInstancesAndValues(PhysicalDiskProperty.class, "PhysicalDisk", "Win32_PerfRawData_PerfDisk_PhysicalDisk WHERE NOT Name=\"_Total\"");
    }
    
    public enum PhysicalDiskProperty implements PerfCounterWildcardQuery.PdhCounterWildcardProperty
    {
        NAME("^_Total"), 
        DISKREADSPERSEC("Disk Reads/sec"), 
        DISKREADBYTESPERSEC("Disk Read Bytes/sec"), 
        DISKWRITESPERSEC("Disk Writes/sec"), 
        DISKWRITEBYTESPERSEC("Disk Write Bytes/sec"), 
        CURRENTDISKQUEUELENGTH("Current Disk Queue Length"), 
        PERCENTDISKTIME("% Disk Time");
        
        private final String counter;
        
        private PhysicalDiskProperty(final String counter) {
            this.counter = counter;
        }
        
        @Override
        public String getCounter() {
            return this.counter;
        }
    }
}

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
public final class ProcessInformation
{
    private static final String WIN32_PROCESS = "Win32_Process";
    private static final String PROCESS = "Process";
    private static final String WIN32_PROCESS_WHERE_NOT_NAME_LIKE_TOTAL = "Win32_Process WHERE NOT Name LIKE\"%_Total\"";
    
    private ProcessInformation() {
    }
    
    public static Pair<List<String>, Map<ProcessPerformanceProperty, List<Long>>> queryProcessCounters() {
        return PerfCounterWildcardQuery.queryInstancesAndValues(ProcessPerformanceProperty.class, "Process", "Win32_Process WHERE NOT Name LIKE\"%_Total\"");
    }
    
    public static Pair<List<String>, Map<HandleCountProperty, List<Long>>> queryHandles() {
        return PerfCounterWildcardQuery.queryInstancesAndValues(HandleCountProperty.class, "Process", "Win32_Process");
    }
    
    public enum ProcessPerformanceProperty implements PerfCounterWildcardQuery.PdhCounterWildcardProperty
    {
        NAME("^*_Total"), 
        PRIORITY("Priority Base"), 
        CREATIONDATE("Elapsed Time"), 
        PROCESSID("ID Process"), 
        PARENTPROCESSID("Creating Process ID"), 
        READTRANSFERCOUNT("IO Read Bytes/sec"), 
        WRITETRANSFERCOUNT("IO Write Bytes/sec"), 
        PRIVATEPAGECOUNT("Working Set - Private"), 
        PAGEFAULTSPERSEC("Page Faults/sec");
        
        private final String counter;
        
        private ProcessPerformanceProperty(final String counter) {
            this.counter = counter;
        }
        
        @Override
        public String getCounter() {
            return this.counter;
        }
    }
    
    public enum HandleCountProperty implements PerfCounterWildcardQuery.PdhCounterWildcardProperty
    {
        NAME("_Total"), 
        HANDLECOUNT("Handle Count");
        
        private final String counter;
        
        private HandleCountProperty(final String counter) {
            this.counter = counter;
        }
        
        @Override
        public String getCounter() {
            return this.counter;
        }
    }
}

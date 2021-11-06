// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.software.os;

import oshi.util.Util;
import oshi.annotation.concurrent.Immutable;
import oshi.driver.unix.Xwininfo;
import oshi.driver.unix.Who;
import oshi.util.ParseUtil;
import oshi.util.ExecutingCommand;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Objects;
import java.util.function.Function;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.List;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public interface OperatingSystem
{
    String getFamily();
    
    String getManufacturer();
    
    OSVersionInfo getVersionInfo();
    
    FileSystem getFileSystem();
    
    InternetProtocolStats getInternetProtocolStats();
    
    default List<OSProcess> getProcesses() {
        return this.getProcesses(null, null, 0);
    }
    
    List<OSProcess> getProcesses(final Predicate<OSProcess> p0, final Comparator<OSProcess> p1, final int p2);
    
    @Deprecated
    default List<OSProcess> getProcesses(final int limit, final ProcessSort sort) {
        return this.getProcesses(null, convertSortToComparator(sort), limit);
    }
    
    default List<OSProcess> getProcesses(final Collection<Integer> pids) {
        return pids.stream().map((Function<? super Integer, ?>)this::getProcess).filter(Objects::nonNull).filter((Predicate<? super Object>)ProcessFiltering.VALID_PROCESS).collect((Collector<? super Object, ?, List<OSProcess>>)Collectors.toList());
    }
    
    OSProcess getProcess(final int p0);
    
    @Deprecated
    default List<OSProcess> getChildProcesses(final int parentPid, final int limit, final ProcessSort sort) {
        return this.getChildProcesses(parentPid, null, convertSortToComparator(sort), limit);
    }
    
    List<OSProcess> getChildProcesses(final int p0, final Predicate<OSProcess> p1, final Comparator<OSProcess> p2, final int p3);
    
    List<OSProcess> getDescendantProcesses(final int p0, final Predicate<OSProcess> p1, final Comparator<OSProcess> p2, final int p3);
    
    int getProcessId();
    
    int getProcessCount();
    
    int getThreadCount();
    
    int getBitness();
    
    long getSystemUptime();
    
    long getSystemBootTime();
    
    default boolean isElevated() {
        return 0 == ParseUtil.parseIntOrDefault(ExecutingCommand.getFirstAnswer("id -u"), -1);
    }
    
    NetworkParams getNetworkParams();
    
    default OSService[] getServices() {
        return new OSService[0];
    }
    
    default List<OSSession> getSessions() {
        return Who.queryWho();
    }
    
    default List<OSDesktopWindow> getDesktopWindows(final boolean visibleOnly) {
        return Xwininfo.queryXWindows(visibleOnly);
    }
    
    @Deprecated
    public enum ProcessSort
    {
        CPU, 
        MEMORY, 
        OLDEST, 
        NEWEST, 
        PID, 
        PARENTPID, 
        NAME;
    }
    
    public static final class ProcessFiltering
    {
        public static final Predicate<OSProcess> ALL_PROCESSES;
        public static final Predicate<OSProcess> VALID_PROCESS;
        public static final Predicate<OSProcess> NO_PARENT;
        public static final Predicate<OSProcess> BITNESS_64;
        public static final Predicate<OSProcess> BITNESS_32;
        
        private ProcessFiltering() {
        }
        
        static {
            ALL_PROCESSES = (p -> true);
            VALID_PROCESS = (p -> !p.getState().equals(OSProcess.State.INVALID));
            NO_PARENT = (p -> p.getParentProcessID() == p.getProcessID());
            BITNESS_64 = (p -> p.getBitness() == 64);
            BITNESS_32 = (p -> p.getBitness() == 32);
        }
    }
    
    public static final class ProcessSorting
    {
        public static final Comparator<OSProcess> NO_SORTING;
        public static final Comparator<OSProcess> CPU_DESC;
        public static final Comparator<OSProcess> RSS_DESC;
        public static final Comparator<OSProcess> UPTIME_ASC;
        public static final Comparator<OSProcess> UPTIME_DESC;
        public static final Comparator<OSProcess> PID_ASC;
        public static final Comparator<OSProcess> PARENTPID_ASC;
        public static final Comparator<OSProcess> NAME_ASC;
        
        private ProcessSorting() {
        }
        
        private static Comparator<OSProcess> convertSortToComparator(final ProcessSort sort) {
            if (sort == null) {
                return ProcessSorting.NO_SORTING;
            }
            switch (sort) {
                case CPU: {
                    return ProcessSorting.CPU_DESC;
                }
                case MEMORY: {
                    return ProcessSorting.RSS_DESC;
                }
                case OLDEST: {
                    return ProcessSorting.UPTIME_DESC;
                }
                case NEWEST: {
                    return ProcessSorting.UPTIME_ASC;
                }
                case PID: {
                    return ProcessSorting.PID_ASC;
                }
                case PARENTPID: {
                    return ProcessSorting.PARENTPID_ASC;
                }
                case NAME: {
                    return ProcessSorting.NAME_ASC;
                }
                default: {
                    throw new IllegalArgumentException("Unimplemented enum type: " + sort.toString());
                }
            }
        }
        
        static {
            NO_SORTING = ((p1, p2) -> 0);
            CPU_DESC = Comparator.comparingDouble(OSProcess::getProcessCpuLoadCumulative).reversed();
            RSS_DESC = Comparator.comparingLong(OSProcess::getResidentSetSize).reversed();
            UPTIME_ASC = Comparator.comparingLong(OSProcess::getUpTime);
            UPTIME_DESC = ProcessSorting.UPTIME_ASC.reversed();
            PID_ASC = Comparator.comparingInt(OSProcess::getProcessID);
            PARENTPID_ASC = Comparator.comparingInt(OSProcess::getParentProcessID);
            NAME_ASC = Comparator.comparing((Function<? super OSProcess, ?>)OSProcess::getName, (Comparator<? super Object>)String.CASE_INSENSITIVE_ORDER);
        }
    }
    
    @Immutable
    public static class OSVersionInfo
    {
        private final String version;
        private final String codeName;
        private final String buildNumber;
        private final String versionStr;
        
        public OSVersionInfo(final String version, final String codeName, final String buildNumber) {
            this.version = version;
            this.codeName = codeName;
            this.buildNumber = buildNumber;
            final StringBuilder sb = new StringBuilder((this.getVersion() != null) ? this.getVersion() : "unknown");
            if (!Util.isBlank(this.getCodeName())) {
                sb.append(" (").append(this.getCodeName()).append(')');
            }
            if (!Util.isBlank(this.getBuildNumber())) {
                sb.append(" build ").append(this.getBuildNumber());
            }
            this.versionStr = sb.toString();
        }
        
        public String getVersion() {
            return this.version;
        }
        
        public String getCodeName() {
            return this.codeName;
        }
        
        public String getBuildNumber() {
            return this.buildNumber;
        }
        
        @Override
        public String toString() {
            return this.versionStr;
        }
    }
}

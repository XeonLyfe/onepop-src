// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.software.os.unix.aix;

import java.io.File;
import oshi.software.os.OSService;
import oshi.software.os.NetworkParams;
import oshi.driver.unix.aix.Uptime;
import oshi.driver.unix.aix.Who;
import oshi.jna.platform.unix.aix.AixLibc;
import java.util.Iterator;
import java.util.Map;
import oshi.util.ParseUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Collection;
import oshi.software.os.OSProcess;
import java.util.List;
import oshi.software.os.InternetProtocolStats;
import oshi.software.os.FileSystem;
import com.sun.jna.Native;
import oshi.util.ExecutingCommand;
import oshi.util.Util;
import oshi.software.os.OperatingSystem;
import oshi.util.tuples.Pair;
import oshi.driver.unix.aix.perfstat.PerfstatProcess;
import oshi.util.Memoizer;
import oshi.driver.unix.aix.perfstat.PerfstatConfig;
import com.sun.jna.platform.unix.aix.Perfstat;
import java.util.function.Supplier;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.software.common.AbstractOperatingSystem;

@ThreadSafe
public class AixOperatingSystem extends AbstractOperatingSystem
{
    private final Supplier<Perfstat.perfstat_partition_config_t> config;
    Supplier<Perfstat.perfstat_process_t[]> procCpu;
    private static final long BOOTTIME;
    
    public AixOperatingSystem() {
        this.config = Memoizer.memoize(PerfstatConfig::queryConfig);
        this.procCpu = Memoizer.memoize(PerfstatProcess::queryProcesses, Memoizer.defaultExpiration());
    }
    
    public String queryManufacturer() {
        return "IBM";
    }
    
    public Pair<String, OperatingSystem.OSVersionInfo> queryFamilyVersionInfo() {
        final Perfstat.perfstat_partition_config_t cfg = this.config.get();
        final String systemName = System.getProperty("os.name");
        final String archName = System.getProperty("os.arch");
        String versionNumber = System.getProperty("os.version");
        if (Util.isBlank(versionNumber)) {
            versionNumber = ExecutingCommand.getFirstAnswer("oslevel");
        }
        String releaseNumber = Native.toString(cfg.OSBuild);
        if (Util.isBlank(releaseNumber)) {
            releaseNumber = ExecutingCommand.getFirstAnswer("oslevel -s");
        }
        else {
            final int idx = releaseNumber.lastIndexOf(32);
            if (idx > 0 && idx < releaseNumber.length()) {
                releaseNumber = releaseNumber.substring(idx + 1);
            }
        }
        return new Pair<String, OperatingSystem.OSVersionInfo>(systemName, new OperatingSystem.OSVersionInfo(versionNumber, archName, releaseNumber));
    }
    
    @Override
    protected int queryBitness(final int jvmBitness) {
        if (jvmBitness == 64) {
            return 64;
        }
        return ((this.config.get().conf & 0x800000) > 0) ? 64 : 32;
    }
    
    @Override
    public FileSystem getFileSystem() {
        return new AixFileSystem();
    }
    
    @Override
    public InternetProtocolStats getInternetProtocolStats() {
        return new AixInternetProtocolStats();
    }
    
    public List<OSProcess> queryAllProcesses() {
        return this.getProcessListFromPS("ps -A -o st,pid,ppid,user,uid,group,gid,thcount,pri,vsize,rssize,etime,time,comm,pagein,args", -1);
    }
    
    public List<OSProcess> queryChildProcesses(final int parentPid) {
        final List<OSProcess> allProcs = this.queryAllProcesses();
        final Set<Integer> descendantPids = AbstractOperatingSystem.getChildrenOrDescendants(allProcs, parentPid, false);
        return allProcs.stream().filter(p -> descendantPids.contains(p.getProcessID())).collect((Collector<? super Object, ?, List<OSProcess>>)Collectors.toList());
    }
    
    public List<OSProcess> queryDescendantProcesses(final int parentPid) {
        final List<OSProcess> allProcs = this.queryAllProcesses();
        final Set<Integer> descendantPids = AbstractOperatingSystem.getChildrenOrDescendants(allProcs, parentPid, true);
        return allProcs.stream().filter(p -> descendantPids.contains(p.getProcessID())).collect((Collector<? super Object, ?, List<OSProcess>>)Collectors.toList());
    }
    
    @Override
    public OSProcess getProcess(final int pid) {
        final List<OSProcess> procs = this.getProcessListFromPS("ps -o st,pid,ppid,user,uid,group,gid,thcount,pri,vsize,rssize,etime,time,comm,pagein,args -p ", pid);
        if (procs.isEmpty()) {
            return null;
        }
        return procs.get(0);
    }
    
    private List<OSProcess> getProcessListFromPS(final String psCommand, final int pid) {
        final Perfstat.perfstat_process_t[] perfstat = this.procCpu.get();
        final List<String> procList = ExecutingCommand.runNative(psCommand + ((pid < 0) ? "" : Integer.valueOf(pid)));
        if (procList.isEmpty() || procList.size() < 2) {
            return Collections.emptyList();
        }
        final Map<Integer, Pair<Long, Long>> cpuMap = new HashMap<Integer, Pair<Long, Long>>();
        for (final Perfstat.perfstat_process_t stat : perfstat) {
            cpuMap.put((int)stat.pid, new Pair<Long, Long>((long)stat.ucpu_time, (long)stat.scpu_time));
        }
        procList.remove(0);
        final List<OSProcess> procs = new ArrayList<OSProcess>();
        for (final String proc : procList) {
            final String[] split = ParseUtil.whitespaces.split(proc.trim(), 16);
            if (split.length == 16) {
                procs.add(new AixOSProcess((pid < 0) ? ParseUtil.parseIntOrDefault(split[1], 0) : pid, split, cpuMap, this.procCpu));
            }
        }
        return procs;
    }
    
    @Override
    public int getProcessId() {
        return AixLibc.INSTANCE.getpid();
    }
    
    @Override
    public int getProcessCount() {
        return this.procCpu.get().length;
    }
    
    @Override
    public int getThreadCount() {
        long tc = 0L;
        for (final Perfstat.perfstat_process_t proc : this.procCpu.get()) {
            tc += proc.num_threads;
        }
        return (int)tc;
    }
    
    @Override
    public long getSystemUptime() {
        return System.currentTimeMillis() / 1000L - AixOperatingSystem.BOOTTIME;
    }
    
    @Override
    public long getSystemBootTime() {
        return AixOperatingSystem.BOOTTIME;
    }
    
    private static long querySystemBootTimeMillis() {
        final long bootTime = Who.queryBootTime();
        if (bootTime >= 1000L) {
            return bootTime;
        }
        return System.currentTimeMillis() - Uptime.queryUpTime();
    }
    
    @Override
    public NetworkParams getNetworkParams() {
        return new AixNetworkParams();
    }
    
    @Override
    public OSService[] getServices() {
        final List<OSService> services = new ArrayList<OSService>();
        final List<String> systemServicesInfoList = ExecutingCommand.runNative("lssrc -a");
        if (systemServicesInfoList.size() > 1) {
            systemServicesInfoList.remove(0);
            for (final String systemService : systemServicesInfoList) {
                final String[] serviceSplit = ParseUtil.whitespaces.split(systemService.trim());
                if (systemService.contains("active")) {
                    if (serviceSplit.length == 4) {
                        services.add(new OSService(serviceSplit[0], ParseUtil.parseIntOrDefault(serviceSplit[2], 0), OSService.State.RUNNING));
                    }
                    else {
                        if (serviceSplit.length != 3) {
                            continue;
                        }
                        services.add(new OSService(serviceSplit[0], ParseUtil.parseIntOrDefault(serviceSplit[1], 0), OSService.State.RUNNING));
                    }
                }
                else {
                    if (!systemService.contains("inoperative")) {
                        continue;
                    }
                    services.add(new OSService(serviceSplit[0], 0, OSService.State.STOPPED));
                }
            }
        }
        final File dir = new File("/etc/rc.d/init.d");
        final File[] listFiles;
        if (dir.exists() && dir.isDirectory() && (listFiles = dir.listFiles()) != null) {
            for (final File file : listFiles) {
                final String installedService = ExecutingCommand.getFirstAnswer(file.getAbsolutePath() + " status");
                if (installedService.contains("running")) {
                    services.add(new OSService(file.getName(), ParseUtil.parseLastInt(installedService, 0), OSService.State.RUNNING));
                }
                else {
                    services.add(new OSService(file.getName(), 0, OSService.State.STOPPED));
                }
            }
        }
        return services.toArray(new OSService[0]);
    }
    
    static {
        BOOTTIME = querySystemBootTimeMillis() / 1000L;
    }
}

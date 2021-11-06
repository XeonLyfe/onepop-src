// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.software.os.unix.solaris;

import java.util.Iterator;
import java.io.File;
import java.util.ArrayList;
import oshi.software.os.OSService;
import oshi.software.os.NetworkParams;
import com.sun.jna.platform.unix.solaris.LibKstat;
import oshi.util.platform.unix.solaris.KstatUtil;
import oshi.driver.linux.proc.ProcessStat;
import oshi.jna.platform.unix.solaris.SolarisLibc;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Collection;
import oshi.software.os.OSProcess;
import oshi.driver.unix.solaris.Who;
import oshi.software.os.OSSession;
import java.util.List;
import oshi.software.os.InternetProtocolStats;
import oshi.software.os.FileSystem;
import oshi.util.ExecutingCommand;
import oshi.util.ParseUtil;
import oshi.software.os.OperatingSystem;
import oshi.util.tuples.Pair;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.software.common.AbstractOperatingSystem;

@ThreadSafe
public class SolarisOperatingSystem extends AbstractOperatingSystem
{
    private static final String PS_FIELDS = "s,pid,ppid,user,uid,group,gid,nlwp,pri,vsz,rss,etime,time,comm,args";
    private static final String PROCESS_LIST_FOR_PID_COMMAND = "ps -o s,pid,ppid,user,uid,group,gid,nlwp,pri,vsz,rss,etime,time,comm,args -p ";
    private static final String PROCESS_LIST_COMMAND = "ps -eo s,pid,ppid,user,uid,group,gid,nlwp,pri,vsz,rss,etime,time,comm,args";
    private static final long BOOTTIME;
    
    public String queryManufacturer() {
        return "Oracle";
    }
    
    public Pair<String, OperatingSystem.OSVersionInfo> queryFamilyVersionInfo() {
        final String[] split = ParseUtil.whitespaces.split(ExecutingCommand.getFirstAnswer("uname -rv"));
        final String version = split[0];
        String buildNumber = null;
        if (split.length > 1) {
            buildNumber = split[1];
        }
        return new Pair<String, OperatingSystem.OSVersionInfo>("SunOS", new OperatingSystem.OSVersionInfo(version, "Solaris", buildNumber));
    }
    
    @Override
    protected int queryBitness(final int jvmBitness) {
        if (jvmBitness == 64) {
            return 64;
        }
        return ParseUtil.parseIntOrDefault(ExecutingCommand.getFirstAnswer("isainfo -b"), 32);
    }
    
    @Override
    public FileSystem getFileSystem() {
        return new SolarisFileSystem();
    }
    
    @Override
    public InternetProtocolStats getInternetProtocolStats() {
        return new SolarisInternetProtocolStats();
    }
    
    @Override
    public List<OSSession> getSessions() {
        return SolarisOperatingSystem.USE_WHO_COMMAND ? super.getSessions() : Who.queryUtxent();
    }
    
    @Override
    public OSProcess getProcess(final int pid) {
        final List<OSProcess> procs = getProcessListFromPS("ps -o s,pid,ppid,user,uid,group,gid,nlwp,pri,vsz,rss,etime,time,comm,args -p ", pid);
        if (procs.isEmpty()) {
            return null;
        }
        return procs.get(0);
    }
    
    public List<OSProcess> queryAllProcesses() {
        return queryAllProcessesFromPS();
    }
    
    public List<OSProcess> queryChildProcesses(final int parentPid) {
        final List<OSProcess> allProcs = queryAllProcessesFromPS();
        final Set<Integer> descendantPids = AbstractOperatingSystem.getChildrenOrDescendants(allProcs, parentPid, false);
        return allProcs.stream().filter(p -> descendantPids.contains(p.getProcessID())).collect((Collector<? super Object, ?, List<OSProcess>>)Collectors.toList());
    }
    
    public List<OSProcess> queryDescendantProcesses(final int parentPid) {
        final List<OSProcess> allProcs = queryAllProcessesFromPS();
        final Set<Integer> descendantPids = AbstractOperatingSystem.getChildrenOrDescendants(allProcs, parentPid, true);
        return allProcs.stream().filter(p -> descendantPids.contains(p.getProcessID())).collect((Collector<? super Object, ?, List<OSProcess>>)Collectors.toList());
    }
    
    private static List<OSProcess> queryAllProcessesFromPS() {
        return getProcessListFromPS("ps -eo s,pid,ppid,user,uid,group,gid,nlwp,pri,vsz,rss,etime,time,comm,args", -1);
    }
    
    private static List<OSProcess> getProcessListFromPS(final String psCommand, final int pid) {
        final List<String> procList = (pid < 0) ? ExecutingCommand.runNative(psCommand) : ExecutingCommand.runNative(psCommand + pid);
        final List<String> procList2 = (pid < 0) ? ExecutingCommand.runNative("prstat -v 1 1") : ExecutingCommand.runNative("prstat -v -p " + pid + " 1 1");
        final Map<Integer, String[]> processMap = SolarisOSProcess.parseAndMergePSandPrstatInfo(procList, 1, 15, procList2, false);
        return processMap.entrySet().stream().map(e -> new SolarisOSProcess(e.getKey(), e.getValue())).collect((Collector<? super Object, ?, List<OSProcess>>)Collectors.toList());
    }
    
    @Override
    public int getProcessId() {
        return SolarisLibc.INSTANCE.getpid();
    }
    
    @Override
    public int getProcessCount() {
        return ProcessStat.getPidFiles().length;
    }
    
    @Override
    public int getThreadCount() {
        final List<String> threadList = ExecutingCommand.runNative("ps -eLo pid");
        if (!threadList.isEmpty()) {
            return threadList.size() - 1;
        }
        return this.getProcessCount();
    }
    
    @Override
    public long getSystemUptime() {
        return querySystemUptime();
    }
    
    private static long querySystemUptime() {
        try (final KstatUtil.KstatChain kc = KstatUtil.openChain()) {
            final LibKstat.Kstat ksp = KstatUtil.KstatChain.lookup("unix", 0, "system_misc");
            if (ksp != null) {
                try (final KstatUtil.KstatChain kc = (KstatUtil.KstatChain)(ksp.ks_snaptime / 1000000000L)) {}
                return;
            }
        }
        return 0L;
    }
    
    @Override
    public long getSystemBootTime() {
        return SolarisOperatingSystem.BOOTTIME;
    }
    
    private static long querySystemBootTime() {
        try (final KstatUtil.KstatChain kc = KstatUtil.openChain()) {
            final LibKstat.Kstat ksp = KstatUtil.KstatChain.lookup("unix", 0, "system_misc");
            if (ksp != null && KstatUtil.KstatChain.read(ksp)) {
                try (final KstatUtil.KstatChain kc = (KstatUtil.KstatChain)KstatUtil.dataLookupLong(ksp, "boot_time")) {}
                return;
            }
        }
        return System.currentTimeMillis() / 1000L - querySystemUptime();
    }
    
    @Override
    public NetworkParams getNetworkParams() {
        return new SolarisNetworkParams();
    }
    
    @Override
    public OSService[] getServices() {
        final List<OSService> services = new ArrayList<OSService>();
        final List<String> legacySvcs = new ArrayList<String>();
        final File dir = new File("/etc/init.d");
        final File[] listFiles;
        if (dir.exists() && dir.isDirectory() && (listFiles = dir.listFiles()) != null) {
            for (final File f : listFiles) {
                legacySvcs.add(f.getName());
            }
        }
        final List<String> svcs = ExecutingCommand.runNative("svcs -p");
        for (final String line : svcs) {
            if (line.startsWith("online")) {
                final int delim = line.lastIndexOf(":/");
                if (delim <= 0) {
                    continue;
                }
                String name = line.substring(delim + 1);
                if (name.endsWith(":default")) {
                    name = name.substring(0, name.length() - 8);
                }
                services.add(new OSService(name, 0, OSService.State.STOPPED));
            }
            else if (line.startsWith(" ")) {
                final String[] split = ParseUtil.whitespaces.split(line.trim());
                if (split.length != 3) {
                    continue;
                }
                services.add(new OSService(split[2], ParseUtil.parseIntOrDefault(split[1], 0), OSService.State.RUNNING));
            }
            else {
                if (!line.startsWith("legacy_run")) {
                    continue;
                }
                for (final String svc : legacySvcs) {
                    if (line.endsWith(svc)) {
                        services.add(new OSService(svc, 0, OSService.State.STOPPED));
                        break;
                    }
                }
            }
        }
        return services.toArray(new OSService[0]);
    }
    
    static {
        BOOTTIME = querySystemBootTime();
    }
}

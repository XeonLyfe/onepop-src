// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.software.os.unix.aix;

import java.util.HashMap;
import java.util.Collections;
import java.util.ArrayList;
import oshi.software.os.OSThread;
import oshi.util.ParseUtil;
import java.util.Iterator;
import java.util.List;
import oshi.util.ExecutingCommand;
import oshi.util.LsofUtil;
import oshi.driver.unix.aix.perfstat.PerfstatCpu;
import oshi.util.Memoizer;
import oshi.util.tuples.Pair;
import java.util.Map;
import com.sun.jna.platform.unix.aix.Perfstat;
import oshi.software.os.OSProcess;
import java.util.function.Supplier;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.software.common.AbstractOSProcess;

@ThreadSafe
public class AixOSProcess extends AbstractOSProcess
{
    private Supplier<Integer> bitness;
    private final Supplier<Long> affinityMask;
    private String name;
    private String path;
    private String commandLine;
    private String user;
    private String userID;
    private String group;
    private String groupID;
    private OSProcess.State state;
    private int parentProcessID;
    private int threadCount;
    private int priority;
    private long virtualSize;
    private long residentSetSize;
    private long kernelTime;
    private long userTime;
    private long startTime;
    private long upTime;
    private long bytesRead;
    private long bytesWritten;
    private long majorFaults;
    private Supplier<Perfstat.perfstat_process_t[]> procCpu;
    
    public AixOSProcess(final int pid, final String[] split, final Map<Integer, Pair<Long, Long>> cpuMap, final Supplier<Perfstat.perfstat_process_t[]> procCpu) {
        super(pid);
        this.bitness = Memoizer.memoize(this::queryBitness);
        this.affinityMask = Memoizer.memoize(PerfstatCpu::queryCpuAffinityMask, Memoizer.defaultExpiration());
        this.path = "";
        this.state = OSProcess.State.INVALID;
        this.procCpu = procCpu;
        this.updateAttributes(split, cpuMap);
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public String getPath() {
        return this.path;
    }
    
    @Override
    public String getCommandLine() {
        return this.commandLine;
    }
    
    @Override
    public String getCurrentWorkingDirectory() {
        return LsofUtil.getCwd(this.getProcessID());
    }
    
    @Override
    public String getUser() {
        return this.user;
    }
    
    @Override
    public String getUserID() {
        return this.userID;
    }
    
    @Override
    public String getGroup() {
        return this.group;
    }
    
    @Override
    public String getGroupID() {
        return this.groupID;
    }
    
    @Override
    public OSProcess.State getState() {
        return this.state;
    }
    
    @Override
    public int getParentProcessID() {
        return this.parentProcessID;
    }
    
    @Override
    public int getThreadCount() {
        return this.threadCount;
    }
    
    @Override
    public int getPriority() {
        return this.priority;
    }
    
    @Override
    public long getVirtualSize() {
        return this.virtualSize;
    }
    
    @Override
    public long getResidentSetSize() {
        return this.residentSetSize;
    }
    
    @Override
    public long getKernelTime() {
        return this.kernelTime;
    }
    
    @Override
    public long getUserTime() {
        return this.userTime;
    }
    
    @Override
    public long getUpTime() {
        return this.upTime;
    }
    
    @Override
    public long getStartTime() {
        return this.startTime;
    }
    
    @Override
    public long getBytesRead() {
        return this.bytesRead;
    }
    
    @Override
    public long getBytesWritten() {
        return this.bytesWritten;
    }
    
    @Override
    public long getOpenFiles() {
        return LsofUtil.getOpenFiles(this.getProcessID());
    }
    
    @Override
    public int getBitness() {
        return this.bitness.get();
    }
    
    private int queryBitness() {
        final List<String> pflags = ExecutingCommand.runNative("pflags " + this.getProcessID());
        for (final String line : pflags) {
            if (line.contains("data model")) {
                if (line.contains("LP32")) {
                    return 32;
                }
                if (line.contains("LP64")) {
                    return 64;
                }
                continue;
            }
        }
        return 0;
    }
    
    @Override
    public long getAffinityMask() {
        long mask = 0L;
        final List<String> processAffinityInfoList = ExecutingCommand.runNative("ps -m -o THREAD -p " + this.getProcessID());
        if (processAffinityInfoList.size() > 2) {
            processAffinityInfoList.remove(0);
            processAffinityInfoList.remove(0);
            for (final String processAffinityInfo : processAffinityInfoList) {
                final String[] threadInfoSplit = ParseUtil.whitespaces.split(processAffinityInfo.trim());
                if (threadInfoSplit.length > 13 && threadInfoSplit[4].charAt(0) != 'Z') {
                    if (threadInfoSplit[11].charAt(0) == '-') {
                        return this.affinityMask.get();
                    }
                    final int affinity = ParseUtil.parseIntOrDefault(threadInfoSplit[11], 0);
                    mask |= 1L << affinity;
                }
            }
        }
        return mask;
    }
    
    @Override
    public List<OSThread> getThreadDetails() {
        final List<String> threadListInfoPs = ExecutingCommand.runNative("ps -m -o THREAD -p " + this.getProcessID());
        if (threadListInfoPs.size() > 2) {
            final List<OSThread> threads = new ArrayList<OSThread>();
            threadListInfoPs.remove(0);
            threadListInfoPs.remove(0);
            for (final String threadInfo : threadListInfoPs) {
                final String[] threadInfoSplit = ParseUtil.whitespaces.split(threadInfo.trim());
                if (threadInfoSplit.length == 13) {
                    final String[] split = { threadInfoSplit[3], threadInfoSplit[4], threadInfoSplit[6] };
                    threads.add(new AixOSThread(this.getProcessID(), split));
                }
            }
            return threads;
        }
        return Collections.emptyList();
    }
    
    @Override
    public long getMajorFaults() {
        return this.majorFaults;
    }
    
    @Override
    public boolean updateAttributes() {
        final Perfstat.perfstat_process_t[] perfstat = this.procCpu.get();
        final List<String> procList = ExecutingCommand.runNative("ps -o s,pid,ppid,user,uid,group,gid,nlwp,pri,vsz,rss,etime,time,comm,args -p " + this.getProcessID());
        final Map<Integer, Pair<Long, Long>> cpuMap = new HashMap<Integer, Pair<Long, Long>>();
        for (final Perfstat.perfstat_process_t stat : perfstat) {
            cpuMap.put((int)stat.pid, new Pair<Long, Long>((long)stat.ucpu_time, (long)stat.scpu_time));
        }
        if (procList.size() > 1) {
            final String[] split = ParseUtil.whitespaces.split(procList.get(1).trim(), 15);
            if (split.length == 15) {
                return this.updateAttributes(split, cpuMap);
            }
        }
        this.state = OSProcess.State.INVALID;
        return false;
    }
    
    private boolean updateAttributes(final String[] split, final Map<Integer, Pair<Long, Long>> cpuMap) {
        final long now = System.currentTimeMillis();
        this.state = getStateFromOutput(split[0].charAt(0));
        this.parentProcessID = ParseUtil.parseIntOrDefault(split[2], 0);
        this.user = split[3];
        this.userID = split[4];
        this.group = split[5];
        this.groupID = split[6];
        this.threadCount = ParseUtil.parseIntOrDefault(split[7], 0);
        this.priority = ParseUtil.parseIntOrDefault(split[8], 0);
        this.virtualSize = ParseUtil.parseLongOrDefault(split[9], 0L) << 10;
        this.residentSetSize = ParseUtil.parseLongOrDefault(split[10], 0L) << 10;
        final long elapsedTime = ParseUtil.parseDHMSOrDefault(split[11], 0L);
        if (cpuMap.containsKey(this.getProcessID())) {
            final Pair<Long, Long> userSystem = cpuMap.get(this.getProcessID());
            this.userTime = userSystem.getA();
            this.kernelTime = userSystem.getB();
        }
        else {
            this.userTime = ParseUtil.parseDHMSOrDefault(split[12], 0L);
            this.kernelTime = 0L;
        }
        this.upTime = ((elapsedTime < 1L) ? 1L : elapsedTime);
        while (this.upTime < this.userTime + this.kernelTime) {
            this.upTime += 500L;
        }
        this.startTime = now - this.upTime;
        this.name = split[13];
        this.majorFaults = ParseUtil.parseLongOrDefault(split[14], 0L);
        this.path = ParseUtil.whitespaces.split(split[15])[0];
        this.commandLine = split[15];
        return true;
    }
    
    static OSProcess.State getStateFromOutput(final char stateValue) {
        OSProcess.State state = null;
        switch (stateValue) {
            case 'O': {
                state = OSProcess.State.INVALID;
                break;
            }
            case 'A':
            case 'R': {
                state = OSProcess.State.RUNNING;
                break;
            }
            case 'I': {
                state = OSProcess.State.WAITING;
                break;
            }
            case 'S':
            case 'W': {
                state = OSProcess.State.SLEEPING;
                break;
            }
            case 'Z': {
                state = OSProcess.State.ZOMBIE;
                break;
            }
            case 'T': {
                state = OSProcess.State.STOPPED;
                break;
            }
            default: {
                state = OSProcess.State.OTHER;
                break;
            }
        }
        return state;
    }
}

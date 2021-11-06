// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.software.os.unix.solaris;

import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import oshi.software.os.OSThread;
import oshi.util.ParseUtil;
import java.util.Iterator;
import java.util.List;
import oshi.util.ExecutingCommand;
import oshi.util.LsofUtil;
import oshi.util.Memoizer;
import oshi.software.os.OSProcess;
import java.util.function.Supplier;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.software.common.AbstractOSProcess;

@ThreadSafe
public class SolarisOSProcess extends AbstractOSProcess
{
    private Supplier<Integer> bitness;
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
    private long contextSwitches;
    
    public SolarisOSProcess(final int pid, final String[] split) {
        super(pid);
        this.bitness = Memoizer.memoize(this::queryBitness);
        this.path = "";
        this.state = OSProcess.State.INVALID;
        this.updateAttributes(split);
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
    public long getContextSwitches() {
        return this.contextSwitches;
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
        long bitMask = 0L;
        final String cpuset = ExecutingCommand.getFirstAnswer("pbind -q " + this.getProcessID());
        if (cpuset.isEmpty()) {
            final List<String> allProcs = ExecutingCommand.runNative("psrinfo");
            for (final String proc : allProcs) {
                final String[] split = ParseUtil.whitespaces.split(proc);
                final int bitToSet = ParseUtil.parseIntOrDefault(split[0], -1);
                if (bitToSet >= 0) {
                    bitMask |= 1L << bitToSet;
                }
            }
            return bitMask;
        }
        if (cpuset.endsWith(".") && cpuset.contains("strongly bound to processor(s)")) {
            final String parse = cpuset.substring(0, cpuset.length() - 1);
            final String[] split2 = ParseUtil.whitespaces.split(parse);
            for (int i = split2.length - 1; i >= 0; --i) {
                final int bitToSet2 = ParseUtil.parseIntOrDefault(split2[i], -1);
                if (bitToSet2 < 0) {
                    break;
                }
                bitMask |= 1L << bitToSet2;
            }
        }
        return bitMask;
    }
    
    @Override
    public List<OSThread> getThreadDetails() {
        final List<String> threadListInfo1 = ExecutingCommand.runNative("ps -o lwp,s,etime,stime,time,addr,pri -p " + this.getProcessID());
        final List<String> threadListInfo2 = ExecutingCommand.runNative("prstat -L -v -p " + this.getProcessID() + " 1 1");
        final Map<Integer, String[]> threadMap = parseAndMergePSandPrstatInfo(threadListInfo1, 0, 7, threadListInfo2, true);
        if (threadMap.keySet().size() > 1) {
            return threadMap.entrySet().stream().map(entry -> new SolarisOSThread(this.getProcessID(), entry.getValue())).collect((Collector<? super Object, ?, List<OSThread>>)Collectors.toList());
        }
        return Collections.emptyList();
    }
    
    @Override
    public boolean updateAttributes() {
        final int pid = this.getProcessID();
        final List<String> procList = ExecutingCommand.runNative("ps -o s,pid,ppid,user,uid,group,gid,nlwp,pri,vsz,rss,etime,time,comm,args -p " + pid);
        final List<String> procList2 = ExecutingCommand.runNative("prstat -v -p " + pid + " 1 1");
        final Map<Integer, String[]> processMap = parseAndMergePSandPrstatInfo(procList, 1, 15, procList2, false);
        if (processMap.containsKey(pid)) {
            return this.updateAttributes(processMap.get(this.getProcessID()));
        }
        this.state = OSProcess.State.INVALID;
        return false;
    }
    
    private boolean updateAttributes(final String[] split) {
        final long now = System.currentTimeMillis();
        this.state = getStateFromOutput(split[0].charAt(0));
        this.parentProcessID = ParseUtil.parseIntOrDefault(split[2], 0);
        this.user = split[3];
        this.userID = split[4];
        this.group = split[5];
        this.groupID = split[6];
        this.threadCount = ParseUtil.parseIntOrDefault(split[7], 0);
        this.priority = ParseUtil.parseIntOrDefault(split[8], 0);
        this.virtualSize = ParseUtil.parseLongOrDefault(split[9], 0L) * 1024L;
        this.residentSetSize = ParseUtil.parseLongOrDefault(split[10], 0L) * 1024L;
        final long elapsedTime = ParseUtil.parseDHMSOrDefault(split[11], 0L);
        this.upTime = ((elapsedTime < 1L) ? 1L : elapsedTime);
        this.startTime = now - this.upTime;
        this.kernelTime = 0L;
        this.userTime = ParseUtil.parseDHMSOrDefault(split[12], 0L);
        this.path = split[13];
        this.name = this.path.substring(this.path.lastIndexOf(47) + 1);
        this.commandLine = split[14];
        final long nonVoluntaryContextSwitches = ParseUtil.parseLongOrDefault(split[15], 0L);
        final long voluntaryContextSwitches = ParseUtil.parseLongOrDefault(split[16], 0L);
        this.contextSwitches = voluntaryContextSwitches + nonVoluntaryContextSwitches;
        return true;
    }
    
    static Map<Integer, String[]> parseAndMergePSandPrstatInfo(final List<String> psInfo, final int psKeyIndex, final int psLength, final List<String> prstatInfo, final boolean useTid) {
        final Map<Integer, String[]> map = new HashMap<Integer, String[]>();
        if (psInfo.size() > 1) {
            psInfo.stream().skip(1L).forEach(info -> {
                final String[] psSplit = ParseUtil.whitespaces.split(info.trim(), psLength);
                final String[] mergedSplit = new String[psLength + 2];
                if (psSplit.length == psLength) {
                    for (int idx = 0; idx < psLength; ++idx) {
                        if (idx == psKeyIndex) {
                            map.put(ParseUtil.parseIntOrDefault(psSplit[idx], 0), mergedSplit);
                        }
                        mergedSplit[idx] = psSplit[idx];
                    }
                }
                return;
            });
            if (prstatInfo.size() > 1) {
                prstatInfo.stream().skip(1L).forEach(threadInfo -> {
                    final String[] splitPrstat = ParseUtil.whitespaces.split(threadInfo.trim());
                    if (splitPrstat.length == 15) {
                        String id = splitPrstat[0];
                        if (useTid) {
                            final int idxAfterForwardSlash = splitPrstat[14].lastIndexOf(47) + 1;
                            if (idxAfterForwardSlash > 0 && idxAfterForwardSlash < splitPrstat[14].length()) {
                                id = splitPrstat[14].substring(idxAfterForwardSlash);
                            }
                        }
                        final String[] existingSplit = map.get(Integer.parseInt(id));
                        if (existingSplit != null) {
                            existingSplit[psLength] = splitPrstat[10];
                            existingSplit[psLength + 1] = splitPrstat[11];
                        }
                    }
                    return;
                });
            }
        }
        return map;
    }
    
    static OSProcess.State getStateFromOutput(final char stateValue) {
        OSProcess.State state = null;
        switch (stateValue) {
            case 'O': {
                state = OSProcess.State.RUNNING;
                break;
            }
            case 'S': {
                state = OSProcess.State.SLEEPING;
                break;
            }
            case 'R':
            case 'W': {
                state = OSProcess.State.WAITING;
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

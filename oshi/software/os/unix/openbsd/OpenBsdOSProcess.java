// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.software.os.unix.openbsd;

import java.util.Iterator;
import java.util.ArrayList;
import oshi.software.os.OSThread;
import java.util.List;
import com.sun.jna.Pointer;
import oshi.jna.platform.unix.openbsd.OpenBsdLibc;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.Memory;
import oshi.util.ParseUtil;
import oshi.util.ExecutingCommand;
import oshi.util.platform.unix.openbsd.FstatUtil;
import oshi.util.Memoizer;
import oshi.software.os.OSProcess;
import java.util.function.Supplier;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.software.common.AbstractOSProcess;

@ThreadSafe
public class OpenBsdOSProcess extends AbstractOSProcess
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
    private long minorFaults;
    private long majorFaults;
    private long contextSwitches;
    
    public OpenBsdOSProcess(final int pid, final String[] split) {
        super(pid);
        this.bitness = Memoizer.memoize(this::queryBitness);
        this.path = "";
        this.state = OSProcess.State.INVALID;
        this.updateThreadCount();
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
        return FstatUtil.getCwd(this.getProcessID());
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
        return FstatUtil.getOpenFiles(this.getProcessID());
    }
    
    @Override
    public int getBitness() {
        return this.bitness.get();
    }
    
    @Override
    public long getAffinityMask() {
        long bitMask = 0L;
        final String cpuset = ExecutingCommand.getFirstAnswer("cpuset -gp " + this.getProcessID());
        final String[] split = cpuset.split(":");
        if (split.length > 1) {
            final String[] split2;
            final String[] bits = split2 = split[1].split(",");
            for (final String bit : split2) {
                final int bitToSet = ParseUtil.parseIntOrDefault(bit.trim(), -1);
                if (bitToSet >= 0) {
                    bitMask |= 1L << bitToSet;
                }
            }
        }
        return bitMask;
    }
    
    private int queryBitness() {
        final int[] mib = { 1, 14, 9, this.getProcessID() };
        final Pointer abi = (Pointer)new Memory(32L);
        final IntByReference size = new IntByReference(32);
        if (0 == OpenBsdLibc.INSTANCE.sysctl(mib, mib.length, abi, size, null, 0)) {
            final String elf = abi.getString(0L);
            if (elf.contains("ELF32")) {
                return 32;
            }
            if (elf.contains("ELF64")) {
                return 64;
            }
        }
        return 0;
    }
    
    @Override
    public List<OSThread> getThreadDetails() {
        final List<OSThread> threads = new ArrayList<OSThread>();
        String psCommand = "ps -aHwwxo tid,state,etime,time,nivcsw,nvcsw,majflt,minflt,pri,args";
        if (this.getProcessID() >= 0) {
            psCommand = psCommand + " -p " + this.getProcessID();
        }
        final List<String> threadList = ExecutingCommand.runNative(psCommand);
        if (threadList.isEmpty() || threadList.size() < 2) {
            return threads;
        }
        threadList.remove(0);
        for (final String thread : threadList) {
            final String[] split = ParseUtil.whitespaces.split(thread.trim(), 10);
            if (split.length == 10) {
                threads.add(new OpenBsdOSThread(this.getProcessID(), split));
            }
        }
        return threads;
    }
    
    @Override
    public long getMinorFaults() {
        return this.minorFaults;
    }
    
    @Override
    public long getMajorFaults() {
        return this.majorFaults;
    }
    
    @Override
    public long getContextSwitches() {
        return this.contextSwitches;
    }
    
    @Override
    public boolean updateAttributes() {
        final String psCommand = "ps -awwxo state,pid,ppid,user,uid,group,gid,pri,vsz,rss,etime,cputime,comm,majflt,minflt,args -p " + this.getProcessID();
        final List<String> procList = ExecutingCommand.runNative(psCommand);
        if (procList.size() > 1) {
            final String[] split = ParseUtil.whitespaces.split(procList.get(1).trim(), 16);
            if (split.length == 16) {
                this.updateThreadCount();
                return this.updateAttributes(split);
            }
        }
        this.state = OSProcess.State.INVALID;
        return false;
    }
    
    private boolean updateAttributes(final String[] split) {
        final long now = System.currentTimeMillis();
        switch (split[0].charAt(0)) {
            case 'R': {
                this.state = OSProcess.State.RUNNING;
                break;
            }
            case 'I':
            case 'S': {
                this.state = OSProcess.State.SLEEPING;
                break;
            }
            case 'D':
            case 'L':
            case 'U': {
                this.state = OSProcess.State.WAITING;
                break;
            }
            case 'Z': {
                this.state = OSProcess.State.ZOMBIE;
                break;
            }
            case 'T': {
                this.state = OSProcess.State.STOPPED;
                break;
            }
            default: {
                this.state = OSProcess.State.OTHER;
                break;
            }
        }
        this.parentProcessID = ParseUtil.parseIntOrDefault(split[2], 0);
        this.user = split[3];
        this.userID = split[4];
        this.group = split[5];
        this.groupID = split[6];
        this.priority = ParseUtil.parseIntOrDefault(split[7], 0);
        this.virtualSize = ParseUtil.parseLongOrDefault(split[8], 0L) * 1024L;
        this.residentSetSize = ParseUtil.parseLongOrDefault(split[9], 0L) * 1024L;
        final long elapsedTime = ParseUtil.parseDHMSOrDefault(split[10], 0L);
        this.upTime = ((elapsedTime < 1L) ? 1L : elapsedTime);
        this.startTime = now - this.upTime;
        this.userTime = ParseUtil.parseDHMSOrDefault(split[11], 0L);
        this.kernelTime = 0L;
        this.path = split[12];
        this.name = this.path.substring(this.path.lastIndexOf(47) + 1);
        this.minorFaults = ParseUtil.parseLongOrDefault(split[13], 0L);
        this.majorFaults = ParseUtil.parseLongOrDefault(split[14], 0L);
        final long nonVoluntaryContextSwitches = ParseUtil.parseLongOrDefault(split[15], 0L);
        final long voluntaryContextSwitches = ParseUtil.parseLongOrDefault(split[16], 0L);
        this.contextSwitches = voluntaryContextSwitches + nonVoluntaryContextSwitches;
        this.commandLine = split[17];
        return true;
    }
    
    private void updateThreadCount() {
        final List<String> threadList = ExecutingCommand.runNative("ps -axHo tid -p " + this.getProcessID());
        if (!threadList.isEmpty()) {
            this.threadCount = threadList.size() - 1;
        }
        this.threadCount = 1;
    }
}

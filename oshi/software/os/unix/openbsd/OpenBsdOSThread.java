// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.software.os.unix.openbsd;

import java.util.Iterator;
import java.util.List;
import oshi.util.ParseUtil;
import oshi.util.ExecutingCommand;
import oshi.software.os.OSProcess;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.software.common.AbstractOSThread;

@ThreadSafe
public class OpenBsdOSThread extends AbstractOSThread
{
    private int threadId;
    private String name;
    private OSProcess.State state;
    private long minorFaults;
    private long majorFaults;
    private long startMemoryAddress;
    private long contextSwitches;
    private long kernelTime;
    private long userTime;
    private long startTime;
    private long upTime;
    private int priority;
    
    public OpenBsdOSThread(final int processId, final String[] split) {
        super(processId);
        this.name = "";
        this.state = OSProcess.State.INVALID;
        this.updateAttributes(split);
    }
    
    @Override
    public int getThreadId() {
        return this.threadId;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public OSProcess.State getState() {
        return this.state;
    }
    
    @Override
    public long getStartMemoryAddress() {
        return this.startMemoryAddress;
    }
    
    @Override
    public long getContextSwitches() {
        return this.contextSwitches;
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
    public int getPriority() {
        return this.priority;
    }
    
    @Override
    public boolean updateAttributes() {
        final String psCommand = "ps -aHwwxo tid,state,etime,cputime,nivcsw,nvcsw,majflt,minflt,pri,args -p " + this.getOwningProcessId();
        final List<String> threadList = ExecutingCommand.runNative(psCommand);
        for (final String psOutput : threadList) {
            final String[] split = ParseUtil.whitespaces.split(psOutput.trim());
            if (split.length > 1 && this.getThreadId() == ParseUtil.parseIntOrDefault(split[1], 0)) {
                return this.updateAttributes(split);
            }
        }
        this.state = OSProcess.State.INVALID;
        return false;
    }
    
    private boolean updateAttributes(final String[] split) {
        if (split.length != 10) {
            this.state = OSProcess.State.INVALID;
            return false;
        }
        this.threadId = ParseUtil.parseIntOrDefault(split[0], 0);
        switch (split[1].charAt(0)) {
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
        final long elapsedTime = ParseUtil.parseDHMSOrDefault(split[2], 0L);
        this.upTime = ((elapsedTime < 1L) ? 1L : elapsedTime);
        final long now = System.currentTimeMillis();
        this.startTime = now - this.upTime;
        this.kernelTime = 0L;
        this.userTime = ParseUtil.parseDHMSOrDefault(split[3], 0L);
        this.startMemoryAddress = 0L;
        final long nonVoluntaryContextSwitches = ParseUtil.parseLongOrDefault(split[3], 0L);
        final long voluntaryContextSwitches = ParseUtil.parseLongOrDefault(split[5], 0L);
        this.contextSwitches = voluntaryContextSwitches + nonVoluntaryContextSwitches;
        this.majorFaults = ParseUtil.parseLongOrDefault(split[6], 0L);
        this.minorFaults = ParseUtil.parseLongOrDefault(split[7], 0L);
        this.priority = ParseUtil.parseIntOrDefault(split[8], 0);
        this.name = split[9];
        return true;
    }
}

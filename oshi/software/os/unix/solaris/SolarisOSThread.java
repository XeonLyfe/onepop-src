// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.software.os.unix.solaris;

import oshi.util.ParseUtil;
import java.util.Optional;
import java.util.List;
import java.util.function.Function;
import java.util.Map;
import oshi.util.ExecutingCommand;
import oshi.software.os.OSProcess;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.software.common.AbstractOSThread;

@ThreadSafe
public class SolarisOSThread extends AbstractOSThread
{
    private int threadId;
    private OSProcess.State state;
    private long startMemoryAddress;
    private long contextSwitches;
    private long kernelTime;
    private long userTime;
    private long startTime;
    private long upTime;
    private int priority;
    
    public SolarisOSThread(final int pid, final String[] split) {
        super(pid);
        this.state = OSProcess.State.INVALID;
        this.updateAttributes(split);
    }
    
    @Override
    public int getThreadId() {
        return this.threadId;
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
        final List<String> threadListInfo1 = ExecutingCommand.runNative("ps -o lwp,s,etime,stime,time,addr,pri -p " + this.getOwningProcessId());
        final List<String> threadListInfo2 = ExecutingCommand.runNative("prstat -L -v -p " + this.getOwningProcessId() + " 1 1");
        final Map<Integer, String[]> threadMap = SolarisOSProcess.parseAndMergePSandPrstatInfo(threadListInfo1, 0, 7, threadListInfo2, true);
        if (threadMap.keySet().size() > 1) {
            final Optional<String[]> split = threadMap.entrySet().stream().filter(entry -> entry.getKey() == this.getThreadId()).map((Function<? super Object, ? extends String[]>)Map.Entry::getValue).findFirst();
            if (split.isPresent()) {
                return this.updateAttributes(split.get());
            }
        }
        this.state = OSProcess.State.INVALID;
        return false;
    }
    
    private boolean updateAttributes(final String[] split) {
        this.threadId = ParseUtil.parseIntOrDefault(split[0], 0);
        this.state = SolarisOSProcess.getStateFromOutput(split[1].charAt(0));
        final long elapsedTime = ParseUtil.parseDHMSOrDefault(split[2], 0L);
        this.upTime = ((elapsedTime < 1L) ? 1L : elapsedTime);
        final long now = System.currentTimeMillis();
        this.startTime = now - this.upTime;
        this.kernelTime = ParseUtil.parseDHMSOrDefault(split[3], 0L);
        this.userTime = ParseUtil.parseDHMSOrDefault(split[4], 0L) - this.kernelTime;
        this.startMemoryAddress = ParseUtil.hexStringToLong(split[5], 0L);
        this.priority = ParseUtil.parseIntOrDefault(split[6], 0);
        final long nonVoluntaryContextSwitches = ParseUtil.parseLongOrDefault(split[7], 0L);
        final long voluntaryContextSwitches = ParseUtil.parseLongOrDefault(split[8], 0L);
        this.contextSwitches = voluntaryContextSwitches + nonVoluntaryContextSwitches;
        return true;
    }
}

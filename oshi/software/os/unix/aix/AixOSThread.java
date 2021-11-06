// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.software.os.unix.aix;

import java.util.Iterator;
import java.util.List;
import oshi.util.ParseUtil;
import oshi.util.ExecutingCommand;
import oshi.software.os.OSProcess;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.software.common.AbstractOSThread;

@ThreadSafe
public class AixOSThread extends AbstractOSThread
{
    private int threadId;
    private OSProcess.State state;
    private long contextSwitches;
    private long kernelTime;
    private long userTime;
    private long startTime;
    private long upTime;
    private int priority;
    
    public AixOSThread(final int pid, final String[] split) {
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
        final List<String> threadListInfoPs = ExecutingCommand.runNative("ps -m -o THREAD -p " + this.getOwningProcessId());
        if (threadListInfoPs.size() > 2) {
            threadListInfoPs.remove(0);
            threadListInfoPs.remove(0);
            for (final String threadInfo : threadListInfoPs) {
                final String[] threadInfoSplit = ParseUtil.whitespaces.split(threadInfo.trim());
                if (threadInfoSplit.length == 13 && threadInfoSplit[3].equals(String.valueOf(this.getThreadId()))) {
                    final String[] split = { threadInfoSplit[3], threadInfoSplit[4], threadInfoSplit[6] };
                    this.updateAttributes(split);
                }
            }
        }
        this.state = OSProcess.State.INVALID;
        return false;
    }
    
    private boolean updateAttributes(final String[] split) {
        this.threadId = ParseUtil.parseIntOrDefault(split[0], 0);
        this.state = AixOSProcess.getStateFromOutput(split[1].charAt(0));
        this.priority = ParseUtil.parseIntOrDefault(split[2], 0);
        return true;
    }
}

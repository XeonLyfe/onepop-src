// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.software.os;

import java.util.List;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public interface OSProcess
{
    String getName();
    
    String getPath();
    
    String getCommandLine();
    
    String getCurrentWorkingDirectory();
    
    String getUser();
    
    String getUserID();
    
    String getGroup();
    
    String getGroupID();
    
    State getState();
    
    int getProcessID();
    
    int getParentProcessID();
    
    int getThreadCount();
    
    int getPriority();
    
    long getVirtualSize();
    
    long getResidentSetSize();
    
    long getKernelTime();
    
    long getUserTime();
    
    long getUpTime();
    
    long getStartTime();
    
    long getBytesRead();
    
    long getBytesWritten();
    
    long getOpenFiles();
    
    double getProcessCpuLoadCumulative();
    
    double getProcessCpuLoadBetweenTicks(final OSProcess p0);
    
    int getBitness();
    
    long getAffinityMask();
    
    boolean updateAttributes();
    
    List<OSThread> getThreadDetails();
    
    default long getMinorFaults() {
        return 0L;
    }
    
    default long getMajorFaults() {
        return 0L;
    }
    
    default long getContextSwitches() {
        return 0L;
    }
    
    public enum State
    {
        NEW, 
        RUNNING, 
        SLEEPING, 
        WAITING, 
        ZOMBIE, 
        STOPPED, 
        OTHER, 
        INVALID, 
        SUSPENDED;
    }
}

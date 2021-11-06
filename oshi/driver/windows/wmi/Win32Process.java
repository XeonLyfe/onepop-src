// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.driver.windows.wmi;

import java.util.Collection;
import java.util.Iterator;
import oshi.util.platform.windows.WmiQueryHandler;
import com.sun.jna.platform.win32.COM.WbemcliUtil;
import java.util.Set;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class Win32Process
{
    private static final String WIN32_PROCESS = "Win32_Process";
    
    private Win32Process() {
    }
    
    public static WbemcliUtil.WmiResult<CommandLineProperty> queryCommandLines(final Set<Integer> pidsToQuery) {
        final StringBuilder sb = new StringBuilder("Win32_Process");
        if (pidsToQuery != null) {
            boolean first = true;
            for (final Integer pid : pidsToQuery) {
                if (first) {
                    sb.append(" WHERE ProcessID=");
                    first = false;
                }
                else {
                    sb.append(" OR ProcessID=");
                }
                sb.append(pid);
            }
        }
        final WbemcliUtil.WmiQuery<CommandLineProperty> commandLineQuery = (WbemcliUtil.WmiQuery<CommandLineProperty>)new WbemcliUtil.WmiQuery(sb.toString(), (Class)CommandLineProperty.class);
        return WmiQueryHandler.createInstance().queryWMI(commandLineQuery);
    }
    
    public static WbemcliUtil.WmiResult<ProcessXPProperty> queryProcesses(final Collection<Integer> pids) {
        final StringBuilder sb = new StringBuilder("Win32_Process");
        if (pids != null) {
            boolean first = true;
            for (final Integer pid : pids) {
                if (first) {
                    sb.append(" WHERE ProcessID=");
                    first = false;
                }
                else {
                    sb.append(" OR ProcessID=");
                }
                sb.append(pid);
            }
        }
        final WbemcliUtil.WmiQuery<ProcessXPProperty> processQueryXP = (WbemcliUtil.WmiQuery<ProcessXPProperty>)new WbemcliUtil.WmiQuery(sb.toString(), (Class)ProcessXPProperty.class);
        return WmiQueryHandler.createInstance().queryWMI(processQueryXP);
    }
    
    public enum CommandLineProperty
    {
        PROCESSID, 
        COMMANDLINE;
    }
    
    public enum ProcessXPProperty
    {
        PROCESSID, 
        NAME, 
        KERNELMODETIME, 
        USERMODETIME, 
        THREADCOUNT, 
        PAGEFILEUSAGE, 
        HANDLECOUNT, 
        EXECUTABLEPATH;
    }
}

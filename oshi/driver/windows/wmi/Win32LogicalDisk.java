// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.driver.windows.wmi;

import oshi.util.platform.windows.WmiQueryHandler;
import com.sun.jna.platform.win32.COM.WbemcliUtil;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class Win32LogicalDisk
{
    private static final String WIN32_LOGICAL_DISK = "Win32_LogicalDisk";
    
    private Win32LogicalDisk() {
    }
    
    public static WbemcliUtil.WmiResult<LogicalDiskProperty> queryLogicalDisk(final String nameToMatch, final boolean localOnly) {
        final StringBuilder wmiClassName = new StringBuilder("Win32_LogicalDisk");
        boolean where = false;
        if (localOnly) {
            wmiClassName.append(" WHERE DriveType != 4");
            where = true;
        }
        if (nameToMatch != null) {
            wmiClassName.append(where ? " AND" : " WHERE").append(" Name=\"").append(nameToMatch).append('\"');
        }
        final WbemcliUtil.WmiQuery<LogicalDiskProperty> logicalDiskQuery = (WbemcliUtil.WmiQuery<LogicalDiskProperty>)new WbemcliUtil.WmiQuery(wmiClassName.toString(), (Class)LogicalDiskProperty.class);
        return WmiQueryHandler.createInstance().queryWMI(logicalDiskQuery);
    }
    
    public enum LogicalDiskProperty
    {
        ACCESS, 
        DESCRIPTION, 
        DRIVETYPE, 
        FILESYSTEM, 
        FREESPACE, 
        NAME, 
        PROVIDERNAME, 
        SIZE, 
        VOLUMENAME;
    }
}

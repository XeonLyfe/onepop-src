// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.driver.windows.wmi;

import com.sun.jna.platform.win32.COM.WbemcliUtil;
import oshi.util.platform.windows.WmiQueryHandler;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class Win32DiskDrive
{
    private static final String WIN32_DISK_DRIVE = "Win32_DiskDrive";
    
    private Win32DiskDrive() {
    }
    
    public static WbemcliUtil.WmiResult<DiskDriveProperty> queryDiskDrive(final WmiQueryHandler h) {
        final WbemcliUtil.WmiQuery<DiskDriveProperty> diskDriveQuery = (WbemcliUtil.WmiQuery<DiskDriveProperty>)new WbemcliUtil.WmiQuery("Win32_DiskDrive", (Class)DiskDriveProperty.class);
        return h.queryWMI(diskDriveQuery, false);
    }
    
    public enum DiskDriveProperty
    {
        INDEX, 
        MANUFACTURER, 
        MODEL, 
        NAME, 
        SERIALNUMBER, 
        SIZE;
    }
}

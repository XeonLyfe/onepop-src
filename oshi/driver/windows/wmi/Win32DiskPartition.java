// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.driver.windows.wmi;

import com.sun.jna.platform.win32.COM.WbemcliUtil;
import oshi.util.platform.windows.WmiQueryHandler;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class Win32DiskPartition
{
    private static final String WIN32_DISK_PARTITION = "Win32_DiskPartition";
    
    private Win32DiskPartition() {
    }
    
    public static WbemcliUtil.WmiResult<DiskPartitionProperty> queryPartition(final WmiQueryHandler h) {
        final WbemcliUtil.WmiQuery<DiskPartitionProperty> partitionQuery = (WbemcliUtil.WmiQuery<DiskPartitionProperty>)new WbemcliUtil.WmiQuery("Win32_DiskPartition", (Class)DiskPartitionProperty.class);
        return h.queryWMI(partitionQuery, false);
    }
    
    public enum DiskPartitionProperty
    {
        INDEX, 
        DESCRIPTION, 
        DEVICEID, 
        DISKINDEX, 
        NAME, 
        SIZE, 
        TYPE;
    }
}

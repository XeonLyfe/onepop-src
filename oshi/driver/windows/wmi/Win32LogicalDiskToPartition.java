// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.driver.windows.wmi;

import com.sun.jna.platform.win32.COM.WbemcliUtil;
import oshi.util.platform.windows.WmiQueryHandler;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class Win32LogicalDiskToPartition
{
    private static final String WIN32_LOGICAL_DISK_TO_PARTITION = "Win32_LogicalDiskToPartition";
    
    private Win32LogicalDiskToPartition() {
    }
    
    public static WbemcliUtil.WmiResult<DiskToPartitionProperty> queryDiskToPartition(final WmiQueryHandler h) {
        final WbemcliUtil.WmiQuery<DiskToPartitionProperty> diskToPartitionQuery = (WbemcliUtil.WmiQuery<DiskToPartitionProperty>)new WbemcliUtil.WmiQuery("Win32_LogicalDiskToPartition", (Class)DiskToPartitionProperty.class);
        return h.queryWMI(diskToPartitionQuery, false);
    }
    
    public enum DiskToPartitionProperty
    {
        ANTECEDENT, 
        DEPENDENT, 
        ENDINGADDRESS, 
        STARTINGADDRESS;
    }
}

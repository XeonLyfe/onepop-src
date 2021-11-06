// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.driver.windows.wmi;

import oshi.util.platform.windows.WmiQueryHandler;
import com.sun.jna.platform.win32.COM.WbemcliUtil;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class Win32PhysicalMemory
{
    private static final String WIN32_PHYSICAL_MEMORY = "Win32_PhysicalMemory";
    
    private Win32PhysicalMemory() {
    }
    
    public static WbemcliUtil.WmiResult<PhysicalMemoryProperty> queryphysicalMemory() {
        final WbemcliUtil.WmiQuery<PhysicalMemoryProperty> physicalMemoryQuery = (WbemcliUtil.WmiQuery<PhysicalMemoryProperty>)new WbemcliUtil.WmiQuery("Win32_PhysicalMemory", (Class)PhysicalMemoryProperty.class);
        return WmiQueryHandler.createInstance().queryWMI(physicalMemoryQuery);
    }
    
    public static WbemcliUtil.WmiResult<PhysicalMemoryPropertyWin8> queryphysicalMemoryWin8() {
        final WbemcliUtil.WmiQuery<PhysicalMemoryPropertyWin8> physicalMemoryQuery = (WbemcliUtil.WmiQuery<PhysicalMemoryPropertyWin8>)new WbemcliUtil.WmiQuery("Win32_PhysicalMemory", (Class)PhysicalMemoryPropertyWin8.class);
        return WmiQueryHandler.createInstance().queryWMI(physicalMemoryQuery);
    }
    
    public enum PhysicalMemoryProperty
    {
        BANKLABEL, 
        CAPACITY, 
        SPEED, 
        MANUFACTURER, 
        SMBIOSMEMORYTYPE;
    }
    
    public enum PhysicalMemoryPropertyWin8
    {
        BANKLABEL, 
        CAPACITY, 
        SPEED, 
        MANUFACTURER, 
        MEMORYTYPE;
    }
}

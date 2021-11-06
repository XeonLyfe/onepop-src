// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.driver.windows.wmi;

import oshi.util.platform.windows.WmiQueryHandler;
import com.sun.jna.platform.win32.COM.WbemcliUtil;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class Win32OperatingSystem
{
    private static final String WIN32_OPERATING_SYSTEM = "Win32_OperatingSystem";
    
    private Win32OperatingSystem() {
    }
    
    public static WbemcliUtil.WmiResult<OSVersionProperty> queryOsVersion() {
        final WbemcliUtil.WmiQuery<OSVersionProperty> osVersionQuery = (WbemcliUtil.WmiQuery<OSVersionProperty>)new WbemcliUtil.WmiQuery("Win32_OperatingSystem", (Class)OSVersionProperty.class);
        return WmiQueryHandler.createInstance().queryWMI(osVersionQuery);
    }
    
    public enum OSVersionProperty
    {
        VERSION, 
        PRODUCTTYPE, 
        BUILDNUMBER, 
        CSDVERSION, 
        SUITEMASK;
    }
}

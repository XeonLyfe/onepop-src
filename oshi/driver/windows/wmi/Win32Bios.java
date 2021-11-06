// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.driver.windows.wmi;

import oshi.util.platform.windows.WmiQueryHandler;
import com.sun.jna.platform.win32.COM.WbemcliUtil;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class Win32Bios
{
    private static final String WIN32_BIOS_WHERE_PRIMARY_BIOS_TRUE = "Win32_BIOS where PrimaryBIOS=true";
    
    private Win32Bios() {
    }
    
    public static WbemcliUtil.WmiResult<BiosSerialProperty> querySerialNumber() {
        final WbemcliUtil.WmiQuery<BiosSerialProperty> serialNumQuery = (WbemcliUtil.WmiQuery<BiosSerialProperty>)new WbemcliUtil.WmiQuery("Win32_BIOS where PrimaryBIOS=true", (Class)BiosSerialProperty.class);
        return WmiQueryHandler.createInstance().queryWMI(serialNumQuery);
    }
    
    public static WbemcliUtil.WmiResult<BiosProperty> queryBiosInfo() {
        final WbemcliUtil.WmiQuery<BiosProperty> biosQuery = (WbemcliUtil.WmiQuery<BiosProperty>)new WbemcliUtil.WmiQuery("Win32_BIOS where PrimaryBIOS=true", (Class)BiosProperty.class);
        return WmiQueryHandler.createInstance().queryWMI(biosQuery);
    }
    
    public enum BiosSerialProperty
    {
        SERIALNUMBER;
    }
    
    public enum BiosProperty
    {
        MANUFACTURER, 
        NAME, 
        DESCRIPTION, 
        VERSION, 
        RELEASEDATE;
    }
}

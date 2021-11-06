// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.driver.windows.wmi;

import oshi.util.platform.windows.WmiQueryHandler;
import com.sun.jna.platform.win32.COM.WbemcliUtil;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class Win32Fan
{
    private static final String WIN32_FAN = "Win32_Fan";
    
    private Win32Fan() {
    }
    
    public static WbemcliUtil.WmiResult<SpeedProperty> querySpeed() {
        final WbemcliUtil.WmiQuery<SpeedProperty> fanQuery = (WbemcliUtil.WmiQuery<SpeedProperty>)new WbemcliUtil.WmiQuery("Win32_Fan", (Class)SpeedProperty.class);
        return WmiQueryHandler.createInstance().queryWMI(fanQuery);
    }
    
    public enum SpeedProperty
    {
        DESIREDSPEED;
    }
}

// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.driver.windows.wmi;

import oshi.util.platform.windows.WmiQueryHandler;
import com.sun.jna.platform.win32.COM.WbemcliUtil;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class Win32ComputerSystem
{
    private static final String WIN32_COMPUTER_SYSTEM = "Win32_ComputerSystem";
    
    private Win32ComputerSystem() {
    }
    
    public static WbemcliUtil.WmiResult<ComputerSystemProperty> queryComputerSystem() {
        final WbemcliUtil.WmiQuery<ComputerSystemProperty> computerSystemQuery = (WbemcliUtil.WmiQuery<ComputerSystemProperty>)new WbemcliUtil.WmiQuery("Win32_ComputerSystem", (Class)ComputerSystemProperty.class);
        return WmiQueryHandler.createInstance().queryWMI(computerSystemQuery);
    }
    
    public enum ComputerSystemProperty
    {
        MANUFACTURER, 
        MODEL;
    }
}

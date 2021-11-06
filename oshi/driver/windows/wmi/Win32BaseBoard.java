// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.driver.windows.wmi;

import oshi.util.platform.windows.WmiQueryHandler;
import com.sun.jna.platform.win32.COM.WbemcliUtil;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class Win32BaseBoard
{
    private static final String WIN32_BASEBOARD = "Win32_BaseBoard";
    
    private Win32BaseBoard() {
    }
    
    public static WbemcliUtil.WmiResult<BaseBoardProperty> queryBaseboardInfo() {
        final WbemcliUtil.WmiQuery<BaseBoardProperty> baseboardQuery = (WbemcliUtil.WmiQuery<BaseBoardProperty>)new WbemcliUtil.WmiQuery("Win32_BaseBoard", (Class)BaseBoardProperty.class);
        return WmiQueryHandler.createInstance().queryWMI(baseboardQuery);
    }
    
    public enum BaseBoardProperty
    {
        MANUFACTURER, 
        MODEL, 
        VERSION, 
        SERIALNUMBER;
    }
}

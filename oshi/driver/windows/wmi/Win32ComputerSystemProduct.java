// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.driver.windows.wmi;

import oshi.util.platform.windows.WmiQueryHandler;
import com.sun.jna.platform.win32.COM.WbemcliUtil;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class Win32ComputerSystemProduct
{
    private static final String WIN32_COMPUTER_SYSTEM_PRODUCT = "Win32_ComputerSystemProduct";
    
    private Win32ComputerSystemProduct() {
    }
    
    public static WbemcliUtil.WmiResult<ComputerSystemProductProperty> queryIdentifyingNumberUUID() {
        final WbemcliUtil.WmiQuery<ComputerSystemProductProperty> identifyingNumberQuery = (WbemcliUtil.WmiQuery<ComputerSystemProductProperty>)new WbemcliUtil.WmiQuery("Win32_ComputerSystemProduct", (Class)ComputerSystemProductProperty.class);
        return WmiQueryHandler.createInstance().queryWMI(identifyingNumberQuery);
    }
    
    public enum ComputerSystemProductProperty
    {
        IDENTIFYINGNUMBER, 
        UUID;
    }
}

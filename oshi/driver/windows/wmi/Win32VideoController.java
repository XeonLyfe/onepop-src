// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.driver.windows.wmi;

import oshi.util.platform.windows.WmiQueryHandler;
import com.sun.jna.platform.win32.COM.WbemcliUtil;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class Win32VideoController
{
    private static final String WIN32_VIDEO_CONTROLLER = "Win32_VideoController";
    
    private Win32VideoController() {
    }
    
    public static WbemcliUtil.WmiResult<VideoControllerProperty> queryVideoController() {
        final WbemcliUtil.WmiQuery<VideoControllerProperty> videoControllerQuery = (WbemcliUtil.WmiQuery<VideoControllerProperty>)new WbemcliUtil.WmiQuery("Win32_VideoController", (Class)VideoControllerProperty.class);
        return WmiQueryHandler.createInstance().queryWMI(videoControllerQuery);
    }
    
    public enum VideoControllerProperty
    {
        ADAPTERCOMPATIBILITY, 
        ADAPTERRAM, 
        DRIVERVERSION, 
        NAME, 
        PNPDEVICEID;
    }
}

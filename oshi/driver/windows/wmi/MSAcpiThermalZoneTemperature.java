// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.driver.windows.wmi;

import oshi.util.platform.windows.WmiQueryHandler;
import com.sun.jna.platform.win32.COM.WbemcliUtil;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class MSAcpiThermalZoneTemperature
{
    public static final String WMI_NAMESPACE = "ROOT\\WMI";
    private static final String MS_ACPI_THERMAL_ZONE_TEMPERATURE = "MSAcpi_ThermalZoneTemperature";
    
    private MSAcpiThermalZoneTemperature() {
    }
    
    public static WbemcliUtil.WmiResult<TemperatureProperty> queryCurrentTemperature() {
        final WbemcliUtil.WmiQuery<TemperatureProperty> curTempQuery = (WbemcliUtil.WmiQuery<TemperatureProperty>)new WbemcliUtil.WmiQuery("ROOT\\WMI", "MSAcpi_ThermalZoneTemperature", (Class)TemperatureProperty.class);
        return WmiQueryHandler.createInstance().queryWMI(curTempQuery);
    }
    
    public enum TemperatureProperty
    {
        CURRENTTEMPERATURE;
    }
}

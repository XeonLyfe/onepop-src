// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.driver.windows.wmi;

import com.sun.jna.platform.win32.COM.WbemcliUtil;
import oshi.util.platform.windows.WmiQueryHandler;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class OhmSensor
{
    private static final String SENSOR = "Sensor";
    
    private OhmSensor() {
    }
    
    public static WbemcliUtil.WmiResult<ValueProperty> querySensorValue(final WmiQueryHandler h, final String identifier, final String sensorType) {
        final StringBuilder sb = new StringBuilder("Sensor");
        sb.append(" WHERE Parent = \"").append(identifier);
        sb.append("\" AND SensorType=\"").append(sensorType).append('\"');
        final WbemcliUtil.WmiQuery<ValueProperty> ohmSensorQuery = (WbemcliUtil.WmiQuery<ValueProperty>)new WbemcliUtil.WmiQuery("ROOT\\OpenHardwareMonitor", sb.toString(), (Class)ValueProperty.class);
        return h.queryWMI(ohmSensorQuery, false);
    }
    
    public enum ValueProperty
    {
        VALUE;
    }
}

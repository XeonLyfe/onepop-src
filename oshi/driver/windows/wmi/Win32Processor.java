// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.driver.windows.wmi;

import oshi.util.platform.windows.WmiQueryHandler;
import com.sun.jna.platform.win32.COM.WbemcliUtil;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class Win32Processor
{
    private static final String WIN32_PROCESSOR = "Win32_Processor";
    
    private Win32Processor() {
    }
    
    public static WbemcliUtil.WmiResult<VoltProperty> queryVoltage() {
        final WbemcliUtil.WmiQuery<VoltProperty> voltQuery = (WbemcliUtil.WmiQuery<VoltProperty>)new WbemcliUtil.WmiQuery("Win32_Processor", (Class)VoltProperty.class);
        return WmiQueryHandler.createInstance().queryWMI(voltQuery);
    }
    
    public static WbemcliUtil.WmiResult<ProcessorIdProperty> queryProcessorId() {
        final WbemcliUtil.WmiQuery<ProcessorIdProperty> idQuery = (WbemcliUtil.WmiQuery<ProcessorIdProperty>)new WbemcliUtil.WmiQuery("Win32_Processor", (Class)ProcessorIdProperty.class);
        return WmiQueryHandler.createInstance().queryWMI(idQuery);
    }
    
    public static WbemcliUtil.WmiResult<BitnessProperty> queryBitness() {
        final WbemcliUtil.WmiQuery<BitnessProperty> bitnessQuery = (WbemcliUtil.WmiQuery<BitnessProperty>)new WbemcliUtil.WmiQuery("Win32_Processor", (Class)BitnessProperty.class);
        return WmiQueryHandler.createInstance().queryWMI(bitnessQuery);
    }
    
    public enum VoltProperty
    {
        CURRENTVOLTAGE, 
        VOLTAGECAPS;
    }
    
    public enum ProcessorIdProperty
    {
        PROCESSORID;
    }
    
    public enum BitnessProperty
    {
        ADDRESSWIDTH;
    }
}

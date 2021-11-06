// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.hardware;

import oshi.annotation.concurrent.Immutable;

@Immutable
public interface ComputerSystem
{
    String getManufacturer();
    
    String getModel();
    
    String getSerialNumber();
    
    String getHardwareUUID();
    
    Firmware getFirmware();
    
    Baseboard getBaseboard();
}

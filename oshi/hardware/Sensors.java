// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.hardware;

import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public interface Sensors
{
    double getCpuTemperature();
    
    int[] getFanSpeeds();
    
    double getCpuVoltage();
}

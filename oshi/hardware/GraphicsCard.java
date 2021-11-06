// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.hardware;

import oshi.annotation.concurrent.Immutable;

@Immutable
public interface GraphicsCard
{
    String getName();
    
    String getDeviceId();
    
    String getVendor();
    
    String getVersionInfo();
    
    long getVRam();
}

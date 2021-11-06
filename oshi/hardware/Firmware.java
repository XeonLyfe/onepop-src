// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.hardware;

import oshi.annotation.concurrent.Immutable;

@Immutable
public interface Firmware
{
    String getManufacturer();
    
    String getName();
    
    String getDescription();
    
    String getVersion();
    
    String getReleaseDate();
}

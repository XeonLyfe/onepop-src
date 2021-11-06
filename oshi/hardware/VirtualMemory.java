// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.hardware;

import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public interface VirtualMemory
{
    long getSwapTotal();
    
    long getSwapUsed();
    
    long getVirtualMax();
    
    long getVirtualInUse();
    
    long getSwapPagesIn();
    
    long getSwapPagesOut();
}

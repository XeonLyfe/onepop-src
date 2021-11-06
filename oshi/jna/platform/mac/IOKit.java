// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.jna.platform.mac;

import com.sun.jna.Native;
import com.sun.jna.ptr.NativeLongByReference;
import com.sun.jna.NativeLong;
import com.sun.jna.Structure;

public interface IOKit extends com.sun.jna.platform.mac.IOKit
{
    public static final IOKit INSTANCE = (IOKit)Native.load("IOKit", (Class)IOKit.class);
    
    int IOConnectCallStructMethod(final com.sun.jna.platform.mac.IOKit.IOConnect p0, final int p1, final Structure p2, final NativeLong p3, final Structure p4, final NativeLongByReference p5);
}

// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.jna.platform.windows;

import com.sun.jna.Native;
import com.sun.jna.win32.W32APIOptions;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public interface Cfgmgr32 extends com.sun.jna.platform.win32.Cfgmgr32
{
    public static final Cfgmgr32 INSTANCE = (Cfgmgr32)Native.load("cfgmgr32", (Class)Cfgmgr32.class, W32APIOptions.DEFAULT_OPTIONS);
    public static final int CM_DRP_DEVICEDESC = 1;
    public static final int CM_DRP_SERVICE = 5;
    public static final int CM_DRP_CLASS = 8;
    public static final int CM_DRP_MFG = 12;
    public static final int CM_DRP_FRIENDLYNAME = 13;
    
    boolean CM_Get_DevNode_Registry_Property(final int p0, final int p1, final IntByReference p2, final Pointer p3, final IntByReference p4, final int p5);
}

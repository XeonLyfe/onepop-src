// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.jna.platform.unix.aix;

import com.sun.jna.Native;
import oshi.jna.platform.unix.CLibrary;

public interface AixLibc extends CLibrary
{
    public static final AixLibc INSTANCE = (AixLibc)Native.load("c", (Class)AixLibc.class);
}

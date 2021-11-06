// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.jna.platform.unix.solaris;

import com.sun.jna.NativeLong;
import com.sun.jna.Structure;
import com.sun.jna.Native;
import oshi.jna.platform.unix.CLibrary;

public interface SolarisLibc extends CLibrary
{
    public static final SolarisLibc INSTANCE = (SolarisLibc)Native.load("c", (Class)SolarisLibc.class);
    public static final int UTX_USERSIZE = 32;
    public static final int UTX_LINESIZE = 32;
    public static final int UTX_IDSIZE = 4;
    public static final int UTX_HOSTSIZE = 257;
    
    SolarisUtmpx getutxent();
    
    @Structure.FieldOrder({ "ut_user", "ut_id", "ut_line", "ut_pid", "ut_type", "ut_tv", "ut_session", "ut_syslen", "ut_host" })
    public static class SolarisUtmpx extends Structure
    {
        public byte[] ut_user;
        public byte[] ut_id;
        public byte[] ut_line;
        public int ut_pid;
        public short ut_type;
        public Timeval ut_tv;
        public int ut_session;
        public short ut_syslen;
        public byte[] ut_host;
        
        public SolarisUtmpx() {
            this.ut_user = new byte[32];
            this.ut_id = new byte[4];
            this.ut_line = new byte[32];
            this.ut_host = new byte[257];
        }
    }
    
    @Structure.FieldOrder({ "e_termination", "e_exit" })
    public static class Exit_status extends Structure
    {
        public short e_termination;
        public short e_exit;
    }
    
    @Structure.FieldOrder({ "tv_sec", "tv_usec" })
    public static class Timeval extends Structure
    {
        public NativeLong tv_sec;
        public NativeLong tv_usec;
    }
}

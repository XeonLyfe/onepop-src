// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi;

import oshi.hardware.platform.unix.openbsd.OpenBsdHardwareAbstractionLayer;
import oshi.hardware.platform.unix.aix.AixHardwareAbstractionLayer;
import oshi.hardware.platform.unix.freebsd.FreeBsdHardwareAbstractionLayer;
import oshi.hardware.platform.unix.solaris.SolarisHardwareAbstractionLayer;
import oshi.hardware.platform.mac.MacHardwareAbstractionLayer;
import oshi.hardware.platform.linux.LinuxHardwareAbstractionLayer;
import oshi.hardware.platform.windows.WindowsHardwareAbstractionLayer;
import oshi.software.os.unix.openbsd.OpenBsdOperatingSystem;
import oshi.software.os.unix.aix.AixOperatingSystem;
import oshi.software.os.unix.freebsd.FreeBsdOperatingSystem;
import oshi.software.os.unix.solaris.SolarisOperatingSystem;
import oshi.software.os.mac.MacOperatingSystem;
import oshi.software.os.linux.LinuxOperatingSystem;
import oshi.software.os.windows.WindowsOperatingSystem;
import com.sun.jna.Platform;
import oshi.util.Memoizer;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;
import java.util.function.Supplier;

public class SystemInfo
{
    private static final PlatformEnum currentPlatform;
    private static final String NOT_SUPPORTED = "Operating system not supported: JNA Platform type ";
    private final Supplier<OperatingSystem> os;
    private final Supplier<HardwareAbstractionLayer> hardware;
    
    public SystemInfo() {
        this.os = Memoizer.memoize(SystemInfo::createOperatingSystem);
        this.hardware = Memoizer.memoize(SystemInfo::createHardware);
        if (getCurrentPlatform().equals(PlatformEnum.UNKNOWN)) {
            throw new UnsupportedOperationException("Operating system not supported: JNA Platform type " + Platform.getOSType());
        }
    }
    
    public static PlatformEnum getCurrentPlatform() {
        return SystemInfo.currentPlatform;
    }
    
    @Deprecated
    public static PlatformEnum getCurrentPlatformEnum() {
        final PlatformEnum platform = getCurrentPlatform();
        return platform.equals(PlatformEnum.MACOS) ? PlatformEnum.MACOSX : platform;
    }
    
    public OperatingSystem getOperatingSystem() {
        return this.os.get();
    }
    
    private static OperatingSystem createOperatingSystem() {
        switch (SystemInfo.currentPlatform) {
            case WINDOWS: {
                return new WindowsOperatingSystem();
            }
            case LINUX: {
                return new LinuxOperatingSystem();
            }
            case MACOS: {
                return new MacOperatingSystem();
            }
            case SOLARIS: {
                return new SolarisOperatingSystem();
            }
            case FREEBSD: {
                return new FreeBsdOperatingSystem();
            }
            case AIX: {
                return new AixOperatingSystem();
            }
            case OPENBSD: {
                return new OpenBsdOperatingSystem();
            }
            default: {
                return null;
            }
        }
    }
    
    public HardwareAbstractionLayer getHardware() {
        return this.hardware.get();
    }
    
    private static HardwareAbstractionLayer createHardware() {
        switch (SystemInfo.currentPlatform) {
            case WINDOWS: {
                return new WindowsHardwareAbstractionLayer();
            }
            case LINUX: {
                return new LinuxHardwareAbstractionLayer();
            }
            case MACOS: {
                return new MacHardwareAbstractionLayer();
            }
            case SOLARIS: {
                return new SolarisHardwareAbstractionLayer();
            }
            case FREEBSD: {
                return new FreeBsdHardwareAbstractionLayer();
            }
            case AIX: {
                return new AixHardwareAbstractionLayer();
            }
            case OPENBSD: {
                return new OpenBsdHardwareAbstractionLayer();
            }
            default: {
                return null;
            }
        }
    }
    
    static {
        if (Platform.isWindows()) {
            currentPlatform = PlatformEnum.WINDOWS;
        }
        else if (Platform.isLinux()) {
            currentPlatform = PlatformEnum.LINUX;
        }
        else if (Platform.isMac()) {
            currentPlatform = PlatformEnum.MACOS;
        }
        else if (Platform.isSolaris()) {
            currentPlatform = PlatformEnum.SOLARIS;
        }
        else if (Platform.isFreeBSD()) {
            currentPlatform = PlatformEnum.FREEBSD;
        }
        else if (Platform.isAIX()) {
            currentPlatform = PlatformEnum.AIX;
        }
        else if (Platform.isOpenBSD()) {
            currentPlatform = PlatformEnum.OPENBSD;
        }
        else {
            currentPlatform = PlatformEnum.UNKNOWN;
        }
    }
}

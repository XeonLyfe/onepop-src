// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.hardware.platform.mac;

import org.slf4j.LoggerFactory;
import java.util.Set;
import java.util.Collection;
import oshi.util.ParseUtil;
import java.util.HashSet;
import com.sun.jna.platform.mac.IOKit;
import oshi.util.Util;
import java.nio.charset.StandardCharsets;
import com.sun.jna.platform.mac.IOKitUtil;
import oshi.util.FormatUtil;
import com.sun.jna.ptr.PointerByReference;
import java.util.Arrays;
import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.platform.mac.SystemB;
import java.util.ArrayList;
import java.util.List;
import oshi.util.platform.mac.SysctlUtil;
import oshi.hardware.CentralProcessor;
import oshi.util.Memoizer;
import oshi.util.tuples.Triplet;
import java.util.function.Supplier;
import org.slf4j.Logger;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.hardware.common.AbstractCentralProcessor;

@ThreadSafe
final class MacCentralProcessor extends AbstractCentralProcessor
{
    private static final Logger LOG;
    private final Supplier<String> vendor;
    private final Supplier<Triplet<Integer, Integer, Long>> typeFamilyFreq;
    private static final int ROSETTA_CPUTYPE = 7;
    private static final int ROSETTA_CPUFAMILY = 1463508716;
    private static final int M1_CPUTYPE = 16777228;
    private static final int M1_CPUFAMILY = 458787763;
    
    MacCentralProcessor() {
        this.vendor = Memoizer.memoize(MacCentralProcessor::platformExpert);
        this.typeFamilyFreq = (Supplier<Triplet<Integer, Integer, Long>>)Memoizer.memoize(MacCentralProcessor::queryArmCpu);
    }
    
    @Override
    protected CentralProcessor.ProcessorIdentifier queryProcessorId() {
        final String cpuName = SysctlUtil.sysctl("machdep.cpu.brand_string", "");
        long cpuFreq = 0L;
        String cpuVendor;
        String cpuStepping;
        String cpuModel;
        String cpuFamily;
        String processorID;
        if (cpuName.startsWith("Apple")) {
            cpuVendor = this.vendor.get();
            cpuStepping = "0";
            cpuModel = "0";
            int type = SysctlUtil.sysctl("hw.cputype", 0);
            int family = SysctlUtil.sysctl("hw.cpufamily", 0);
            if (family == 1463508716) {
                type = (int)this.typeFamilyFreq.get().getA();
                family = (int)this.typeFamilyFreq.get().getB();
            }
            cpuFreq = (long)this.typeFamilyFreq.get().getC();
            cpuFamily = String.format("0x%08x", family);
            processorID = String.format("%08x%08x", type, family);
        }
        else {
            cpuVendor = SysctlUtil.sysctl("machdep.cpu.vendor", "");
            int i = SysctlUtil.sysctl("machdep.cpu.stepping", -1);
            cpuStepping = ((i < 0) ? "" : Integer.toString(i));
            i = SysctlUtil.sysctl("machdep.cpu.model", -1);
            cpuModel = ((i < 0) ? "" : Integer.toString(i));
            i = SysctlUtil.sysctl("machdep.cpu.family", -1);
            cpuFamily = ((i < 0) ? "" : Integer.toString(i));
            long processorIdBits = 0L;
            processorIdBits |= SysctlUtil.sysctl("machdep.cpu.signature", 0);
            processorIdBits |= (SysctlUtil.sysctl("machdep.cpu.feature_bits", 0L) & -1L) << 32;
            processorID = String.format("%016x", processorIdBits);
        }
        if (cpuFreq == 0L) {
            cpuFreq = SysctlUtil.sysctl("hw.cpufrequency", 0L);
        }
        final boolean cpu64bit = SysctlUtil.sysctl("hw.cpu64bit_capable", 0) != 0;
        return new CentralProcessor.ProcessorIdentifier(cpuVendor, cpuName, cpuFamily, cpuModel, cpuStepping, processorID, cpu64bit, cpuFreq);
    }
    
    @Override
    protected List<CentralProcessor.LogicalProcessor> initProcessorCounts() {
        final int logicalProcessorCount = SysctlUtil.sysctl("hw.logicalcpu", 1);
        final int physicalProcessorCount = SysctlUtil.sysctl("hw.physicalcpu", 1);
        final int physicalPackageCount = SysctlUtil.sysctl("hw.packages", 1);
        final List<CentralProcessor.LogicalProcessor> logProcs = new ArrayList<CentralProcessor.LogicalProcessor>(logicalProcessorCount);
        for (int i = 0; i < logicalProcessorCount; ++i) {
            logProcs.add(new CentralProcessor.LogicalProcessor(i, i * physicalProcessorCount / logicalProcessorCount, i * physicalPackageCount / logicalProcessorCount));
        }
        return logProcs;
    }
    
    public long[] querySystemCpuLoadTicks() {
        final long[] ticks = new long[CentralProcessor.TickType.values().length];
        final int machPort = SystemB.INSTANCE.mach_host_self();
        final SystemB.HostCpuLoadInfo cpuLoadInfo = new SystemB.HostCpuLoadInfo();
        if (0 != SystemB.INSTANCE.host_statistics(machPort, 3, (Structure)cpuLoadInfo, new IntByReference(cpuLoadInfo.size()))) {
            MacCentralProcessor.LOG.error("Failed to get System CPU ticks. Error code: {} ", (Object)Native.getLastError());
            return ticks;
        }
        ticks[CentralProcessor.TickType.USER.getIndex()] = cpuLoadInfo.cpu_ticks[0];
        ticks[CentralProcessor.TickType.NICE.getIndex()] = cpuLoadInfo.cpu_ticks[3];
        ticks[CentralProcessor.TickType.SYSTEM.getIndex()] = cpuLoadInfo.cpu_ticks[1];
        ticks[CentralProcessor.TickType.IDLE.getIndex()] = cpuLoadInfo.cpu_ticks[2];
        return ticks;
    }
    
    public long[] queryCurrentFreq() {
        final long[] freq = { SysctlUtil.sysctl("hw.cpufrequency", this.getProcessorIdentifier().getVendorFreq()) };
        return freq;
    }
    
    public long queryMaxFreq() {
        return SysctlUtil.sysctl("hw.cpufrequency_max", this.getProcessorIdentifier().getVendorFreq());
    }
    
    @Override
    public double[] getSystemLoadAverage(final int nelem) {
        if (nelem < 1 || nelem > 3) {
            throw new IllegalArgumentException("Must include from one to three elements.");
        }
        final double[] average = new double[nelem];
        final int retval = SystemB.INSTANCE.getloadavg(average, nelem);
        if (retval < nelem) {
            Arrays.fill(average, -1.0);
        }
        return average;
    }
    
    public long[][] queryProcessorCpuLoadTicks() {
        final long[][] ticks = new long[this.getLogicalProcessorCount()][CentralProcessor.TickType.values().length];
        final int machPort = SystemB.INSTANCE.mach_host_self();
        final IntByReference procCount = new IntByReference();
        final PointerByReference procCpuLoadInfo = new PointerByReference();
        final IntByReference procInfoCount = new IntByReference();
        if (0 != SystemB.INSTANCE.host_processor_info(machPort, 2, procCount, procCpuLoadInfo, procInfoCount)) {
            MacCentralProcessor.LOG.error("Failed to update CPU Load. Error code: {}", (Object)Native.getLastError());
            return ticks;
        }
        final int[] cpuTicks = procCpuLoadInfo.getValue().getIntArray(0L, procInfoCount.getValue());
        for (int cpu = 0; cpu < procCount.getValue(); ++cpu) {
            final int offset = cpu * 4;
            ticks[cpu][CentralProcessor.TickType.USER.getIndex()] = FormatUtil.getUnsignedInt(cpuTicks[offset + 0]);
            ticks[cpu][CentralProcessor.TickType.NICE.getIndex()] = FormatUtil.getUnsignedInt(cpuTicks[offset + 3]);
            ticks[cpu][CentralProcessor.TickType.SYSTEM.getIndex()] = FormatUtil.getUnsignedInt(cpuTicks[offset + 1]);
            ticks[cpu][CentralProcessor.TickType.IDLE.getIndex()] = FormatUtil.getUnsignedInt(cpuTicks[offset + 2]);
        }
        return ticks;
    }
    
    public long queryContextSwitches() {
        return 0L;
    }
    
    public long queryInterrupts() {
        return 0L;
    }
    
    private static String platformExpert() {
        String manufacturer = null;
        final IOKit.IORegistryEntry platformExpert = (IOKit.IORegistryEntry)IOKitUtil.getMatchingService("IOPlatformExpertDevice");
        if (platformExpert != null) {
            final byte[] data = platformExpert.getByteArrayProperty("manufacturer");
            if (data != null) {
                manufacturer = Native.toString(data, StandardCharsets.UTF_8);
            }
            platformExpert.release();
        }
        return Util.isBlank(manufacturer) ? "Apple Inc." : manufacturer;
    }
    
    private static Triplet<Integer, Integer, Long> queryArmCpu() {
        int type = 7;
        int family = 1463508716;
        long freq = 0L;
        final IOKit.IOIterator iter = IOKitUtil.getMatchingServices("IOPlatformDevice");
        if (iter != null) {
            final Set<String> compatibleStrSet = new HashSet<String>();
            for (IOKit.IORegistryEntry cpu = iter.next(); cpu != null; cpu = iter.next()) {
                if (cpu.getName().startsWith("cpu")) {
                    byte[] data = cpu.getByteArrayProperty("clock-frequency");
                    if (data != null) {
                        final long cpuFreq = ParseUtil.byteArrayToLong(data, data.length, false) * 1000L;
                        if (cpuFreq > freq) {
                            freq = cpuFreq;
                        }
                    }
                    data = cpu.getByteArrayProperty("compatible");
                    if (data != null) {
                        for (final String s : new String(data, StandardCharsets.UTF_8).split("\u0000")) {
                            if (!s.isEmpty()) {
                                compatibleStrSet.add(s);
                            }
                        }
                    }
                }
                cpu.release();
            }
            iter.release();
            final List<String> m1compatible = Arrays.asList("ARM,v8", "apple,firestorm", "apple,icestorm");
            compatibleStrSet.retainAll(m1compatible);
            if (compatibleStrSet.size() == m1compatible.size()) {
                type = 16777228;
                family = 458787763;
            }
        }
        return new Triplet<Integer, Integer, Long>(type, family, freq);
    }
    
    static {
        LOG = LoggerFactory.getLogger((Class)MacCentralProcessor.class);
    }
}

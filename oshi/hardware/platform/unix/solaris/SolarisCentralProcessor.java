// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.hardware.platform.unix.solaris;

import oshi.jna.platform.unix.solaris.SolarisLibc;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import oshi.util.ParseUtil;
import java.util.ArrayList;
import java.util.List;
import com.sun.jna.platform.unix.solaris.LibKstat;
import oshi.util.ExecutingCommand;
import oshi.util.platform.unix.solaris.KstatUtil;
import oshi.hardware.CentralProcessor;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.hardware.common.AbstractCentralProcessor;

@ThreadSafe
final class SolarisCentralProcessor extends AbstractCentralProcessor
{
    private static final String CPU_INFO = "cpu_info";
    
    @Override
    protected CentralProcessor.ProcessorIdentifier queryProcessorId() {
        String cpuVendor = "";
        String cpuName = "";
        String cpuFamily = "";
        String cpuModel = "";
        String cpuStepping = "";
        long cpuFreq = 0L;
        try (final KstatUtil.KstatChain kc = KstatUtil.openChain()) {
            final LibKstat.Kstat ksp = KstatUtil.KstatChain.lookup("cpu_info", -1, null);
            if (ksp != null && KstatUtil.KstatChain.read(ksp)) {
                cpuVendor = KstatUtil.dataLookupString(ksp, "vendor_id");
                cpuName = KstatUtil.dataLookupString(ksp, "brand");
                cpuFamily = KstatUtil.dataLookupString(ksp, "family");
                cpuModel = KstatUtil.dataLookupString(ksp, "model");
                cpuStepping = KstatUtil.dataLookupString(ksp, "stepping");
                cpuFreq = KstatUtil.dataLookupLong(ksp, "clock_MHz") * 1000000L;
            }
        }
        final boolean cpu64bit = "64".equals(ExecutingCommand.getFirstAnswer("isainfo -b").trim());
        final String processorID = getProcessorID(cpuStepping, cpuModel, cpuFamily);
        return new CentralProcessor.ProcessorIdentifier(cpuVendor, cpuName, cpuFamily, cpuModel, cpuStepping, processorID, cpu64bit, cpuFreq);
    }
    
    @Override
    protected List<CentralProcessor.LogicalProcessor> initProcessorCounts() {
        final Map<Integer, Integer> numaNodeMap = mapNumaNodes();
        final List<CentralProcessor.LogicalProcessor> logProcs = new ArrayList<CentralProcessor.LogicalProcessor>();
        try (final KstatUtil.KstatChain kc = KstatUtil.openChain()) {
            final List<LibKstat.Kstat> kstats = KstatUtil.KstatChain.lookupAll("cpu_info", -1, null);
            for (final LibKstat.Kstat ksp : kstats) {
                if (ksp != null && KstatUtil.KstatChain.read(ksp)) {
                    final int procId = logProcs.size();
                    final String chipId = KstatUtil.dataLookupString(ksp, "chip_id");
                    final String coreId = KstatUtil.dataLookupString(ksp, "core_id");
                    final CentralProcessor.LogicalProcessor logProc = new CentralProcessor.LogicalProcessor(procId, ParseUtil.parseIntOrDefault(coreId, 0), ParseUtil.parseIntOrDefault(chipId, 0), numaNodeMap.getOrDefault(procId, 0));
                    logProcs.add(logProc);
                }
            }
        }
        if (logProcs.isEmpty()) {
            logProcs.add(new CentralProcessor.LogicalProcessor(0, 0, 0));
        }
        return logProcs;
    }
    
    private static Map<Integer, Integer> mapNumaNodes() {
        final Map<Integer, Integer> numaNodeMap = new HashMap<Integer, Integer>();
        int lgroup = 0;
        for (final String line : ExecutingCommand.runNative("lgrpinfo -c leaves")) {
            if (line.startsWith("lgroup")) {
                lgroup = ParseUtil.getFirstIntValue(line);
            }
            else {
                if (!line.contains("CPUs:") && !line.contains("CPU:")) {
                    continue;
                }
                for (final Integer cpu : ParseUtil.parseHyphenatedIntList(line.split(":")[1])) {
                    numaNodeMap.put(cpu, lgroup);
                }
            }
        }
        return numaNodeMap;
    }
    
    public long[] querySystemCpuLoadTicks() {
        final long[] ticks = new long[CentralProcessor.TickType.values().length];
        final long[][] procTicks = this.getProcessorCpuLoadTicks();
        for (int i = 0; i < ticks.length; ++i) {
            for (final long[] procTick : procTicks) {
                final long[] array2 = ticks;
                final int n = i;
                array2[n] += procTick[i];
            }
            final long[] array3 = ticks;
            final int n2 = i;
            array3[n2] /= procTicks.length;
        }
        return ticks;
    }
    
    public long[] queryCurrentFreq() {
        final long[] freqs = new long[this.getLogicalProcessorCount()];
        Arrays.fill(freqs, -1L);
        try (final KstatUtil.KstatChain kc = KstatUtil.openChain()) {
            for (int i = 0; i < freqs.length; ++i) {
                for (final LibKstat.Kstat ksp : KstatUtil.KstatChain.lookupAll("cpu_info", i, null)) {
                    if (KstatUtil.KstatChain.read(ksp)) {
                        freqs[i] = KstatUtil.dataLookupLong(ksp, "current_clock_Hz");
                    }
                }
            }
        }
        return freqs;
    }
    
    public long queryMaxFreq() {
        long max = -1L;
        try (final KstatUtil.KstatChain kc = KstatUtil.openChain()) {
            for (final LibKstat.Kstat ksp : KstatUtil.KstatChain.lookupAll("cpu_info", 0, null)) {
                if (KstatUtil.KstatChain.read(ksp)) {
                    final String suppFreq = KstatUtil.dataLookupString(ksp, "supported_frequencies_Hz");
                    if (suppFreq.isEmpty()) {
                        continue;
                    }
                    for (final String s : suppFreq.split(":")) {
                        final long freq = ParseUtil.parseLongOrDefault(s, -1L);
                        if (max < freq) {
                            max = freq;
                        }
                    }
                }
            }
        }
        return max;
    }
    
    @Override
    public double[] getSystemLoadAverage(final int nelem) {
        if (nelem < 1 || nelem > 3) {
            throw new IllegalArgumentException("Must include from one to three elements.");
        }
        final double[] average = new double[nelem];
        final int retval = SolarisLibc.INSTANCE.getloadavg(average, nelem);
        if (retval < nelem) {
            for (int i = Math.max(retval, 0); i < average.length; ++i) {
                average[i] = -1.0;
            }
        }
        return average;
    }
    
    public long[][] queryProcessorCpuLoadTicks() {
        final long[][] ticks = new long[this.getLogicalProcessorCount()][CentralProcessor.TickType.values().length];
        int cpu = -1;
        try (final KstatUtil.KstatChain kc = KstatUtil.openChain()) {
            for (final LibKstat.Kstat ksp : KstatUtil.KstatChain.lookupAll("cpu", -1, "sys")) {
                if (++cpu >= ticks.length) {
                    break;
                }
                if (!KstatUtil.KstatChain.read(ksp)) {
                    continue;
                }
                ticks[cpu][CentralProcessor.TickType.IDLE.getIndex()] = KstatUtil.dataLookupLong(ksp, "cpu_ticks_idle");
                ticks[cpu][CentralProcessor.TickType.SYSTEM.getIndex()] = KstatUtil.dataLookupLong(ksp, "cpu_ticks_kernel");
                ticks[cpu][CentralProcessor.TickType.USER.getIndex()] = KstatUtil.dataLookupLong(ksp, "cpu_ticks_user");
            }
        }
        return ticks;
    }
    
    private static String getProcessorID(final String stepping, final String model, final String family) {
        final List<String> isainfo = ExecutingCommand.runNative("isainfo -v");
        final StringBuilder flags = new StringBuilder();
        for (final String line : isainfo) {
            if (line.startsWith("32-bit")) {
                break;
            }
            if (line.startsWith("64-bit")) {
                continue;
            }
            flags.append(' ').append(line.trim());
        }
        return AbstractCentralProcessor.createProcessorID(stepping, model, family, ParseUtil.whitespaces.split(flags.toString().toLowerCase()));
    }
    
    public long queryContextSwitches() {
        long swtch = 0L;
        final List<String> kstat = ExecutingCommand.runNative("kstat -p cpu_stat:::/pswitch\\\\|inv_swtch/");
        for (final String s : kstat) {
            swtch += ParseUtil.parseLastLong(s, 0L);
        }
        return swtch;
    }
    
    public long queryInterrupts() {
        long intr = 0L;
        final List<String> kstat = ExecutingCommand.runNative("kstat -p cpu_stat:::/intr/");
        for (final String s : kstat) {
            intr += ParseUtil.parseLastLong(s, 0L);
        }
        return intr;
    }
}

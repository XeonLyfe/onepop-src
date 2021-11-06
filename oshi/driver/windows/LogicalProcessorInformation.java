// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.driver.windows;

import java.util.Iterator;
import java.util.Comparator;
import com.sun.jna.platform.win32.WinNT;
import java.util.ArrayList;
import com.sun.jna.platform.win32.Kernel32Util;
import oshi.hardware.CentralProcessor;
import java.util.List;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class LogicalProcessorInformation
{
    private LogicalProcessorInformation() {
    }
    
    public static List<CentralProcessor.LogicalProcessor> getLogicalProcessorInformationEx() {
        final WinNT.SYSTEM_LOGICAL_PROCESSOR_INFORMATION_EX[] procInfo = Kernel32Util.getLogicalProcessorInformationEx(65535);
        final List<WinNT.GROUP_AFFINITY[]> packages = new ArrayList<WinNT.GROUP_AFFINITY[]>();
        final List<WinNT.GROUP_AFFINITY> cores = new ArrayList<WinNT.GROUP_AFFINITY>();
        final List<WinNT.NUMA_NODE_RELATIONSHIP> numaNodes = new ArrayList<WinNT.NUMA_NODE_RELATIONSHIP>();
        for (int i = 0; i < procInfo.length; ++i) {
            switch (procInfo[i].relationship) {
                case 3: {
                    packages.add(((WinNT.PROCESSOR_RELATIONSHIP)procInfo[i]).groupMask);
                    break;
                }
                case 0: {
                    cores.add(((WinNT.PROCESSOR_RELATIONSHIP)procInfo[i]).groupMask[0]);
                    break;
                }
                case 1: {
                    numaNodes.add((WinNT.NUMA_NODE_RELATIONSHIP)procInfo[i]);
                    break;
                }
            }
        }
        cores.sort(Comparator.comparing(c -> c.group * 64L + c.mask.longValue()));
        packages.sort(Comparator.comparing(p -> p[0].group * 64L + p[0].mask.longValue()));
        numaNodes.sort(Comparator.comparing(n -> n.nodeNumber));
        final List<CentralProcessor.LogicalProcessor> logProcs = new ArrayList<CentralProcessor.LogicalProcessor>();
        for (final WinNT.NUMA_NODE_RELATIONSHIP node : numaNodes) {
            final int nodeNum = node.nodeNumber;
            final int group = node.groupMask.group;
            final long mask = node.groupMask.mask.longValue();
            final int lowBit = Long.numberOfTrailingZeros(mask);
            for (int hiBit = 63 - Long.numberOfLeadingZeros(mask), lp = lowBit; lp <= hiBit; ++lp) {
                if ((mask & 1L << lp) != 0x0L) {
                    final CentralProcessor.LogicalProcessor logProc = new CentralProcessor.LogicalProcessor(lp, getMatchingCore(cores, group, lp), getMatchingPackage(packages, group, lp), nodeNum, group);
                    logProcs.add(logProc);
                }
            }
        }
        return logProcs;
    }
    
    private static int getMatchingPackage(final List<WinNT.GROUP_AFFINITY[]> packages, final int g, final int lp) {
        for (int i = 0; i < packages.size(); ++i) {
            for (int j = 0; j < packages.get(i).length; ++j) {
                if ((packages.get(i)[j].mask.longValue() & 1L << lp) != 0x0L && packages.get(i)[j].group == g) {
                    return i;
                }
            }
        }
        return 0;
    }
    
    private static int getMatchingCore(final List<WinNT.GROUP_AFFINITY> cores, final int g, final int lp) {
        for (int j = 0; j < cores.size(); ++j) {
            if ((cores.get(j).mask.longValue() & 1L << lp) != 0x0L && cores.get(j).group == g) {
                return j;
            }
        }
        return 0;
    }
    
    public static List<CentralProcessor.LogicalProcessor> getLogicalProcessorInformation() {
        final List<Long> packageMaskList = new ArrayList<Long>();
        final List<Long> coreMaskList = new ArrayList<Long>();
        final WinNT.SYSTEM_LOGICAL_PROCESSOR_INFORMATION[] logicalProcessorInformation;
        final WinNT.SYSTEM_LOGICAL_PROCESSOR_INFORMATION[] processors = logicalProcessorInformation = Kernel32Util.getLogicalProcessorInformation();
        for (final WinNT.SYSTEM_LOGICAL_PROCESSOR_INFORMATION proc : logicalProcessorInformation) {
            if (proc.relationship == 3) {
                packageMaskList.add(proc.processorMask.longValue());
            }
            else if (proc.relationship == 0) {
                coreMaskList.add(proc.processorMask.longValue());
            }
        }
        coreMaskList.sort(null);
        packageMaskList.sort(null);
        final List<CentralProcessor.LogicalProcessor> logProcs = new ArrayList<CentralProcessor.LogicalProcessor>();
        for (int core = 0; core < coreMaskList.size(); ++core) {
            final long coreMask = coreMaskList.get(core);
            final int lowBit = Long.numberOfTrailingZeros(coreMask);
            for (int hiBit = 63 - Long.numberOfLeadingZeros(coreMask), i = lowBit; i <= hiBit; ++i) {
                if ((coreMask & 1L << i) != 0x0L) {
                    final CentralProcessor.LogicalProcessor logProc = new CentralProcessor.LogicalProcessor(i, core, getBitMatchingPackageNumber(packageMaskList, i));
                    logProcs.add(logProc);
                }
            }
        }
        return logProcs;
    }
    
    private static int getBitMatchingPackageNumber(final List<Long> packageMaskList, final int logProc) {
        for (int i = 0; i < packageMaskList.size(); ++i) {
            if (((long)packageMaskList.get(i) & 1L << logProc) != 0x0L) {
                return i;
            }
        }
        return 0;
    }
}

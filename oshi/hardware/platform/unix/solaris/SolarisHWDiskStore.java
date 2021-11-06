// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.hardware.platform.unix.solaris;

import java.util.Collections;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.Comparator;
import oshi.driver.unix.solaris.disk.Prtvtoc;
import java.util.Iterator;
import oshi.util.tuples.Quintet;
import java.util.Map;
import java.util.ArrayList;
import oshi.driver.unix.solaris.disk.Lshal;
import oshi.driver.unix.solaris.disk.Iostat;
import oshi.hardware.HWDiskStore;
import com.sun.jna.platform.unix.solaris.LibKstat;
import oshi.util.platform.unix.solaris.KstatUtil;
import oshi.hardware.HWPartition;
import java.util.List;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.hardware.common.AbstractHWDiskStore;

@ThreadSafe
public final class SolarisHWDiskStore extends AbstractHWDiskStore
{
    private long reads;
    private long readBytes;
    private long writes;
    private long writeBytes;
    private long currentQueueLength;
    private long transferTime;
    private long timeStamp;
    private List<HWPartition> partitionList;
    
    private SolarisHWDiskStore(final String name, final String model, final String serial, final long size) {
        super(name, model, serial, size);
        this.reads = 0L;
        this.readBytes = 0L;
        this.writes = 0L;
        this.writeBytes = 0L;
        this.currentQueueLength = 0L;
        this.transferTime = 0L;
        this.timeStamp = 0L;
    }
    
    @Override
    public long getReads() {
        return this.reads;
    }
    
    @Override
    public long getReadBytes() {
        return this.readBytes;
    }
    
    @Override
    public long getWrites() {
        return this.writes;
    }
    
    @Override
    public long getWriteBytes() {
        return this.writeBytes;
    }
    
    @Override
    public long getCurrentQueueLength() {
        return this.currentQueueLength;
    }
    
    @Override
    public long getTransferTime() {
        return this.transferTime;
    }
    
    @Override
    public long getTimeStamp() {
        return this.timeStamp;
    }
    
    @Override
    public List<HWPartition> getPartitions() {
        return this.partitionList;
    }
    
    @Override
    public boolean updateAttributes() {
        try (final KstatUtil.KstatChain kc = KstatUtil.openChain()) {
            final LibKstat.Kstat ksp = KstatUtil.KstatChain.lookup(null, 0, this.getName());
            if (ksp != null && KstatUtil.KstatChain.read(ksp)) {
                final LibKstat.KstatIO data = new LibKstat.KstatIO(ksp.ks_data);
                this.reads = data.reads;
                this.writes = data.writes;
                this.readBytes = data.nread;
                this.writeBytes = data.nwritten;
                this.currentQueueLength = data.wcnt + (long)data.rcnt;
                this.transferTime = data.rtime / 1000000L;
                this.timeStamp = ksp.ks_snaptime / 1000000L;
                try (final KstatUtil.KstatChain kc = (KstatUtil.KstatChain)true) {}
                return;
            }
        }
        return false;
    }
    
    public static List<HWDiskStore> getDisks() {
        final Map<String, String> deviceMap = Iostat.queryPartitionToMountMap();
        final Map<String, Integer> majorMap = Lshal.queryDiskToMajorMap();
        final Map<String, Quintet<String, String, String, String, Long>> deviceStringMap = Iostat.queryDeviceStrings(deviceMap.keySet());
        final List<HWDiskStore> storeList = new ArrayList<HWDiskStore>();
        for (final Map.Entry<String, Quintet<String, String, String, String, Long>> entry : deviceStringMap.entrySet()) {
            final String storeName = entry.getKey();
            final Quintet<String, String, String, String, Long> val = entry.getValue();
            storeList.add(createStore(storeName, val.getA(), val.getB(), val.getC(), val.getD(), val.getE(), deviceMap.getOrDefault(storeName, ""), majorMap.getOrDefault(storeName, 0)));
        }
        return storeList;
    }
    
    private static SolarisHWDiskStore createStore(final String diskName, final String model, final String vendor, final String product, final String serial, final long size, final String mount, final int major) {
        final SolarisHWDiskStore store = new SolarisHWDiskStore(diskName, model.isEmpty() ? (vendor + " " + product).trim() : model, serial, size);
        store.partitionList = Collections.unmodifiableList((List<? extends HWPartition>)Prtvtoc.queryPartitions(mount, major).stream().sorted(Comparator.comparing((Function<? super Object, ? extends Comparable>)HWPartition::getName)).collect((Collector<? super Object, ?, List<? extends T>>)Collectors.toList()));
        store.updateAttributes();
        return store;
    }
}

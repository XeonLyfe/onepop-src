// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.driver.windows.wmi;

import com.sun.jna.platform.win32.COM.WbemcliUtil;
import oshi.util.platform.windows.WmiQueryHandler;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class MSFTStorage
{
    private static final String STORAGE_NAMESPACE = "ROOT\\Microsoft\\Windows\\Storage";
    private static final String MSFT_STORAGE_POOL_WHERE_IS_PRIMORDIAL_FALSE = "MSFT_StoragePool WHERE IsPrimordial=FALSE";
    private static final String MSFT_STORAGE_POOL_TO_PHYSICAL_DISK = "MSFT_StoragePoolToPhysicalDisk";
    private static final String MSFT_PHYSICAL_DISK = "MSFT_PhysicalDisk";
    private static final String MSFT_VIRTUAL_DISK = "MSFT_VirtualDisk";
    
    private MSFTStorage() {
    }
    
    public static WbemcliUtil.WmiResult<StoragePoolProperty> queryStoragePools(final WmiQueryHandler h) {
        final WbemcliUtil.WmiQuery<StoragePoolProperty> storagePoolQuery = (WbemcliUtil.WmiQuery<StoragePoolProperty>)new WbemcliUtil.WmiQuery("ROOT\\Microsoft\\Windows\\Storage", "MSFT_StoragePool WHERE IsPrimordial=FALSE", (Class)StoragePoolProperty.class);
        return h.queryWMI(storagePoolQuery, false);
    }
    
    public static WbemcliUtil.WmiResult<StoragePoolToPhysicalDiskProperty> queryStoragePoolPhysicalDisks(final WmiQueryHandler h) {
        final WbemcliUtil.WmiQuery<StoragePoolToPhysicalDiskProperty> storagePoolToPhysicalDiskQuery = (WbemcliUtil.WmiQuery<StoragePoolToPhysicalDiskProperty>)new WbemcliUtil.WmiQuery("ROOT\\Microsoft\\Windows\\Storage", "MSFT_StoragePoolToPhysicalDisk", (Class)StoragePoolToPhysicalDiskProperty.class);
        return h.queryWMI(storagePoolToPhysicalDiskQuery, false);
    }
    
    public static WbemcliUtil.WmiResult<PhysicalDiskProperty> queryPhysicalDisks(final WmiQueryHandler h) {
        final WbemcliUtil.WmiQuery<PhysicalDiskProperty> physicalDiskQuery = (WbemcliUtil.WmiQuery<PhysicalDiskProperty>)new WbemcliUtil.WmiQuery("ROOT\\Microsoft\\Windows\\Storage", "MSFT_PhysicalDisk", (Class)PhysicalDiskProperty.class);
        return h.queryWMI(physicalDiskQuery, false);
    }
    
    public static WbemcliUtil.WmiResult<VirtualDiskProperty> queryVirtualDisks(final WmiQueryHandler h) {
        final WbemcliUtil.WmiQuery<VirtualDiskProperty> virtualDiskQuery = (WbemcliUtil.WmiQuery<VirtualDiskProperty>)new WbemcliUtil.WmiQuery("ROOT\\Microsoft\\Windows\\Storage", "MSFT_VirtualDisk", (Class)VirtualDiskProperty.class);
        return h.queryWMI(virtualDiskQuery, false);
    }
    
    public enum StoragePoolProperty
    {
        FRIENDLYNAME, 
        OBJECTID;
    }
    
    public enum StoragePoolToPhysicalDiskProperty
    {
        STORAGEPOOL, 
        PHYSICALDISK;
    }
    
    public enum PhysicalDiskProperty
    {
        FRIENDLYNAME, 
        PHYSICALLOCATION, 
        OBJECTID;
    }
    
    public enum VirtualDiskProperty
    {
        FRIENDLYNAME, 
        OBJECTID;
    }
}

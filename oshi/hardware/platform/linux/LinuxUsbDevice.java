// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.hardware.platform.linux;

import java.util.Map;
import java.util.HashMap;
import com.sun.jna.platform.linux.Udev;
import java.util.Iterator;
import java.util.Collections;
import java.util.ArrayList;
import oshi.hardware.UsbDevice;
import java.util.List;
import oshi.annotation.concurrent.Immutable;
import oshi.hardware.common.AbstractUsbDevice;

@Immutable
public class LinuxUsbDevice extends AbstractUsbDevice
{
    public LinuxUsbDevice(final String name, final String vendor, final String vendorId, final String productId, final String serialNumber, final String uniqueDeviceId, final List<UsbDevice> connectedDevices) {
        super(name, vendor, vendorId, productId, serialNumber, uniqueDeviceId, connectedDevices);
    }
    
    public static List<UsbDevice> getUsbDevices(final boolean tree) {
        final List<UsbDevice> devices = getUsbDevices();
        if (tree) {
            return devices;
        }
        final List<UsbDevice> deviceList = new ArrayList<UsbDevice>();
        for (final UsbDevice device : devices) {
            deviceList.add(new LinuxUsbDevice(device.getName(), device.getVendor(), device.getVendorId(), device.getProductId(), device.getSerialNumber(), device.getUniqueDeviceId(), Collections.emptyList()));
            addDevicesToList(deviceList, device.getConnectedDevices());
        }
        return deviceList;
    }
    
    private static List<UsbDevice> getUsbDevices() {
        final Udev.UdevContext udev = Udev.INSTANCE.udev_new();
        final Udev.UdevEnumerate enumerate = Udev.INSTANCE.udev_enumerate_new(udev);
        Udev.INSTANCE.udev_enumerate_add_match_subsystem(enumerate, "usb");
        Udev.INSTANCE.udev_enumerate_scan_devices(enumerate);
        final Udev.UdevListEntry devices = Udev.INSTANCE.udev_enumerate_get_list_entry(enumerate);
        final List<String> usbControllers = new ArrayList<String>();
        final Map<String, String> nameMap = new HashMap<String, String>();
        final Map<String, String> vendorMap = new HashMap<String, String>();
        final Map<String, String> vendorIdMap = new HashMap<String, String>();
        final Map<String, String> productIdMap = new HashMap<String, String>();
        final Map<String, String> serialMap = new HashMap<String, String>();
        final Map<String, List<String>> hubMap = new HashMap<String, List<String>>();
        for (Udev.UdevListEntry dev_list_entry = devices; dev_list_entry != null; dev_list_entry = Udev.INSTANCE.udev_list_entry_get_next(dev_list_entry)) {
            final String path = Udev.INSTANCE.udev_list_entry_get_name(dev_list_entry);
            final Udev.UdevDevice dev = Udev.INSTANCE.udev_device_new_from_syspath(udev, path);
            if ("usb_device".equals(Udev.INSTANCE.udev_device_get_devtype(dev))) {
                String value = Udev.INSTANCE.udev_device_get_sysattr_value(dev, "product");
                if (value != null) {
                    nameMap.put(path, value);
                }
                value = Udev.INSTANCE.udev_device_get_sysattr_value(dev, "manufacturer");
                if (value != null) {
                    vendorMap.put(path, value);
                }
                value = Udev.INSTANCE.udev_device_get_sysattr_value(dev, "idVendor");
                if (value != null) {
                    vendorIdMap.put(path, value);
                }
                value = Udev.INSTANCE.udev_device_get_sysattr_value(dev, "idProduct");
                if (value != null) {
                    productIdMap.put(path, value);
                }
                value = Udev.INSTANCE.udev_device_get_sysattr_value(dev, "serial");
                if (value != null) {
                    serialMap.put(path, value);
                }
                final Udev.UdevDevice parent = Udev.INSTANCE.udev_device_get_parent_with_subsystem_devtype(dev, "usb", "usb_device");
                if (parent == null) {
                    usbControllers.add(path);
                }
                else {
                    final String parentPath = Udev.INSTANCE.udev_device_get_syspath(parent);
                    hubMap.computeIfAbsent(parentPath, x -> new ArrayList()).add(path);
                }
                Udev.INSTANCE.udev_device_unref(dev);
            }
        }
        Udev.INSTANCE.udev_enumerate_unref(enumerate);
        Udev.INSTANCE.udev_unref(udev);
        final List<UsbDevice> controllerDevices = new ArrayList<UsbDevice>();
        for (final String controller : usbControllers) {
            controllerDevices.add(getDeviceAndChildren(controller, "0000", "0000", nameMap, vendorMap, vendorIdMap, productIdMap, serialMap, hubMap));
        }
        return controllerDevices;
    }
    
    private static void addDevicesToList(final List<UsbDevice> deviceList, final List<UsbDevice> list) {
        for (final UsbDevice device : list) {
            deviceList.add(device);
            addDevicesToList(deviceList, device.getConnectedDevices());
        }
    }
    
    private static LinuxUsbDevice getDeviceAndChildren(final String devPath, final String vid, final String pid, final Map<String, String> nameMap, final Map<String, String> vendorMap, final Map<String, String> vendorIdMap, final Map<String, String> productIdMap, final Map<String, String> serialMap, final Map<String, List<String>> hubMap) {
        final String vendorId = vendorIdMap.getOrDefault(devPath, vid);
        final String productId = productIdMap.getOrDefault(devPath, pid);
        final List<String> childPaths = hubMap.getOrDefault(devPath, new ArrayList<String>());
        final List<UsbDevice> usbDevices = new ArrayList<UsbDevice>();
        for (final String path : childPaths) {
            usbDevices.add(getDeviceAndChildren(path, vendorId, productId, nameMap, vendorMap, vendorIdMap, productIdMap, serialMap, hubMap));
        }
        Collections.sort(usbDevices);
        return new LinuxUsbDevice(nameMap.getOrDefault(devPath, vendorId + ":" + productId), vendorMap.getOrDefault(devPath, ""), vendorId, productId, serialMap.getOrDefault(devPath, ""), devPath, usbDevices);
    }
}

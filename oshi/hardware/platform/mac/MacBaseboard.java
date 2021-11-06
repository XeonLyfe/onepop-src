// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.hardware.platform.mac;

import com.sun.jna.platform.mac.IOKit;
import oshi.util.Util;
import com.sun.jna.Native;
import java.nio.charset.StandardCharsets;
import com.sun.jna.platform.mac.IOKitUtil;
import oshi.util.Memoizer;
import oshi.util.tuples.Quartet;
import java.util.function.Supplier;
import oshi.annotation.concurrent.Immutable;
import oshi.hardware.common.AbstractBaseboard;

@Immutable
final class MacBaseboard extends AbstractBaseboard
{
    private final Supplier<Quartet<String, String, String, String>> manufModelVersSerial;
    
    MacBaseboard() {
        this.manufModelVersSerial = (Supplier<Quartet<String, String, String, String>>)Memoizer.memoize(MacBaseboard::queryPlatform);
    }
    
    @Override
    public String getManufacturer() {
        return (String)this.manufModelVersSerial.get().getA();
    }
    
    @Override
    public String getModel() {
        return (String)this.manufModelVersSerial.get().getB();
    }
    
    @Override
    public String getVersion() {
        return (String)this.manufModelVersSerial.get().getC();
    }
    
    @Override
    public String getSerialNumber() {
        return (String)this.manufModelVersSerial.get().getD();
    }
    
    private static Quartet<String, String, String, String> queryPlatform() {
        String manufacturer = null;
        String model = null;
        String version = null;
        String serialNumber = null;
        final IOKit.IORegistryEntry platformExpert = (IOKit.IORegistryEntry)IOKitUtil.getMatchingService("IOPlatformExpertDevice");
        if (platformExpert != null) {
            byte[] data = platformExpert.getByteArrayProperty("manufacturer");
            if (data != null) {
                manufacturer = Native.toString(data, StandardCharsets.UTF_8);
            }
            data = platformExpert.getByteArrayProperty("board-id");
            if (data != null) {
                model = Native.toString(data, StandardCharsets.UTF_8);
            }
            if (Util.isBlank(model)) {
                data = platformExpert.getByteArrayProperty("model-number");
                if (data != null) {
                    model = Native.toString(data, StandardCharsets.UTF_8);
                }
            }
            data = platformExpert.getByteArrayProperty("version");
            if (data != null) {
                version = Native.toString(data, StandardCharsets.UTF_8);
            }
            data = platformExpert.getByteArrayProperty("mlb-serial-number");
            if (data != null) {
                serialNumber = Native.toString(data, StandardCharsets.UTF_8);
            }
            if (Util.isBlank(serialNumber)) {
                serialNumber = platformExpert.getStringProperty("IOPlatformSerialNumber");
            }
            platformExpert.release();
        }
        return new Quartet<String, String, String, String>(Util.isBlank(manufacturer) ? "Apple Inc." : manufacturer, Util.isBlank(model) ? "unknown" : model, Util.isBlank(version) ? "unknown" : version, Util.isBlank(serialNumber) ? "unknown" : serialNumber);
    }
}

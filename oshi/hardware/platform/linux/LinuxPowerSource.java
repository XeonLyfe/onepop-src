// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.hardware.platform.linux;

import java.util.Iterator;
import java.util.Map;
import oshi.util.ParseUtil;
import java.util.HashMap;
import oshi.util.FileUtil;
import java.util.ArrayList;
import java.io.File;
import java.util.List;
import java.time.LocalDate;
import oshi.hardware.PowerSource;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.hardware.common.AbstractPowerSource;

@ThreadSafe
public final class LinuxPowerSource extends AbstractPowerSource
{
    private static final String PS_PATH = "/sys/class/power_supply/";
    
    public LinuxPowerSource(final String psName, final String psDeviceName, final double psRemainingCapacityPercent, final double psTimeRemainingEstimated, final double psTimeRemainingInstant, final double psPowerUsageRate, final double psVoltage, final double psAmperage, final boolean psPowerOnLine, final boolean psCharging, final boolean psDischarging, final PowerSource.CapacityUnits psCapacityUnits, final int psCurrentCapacity, final int psMaxCapacity, final int psDesignCapacity, final int psCycleCount, final String psChemistry, final LocalDate psManufactureDate, final String psManufacturer, final String psSerialNumber, final double psTemperature) {
        super(psName, psDeviceName, psRemainingCapacityPercent, psTimeRemainingEstimated, psTimeRemainingInstant, psPowerUsageRate, psVoltage, psAmperage, psPowerOnLine, psCharging, psDischarging, psCapacityUnits, psCurrentCapacity, psMaxCapacity, psDesignCapacity, psCycleCount, psChemistry, psManufactureDate, psManufacturer, psSerialNumber, psTemperature);
    }
    
    public static List<PowerSource> getPowerSources() {
        double psRemainingCapacityPercent = -1.0;
        final double psTimeRemainingEstimated = -1.0;
        final double psTimeRemainingInstant = -1.0;
        double psPowerUsageRate = 0.0;
        double psVoltage = -1.0;
        double psAmperage = 0.0;
        final boolean psPowerOnLine = false;
        boolean psCharging = false;
        boolean psDischarging = false;
        final PowerSource.CapacityUnits psCapacityUnits = PowerSource.CapacityUnits.RELATIVE;
        int psCurrentCapacity = -1;
        int psMaxCapacity = -1;
        final int psDesignCapacity = -1;
        int psCycleCount = -1;
        final LocalDate psManufactureDate = null;
        final double psTemperature = 0.0;
        final File f = new File("/sys/class/power_supply/");
        final String[] psNames = f.list();
        final List<PowerSource> psList = new ArrayList<PowerSource>();
        if (psNames != null) {
            for (final String name : psNames) {
                if (!name.startsWith("ADP") && !name.startsWith("AC")) {
                    final List<String> psInfo = FileUtil.readFile("/sys/class/power_supply/" + name + "/uevent", false);
                    if (!psInfo.isEmpty()) {
                        final Map<String, String> psMap = new HashMap<String, String>();
                        for (final String line : psInfo) {
                            final String[] split = line.split("=");
                            if (split.length > 1 && !split[1].isEmpty()) {
                                psMap.put(split[0], split[1]);
                            }
                        }
                        final String psName = psMap.getOrDefault("POWER_SUPPLY_NAME", name);
                        final String status = psMap.get("POWER_SUPPLY_STATUS");
                        psCharging = "Charging".equals(status);
                        psDischarging = "Discharging".equals(status);
                        if (psMap.containsKey("POWER_SUPPLY_CAPACITY")) {
                            psRemainingCapacityPercent = ParseUtil.parseIntOrDefault(psMap.get("POWER_SUPPLY_CAPACITY"), -100) / 100.0;
                        }
                        if (psMap.containsKey("POWER_SUPPLY_ENERGY_NOW")) {
                            psCurrentCapacity = ParseUtil.parseIntOrDefault(psMap.get("POWER_SUPPLY_ENERGY_NOW"), -1);
                        }
                        else if (psMap.containsKey("POWER_SUPPLY_CHARGE_NOW")) {
                            psCurrentCapacity = ParseUtil.parseIntOrDefault(psMap.get("POWER_SUPPLY_CHARGE_NOW"), -1);
                        }
                        if (psMap.containsKey("POWER_SUPPLY_ENERGY_FULL")) {
                            psCurrentCapacity = ParseUtil.parseIntOrDefault(psMap.get("POWER_SUPPLY_ENERGY_FULL"), 1);
                        }
                        else if (psMap.containsKey("POWER_SUPPLY_CHARGE_FULL")) {
                            psCurrentCapacity = ParseUtil.parseIntOrDefault(psMap.get("POWER_SUPPLY_CHARGE_FULL"), 1);
                        }
                        if (psMap.containsKey("POWER_SUPPLY_ENERGY_FULL_DESIGN")) {
                            psMaxCapacity = ParseUtil.parseIntOrDefault(psMap.get("POWER_SUPPLY_ENERGY_FULL_DESIGN"), 1);
                        }
                        else if (psMap.containsKey("POWER_SUPPLY_CHARGE_FULL_DESIGN")) {
                            psMaxCapacity = ParseUtil.parseIntOrDefault(psMap.get("POWER_SUPPLY_CHARGE_FULL_DESIGN"), 1);
                        }
                        if (psMap.containsKey("POWER_SUPPLY_VOLTAGE_NOW")) {
                            psVoltage = ParseUtil.parseIntOrDefault(psMap.get("POWER_SUPPLY_VOLTAGE_NOW"), -1);
                        }
                        if (psMap.containsKey("POWER_SUPPLY_POWER_NOW")) {
                            psPowerUsageRate = ParseUtil.parseIntOrDefault(psMap.get("POWER_SUPPLY_POWER_NOW"), -1);
                        }
                        if (psVoltage > 0.0) {
                            psAmperage = psPowerUsageRate / psVoltage;
                        }
                        if (psMap.containsKey("POWER_SUPPLY_CYCLE_COUNT")) {
                            psCycleCount = ParseUtil.parseIntOrDefault(psMap.get("POWER_SUPPLY_CYCLE_COUNT"), -1);
                        }
                        final String psChemistry = psMap.getOrDefault("POWER_SUPPLY_TECHNOLOGY", "unknown");
                        final String psDeviceName = psMap.getOrDefault("POWER_SUPPLY_MODEL_NAME", "unknown");
                        final String psManufacturer = psMap.getOrDefault("POWER_SUPPLY_MANUFACTURER", "unknown");
                        final String psSerialNumber = psMap.getOrDefault("POWER_SUPPLY_SERIAL_NUMBER", "unknown");
                        if (ParseUtil.parseIntOrDefault(psMap.get("POWER_SUPPLY_PRESENT"), 1) > 0) {
                            psList.add(new LinuxPowerSource(psName, psDeviceName, psRemainingCapacityPercent, psTimeRemainingEstimated, psTimeRemainingInstant, psPowerUsageRate, psVoltage, psAmperage, psPowerOnLine, psCharging, psDischarging, psCapacityUnits, psCurrentCapacity, psMaxCapacity, psDesignCapacity, psCycleCount, psChemistry, psManufactureDate, psManufacturer, psSerialNumber, psTemperature));
                        }
                    }
                }
            }
        }
        return psList;
    }
}

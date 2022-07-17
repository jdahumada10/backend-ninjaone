package com.ninjaone.backendinterviewproject.util.supplier.model;

import com.ninjaone.backendinterviewproject.model.Cost;
import com.ninjaone.backendinterviewproject.model.DeviceType;
import org.mockito.internal.util.collections.Sets;

import java.util.Set;

public class SupplierCostObjectFaker {

    private static final String DEVICE_COST_ID = "1";
    private static final String USD_CURRENCY = "USD";
    private static final String ANTIVIRUS_WINDOWS_WORKSTATION_COST_ID = "2";
    private static final String ANTIVIRUS_MAC_COST_ID = "3";
    private static final String BACKUP_COST_ID = "4";
    private static final String SCREEN_SHARE_COST_ID = "5";

    public static Set<Cost> getDeviceCosts(){

        return Sets.newSet(deviceCost());
    }

    private static Cost deviceCost(){

        final DeviceType allDeviceType = SupplierDeviceTypeObjectFaker.getAllDeviceType();
        return Cost.builder().id(DEVICE_COST_ID)
                .value(4)
                .currency(USD_CURRENCY)
                .deviceType(allDeviceType)
                .build();
    }

    public static Set<Cost> getAntivirusCosts(){

        return Sets.newSet(antivirusMacCost(),antivirusWindowsWorkstationCost());
    }

    private static Cost antivirusWindowsWorkstationCost(){

        final DeviceType windowsWorkstationDeviceType = SupplierDeviceTypeObjectFaker.getWindowsWorkstationDeviceType();
        return Cost.builder().id(ANTIVIRUS_WINDOWS_WORKSTATION_COST_ID)
                .value(5)
                .currency(USD_CURRENCY)
                .deviceType(windowsWorkstationDeviceType)
                .build();
    }

    private static Cost antivirusMacCost(){

        final DeviceType macDeviceType = SupplierDeviceTypeObjectFaker.getMacDeviceType();
        return Cost.builder().id(ANTIVIRUS_MAC_COST_ID)
                .value(7)
                .currency(USD_CURRENCY)
                .deviceType(macDeviceType)
                .build();
    }

    public static Set<Cost> getBackupCosts(){

        return Sets.newSet(backupCost());
    }

    private static Cost backupCost(){

        final DeviceType allDeviceType = SupplierDeviceTypeObjectFaker.getAllDeviceType();
        return Cost.builder().id(BACKUP_COST_ID)
                .value(3)
                .currency(USD_CURRENCY)
                .deviceType(allDeviceType)
                .build();
    }

    public static Set<Cost> getScreenShareCosts(){

        return Sets.newSet(screenShare());
    }

    public static Cost screenShare(){

        final DeviceType allDeviceType = SupplierDeviceTypeObjectFaker.getAllDeviceType();
        return Cost.builder().id(SCREEN_SHARE_COST_ID)
                .value(1)
                .currency(USD_CURRENCY)
                .deviceType(allDeviceType)
                .build();
    }
}

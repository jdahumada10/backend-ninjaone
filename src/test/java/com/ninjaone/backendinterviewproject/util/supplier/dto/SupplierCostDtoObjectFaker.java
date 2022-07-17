package com.ninjaone.backendinterviewproject.util.supplier.dto;

import com.ninjaone.backendinterviewproject.controller.dto.CostDto;
import com.ninjaone.backendinterviewproject.controller.dto.DeviceTypeDto;
import org.mockito.internal.util.collections.Sets;

import java.util.Set;

public class SupplierCostDtoObjectFaker {

    private static final String DEVICE_COST_ID = "1";
    private static final String USD_CURRENCY = "USD";
    private static final String ANTIVIRUS_WINDOWS_WORKSTATION_COST_ID = "2";
    private static final String ANTIVIRUS_MAC_COST_ID = "3";
    private static final String BACKUP_COST_ID = "4";
    private static final String SCREEN_SHARE_COST_ID = "5";

    public static Set<CostDto> getDeviceCosts(){

        return Sets.newSet(deviceCost());
    }

    private static CostDto deviceCost(){

        final DeviceTypeDto allDeviceType = SupplierDeviceTypeDtoObjectFaker.getAllDeviceType();
        return CostDto.builder().id(DEVICE_COST_ID)
                .value(4)
                .currency(USD_CURRENCY)
                .deviceType(allDeviceType)
                .build();
    }

    public static Set<CostDto> getAntivirusCosts(){

        return Sets.newSet(antivirusMacCost(),antivirusWindowsWorkstationCost());
    }

    private static CostDto antivirusWindowsWorkstationCost(){

        final DeviceTypeDto windowsWorkstationDeviceType = SupplierDeviceTypeDtoObjectFaker.getWindowsWorkstationDeviceType();
        return CostDto.builder().id(ANTIVIRUS_WINDOWS_WORKSTATION_COST_ID)
                .value(5)
                .currency(USD_CURRENCY)
                .deviceType(windowsWorkstationDeviceType)
                .build();
    }

    private static CostDto antivirusMacCost(){

        final DeviceTypeDto macDeviceType = SupplierDeviceTypeDtoObjectFaker.getMacDeviceType();
        return CostDto.builder().id(ANTIVIRUS_MAC_COST_ID)
                .value(7)
                .currency(USD_CURRENCY)
                .deviceType(macDeviceType)
                .build();
    }

    public static Set<CostDto> getBackupCosts(){

        return Sets.newSet(backupCost());
    }

    private static CostDto backupCost(){

        final DeviceTypeDto allDeviceType = SupplierDeviceTypeDtoObjectFaker.getAllDeviceType();
        return CostDto.builder().id(BACKUP_COST_ID)
                .value(3)
                .currency(USD_CURRENCY)
                .deviceType(allDeviceType)
                .build();
    }

    public static Set<CostDto> getScreenShareCosts(){

        return Sets.newSet(screenShare());
    }

    public static CostDto screenShare(){

        final DeviceTypeDto allDeviceType = SupplierDeviceTypeDtoObjectFaker.getAllDeviceType();
        return CostDto.builder().id(SCREEN_SHARE_COST_ID)
                .value(1)
                .currency(USD_CURRENCY)
                .deviceType(allDeviceType)
                .build();
    }
}

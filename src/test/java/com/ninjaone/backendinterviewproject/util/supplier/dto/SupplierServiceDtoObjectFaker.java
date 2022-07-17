package com.ninjaone.backendinterviewproject.util.supplier.dto;

import com.ninjaone.backendinterviewproject.controller.dto.ServiceDto;

public class SupplierServiceDtoObjectFaker {

    public static final String ANTIVIRUS_ID = "1";
    public static final String DEVICE_SERVICE_ID = "2";
    public static final String BACKUP_ID = "3";
    public static final String SCREEN_SHARE_ID = "4";

    public static ServiceDto getAntivirusService(){

        return ServiceDto.builder()
                .id(ANTIVIRUS_ID)
                .description("Antivirus")
                .costs(SupplierCostDtoObjectFaker.getAntivirusCosts())
                .build();
    }

    public static ServiceDto getDeviceService(){

        return ServiceDto.builder().
                id(DEVICE_SERVICE_ID)
                .description("Device")
                .costs(SupplierCostDtoObjectFaker.getDeviceCosts())
                .build();
    }

    public static ServiceDto getBackupService(){

        return ServiceDto.builder().
                id(BACKUP_ID)
                .description("Backup")
                .costs(SupplierCostDtoObjectFaker.getBackupCosts())
                .build();
    }

    public static ServiceDto getScreenShareService(){

        return ServiceDto.builder().
                id(SCREEN_SHARE_ID)
                .description("Screen Share")
                .costs(SupplierCostDtoObjectFaker.getScreenShareCosts())
                .build();
    }


}

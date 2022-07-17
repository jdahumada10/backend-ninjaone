package com.ninjaone.backendinterviewproject.util.supplier.model;

import com.ninjaone.backendinterviewproject.model.Service;

public class SupplierServiceObjectFaker {

    public static final String ANTIVIRUS_ID = "1";
    public static final String DEVICE_SERVICE_ID = "2";
    public static final String BACKUP_ID = "3";
    public static final String SCREEN_SHARE_ID = "4";

    public static Service getAntivirusService(){

        return Service.builder()
                .id(ANTIVIRUS_ID)
                .description("Antivirus")
                .costs(SupplierCostObjectFaker.getAntivirusCosts())
                .build();
    }

    public static Service getDeviceService(){

        return Service.builder().
                id(DEVICE_SERVICE_ID)
                .description("Device")
                .costs(SupplierCostObjectFaker.getDeviceCosts())
                .build();
    }

    public static Service getBackupService(){

        return Service.builder().
                id(BACKUP_ID)
                .description("Backup")
                .costs(SupplierCostObjectFaker.getBackupCosts())
                .build();
    }

    public static Service getScreenShareService(){

        return Service.builder().
                id(SCREEN_SHARE_ID)
                .description("Screen Share")
                .costs(SupplierCostObjectFaker.getScreenShareCosts())
                .build();
    }


}

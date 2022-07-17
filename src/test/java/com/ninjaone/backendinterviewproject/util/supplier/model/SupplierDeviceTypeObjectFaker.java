package com.ninjaone.backendinterviewproject.util.supplier.model;

import com.ninjaone.backendinterviewproject.model.DeviceType;

public class SupplierDeviceTypeObjectFaker {


    private static final String WINDOWS_WORKSTATION_ID = "1";
    private static final String MAC_ID = "2";
    public static final String ALL_ID = "3";

    public static DeviceType getWindowsWorkstationDeviceType(){

        return DeviceType.builder().id(WINDOWS_WORKSTATION_ID).detail("Windows Workstation").build();
    }

    public static DeviceType getMacDeviceType(){

        return DeviceType.builder().id(MAC_ID).detail("Mac").build();
    }

    public static DeviceType getAllDeviceType(){

        return DeviceType.builder().id(ALL_ID).detail("All").build();
    }
}

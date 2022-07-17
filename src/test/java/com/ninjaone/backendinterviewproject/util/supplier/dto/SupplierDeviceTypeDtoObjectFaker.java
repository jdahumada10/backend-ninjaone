package com.ninjaone.backendinterviewproject.util.supplier.dto;

import com.ninjaone.backendinterviewproject.controller.dto.DeviceTypeDto;

public class SupplierDeviceTypeDtoObjectFaker {


    private static final String WINDOWS_WORKSTATION_ID = "1";
    private static final String MAC_ID = "2";
    public static final String ALL_ID = "3";

    public static DeviceTypeDto getWindowsWorkstationDeviceType(){

        return DeviceTypeDto.builder().id(WINDOWS_WORKSTATION_ID).detail("Windows Workstation").build();
    }

    public static DeviceTypeDto getMacDeviceType(){

        return DeviceTypeDto.builder().id(MAC_ID).detail("Mac").build();
    }

    public static DeviceTypeDto getAllDeviceType(){

        return DeviceTypeDto.builder().id(ALL_ID).detail("All").build();
    }
}

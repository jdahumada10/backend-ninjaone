package com.ninjaone.backendinterviewproject.util.supplier.dto;

import com.ninjaone.backendinterviewproject.controller.dto.DeviceDto;
import com.ninjaone.backendinterviewproject.controller.dto.DeviceTypeDto;
import com.ninjaone.backendinterviewproject.controller.dto.ServiceDto;
import org.mockito.internal.util.collections.Sets;

import java.util.Set;

public class SupplierDeviceDtoObjectFaker {

    public static DeviceDto getWindowsDevice(final String deviceId, final String customerId){

        final DeviceTypeDto deviceTypeDto = SupplierDeviceTypeDtoObjectFaker.getWindowsWorkstationDeviceType();
        final Set<ServiceDto> services = Sets.newSet(
                SupplierServiceDtoObjectFaker.getDeviceService(),
                SupplierServiceDtoObjectFaker.getAntivirusService(),
                SupplierServiceDtoObjectFaker.getBackupService(),
                SupplierServiceDtoObjectFaker.getScreenShareService()
        );
        return DeviceDto.builder().id(deviceId).customerId(customerId).systemName("Windows").type(deviceTypeDto).services(services).build();
    }

    public static DeviceDto getMacDevice(final String deviceId, final String customerId){

        final DeviceTypeDto deviceTypeDto = SupplierDeviceTypeDtoObjectFaker.getMacDeviceType();
        final Set<ServiceDto> services = Sets.newSet(
                SupplierServiceDtoObjectFaker.getDeviceService(),
                SupplierServiceDtoObjectFaker.getAntivirusService(),
                SupplierServiceDtoObjectFaker.getBackupService(),
                SupplierServiceDtoObjectFaker.getScreenShareService()
        );
        return DeviceDto.builder().id(deviceId).customerId(customerId).systemName("Mac").type(deviceTypeDto).services(services).build();
    }
}

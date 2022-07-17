package com.ninjaone.backendinterviewproject.util.supplier.dto;

import com.ninjaone.backendinterviewproject.controller.dto.DeviceCostDto;
import com.ninjaone.backendinterviewproject.controller.dto.DeviceDto;
import com.ninjaone.backendinterviewproject.controller.dto.ServiceDto;

import java.util.stream.Collectors;

public class SupplierDeviceCostDtoObjectFaker {

    public static DeviceCostDto getDefaultDeviceCost(final String deviceId, final String customerId){

        final DeviceDto device = SupplierDeviceDtoObjectFaker.getMacDevice(deviceId, customerId);
        return DeviceCostDto.builder()
                .deviceId(deviceId)
                .deviceType(device.getType().getDetail())
                .deviceCost(15.0)
                .services(device.getServices()
                                .stream()
                                .map(ServiceDto::getDescription)
                                .collect(Collectors.toSet()))
                .build();
    }
}

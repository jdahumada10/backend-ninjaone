package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.controller.dto.DeviceCostDto;
import com.ninjaone.backendinterviewproject.controller.dto.DeviceDto;
import com.ninjaone.backendinterviewproject.controller.exception.model.NotAuthorizedException;

import java.util.List;

public interface DeviceService {
    DeviceDto updateDevice(String customerId, DeviceDto deviceDto, String deviceId) throws NotAuthorizedException;
    DeviceDto createDevice(String customerId, DeviceDto deviceDto) throws NotAuthorizedException;
    DeviceDto getDevice(String customerId, String deviceId) throws NotAuthorizedException;
    void deleteDevice(String customerId, String id) throws NotAuthorizedException;
    DeviceCostDto getDeviceTotalCost(String customerId, String deviceId) throws NotAuthorizedException;

    List<DeviceDto> getAllCustomerDevices(String customerId) throws NotAuthorizedException;
}

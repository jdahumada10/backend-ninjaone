package com.ninjaone.backendinterviewproject.controller;

import com.ninjaone.backendinterviewproject.controller.dto.DeviceCostDto;
import com.ninjaone.backendinterviewproject.controller.dto.DeviceDto;
import com.ninjaone.backendinterviewproject.controller.exception.model.NotAuthorizedException;
import com.ninjaone.backendinterviewproject.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1.0/")
public class DeviceController {
    private final DeviceService deviceService;

    @PreAuthorize("hasRole('ADMIN') OR hasRole('CUSTOMER')")
    @PostMapping("customers/{customerId}/devices")
    public ResponseEntity<DeviceDto> createDevice(@RequestBody @Valid DeviceDto device, @PathVariable String customerId) throws NotAuthorizedException {

        device.setCustomerId(customerId);
        final DeviceDto updatedDevice = deviceService.createDevice(customerId,device);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedDevice);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('CUSTOMER')")
    @PutMapping("customers/{customerId}/devices/{deviceId}")
    public ResponseEntity<DeviceDto> updateDevice(@RequestBody @Valid DeviceDto device, @PathVariable String customerId, @PathVariable String deviceId) throws NotAuthorizedException {

        device.setCustomerId(customerId);
        device.setId(deviceId);
        final DeviceDto updatedDevice = deviceService.updateDevice(customerId,device,deviceId);
        return updatedDevice != null ? ResponseEntity.ok(updatedDevice) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('CUSTOMER')")
    @GetMapping("customers/{customerId}/devices/{deviceId}")
    public ResponseEntity<DeviceDto> getDevice(@PathVariable String customerId, @PathVariable String deviceId) throws NotAuthorizedException{

        final DeviceDto device = deviceService.getDevice(customerId, deviceId);
        return device != null ? ResponseEntity.ok(device) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('CUSTOMER')")
    @GetMapping("customers/{customerId}/devices")
    public ResponseEntity<List<DeviceDto>> getAllDevices(@PathVariable String customerId) throws NotAuthorizedException{

        final List<DeviceDto> devices = deviceService.getAllCustomerDevices(customerId);
        return devices != null ? ResponseEntity.ok(devices) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('CUSTOMER')")
    @GetMapping("customers/{customerId}/devices/{deviceId}/totalCost")
    public ResponseEntity<DeviceCostDto> getDeviceTotalCost(@PathVariable String customerId, @PathVariable String deviceId) throws NotAuthorizedException{

        final DeviceCostDto deviceCost = deviceService.getDeviceTotalCost(customerId, deviceId);
        return deviceCost != null ? ResponseEntity.ok(deviceCost) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('CUSTOMER')")
    @DeleteMapping("customers/{customerId}/devices/{deviceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDevice(@PathVariable String customerId, @PathVariable String deviceId) throws NotAuthorizedException {

        deviceService.deleteDevice(customerId, deviceId);
    }
}

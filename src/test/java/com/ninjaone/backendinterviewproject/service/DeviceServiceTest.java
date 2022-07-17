package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.controller.dto.DeviceCostDto;
import com.ninjaone.backendinterviewproject.controller.dto.DeviceDto;
import com.ninjaone.backendinterviewproject.controller.dto.TotalCostDto;
import com.ninjaone.backendinterviewproject.database.DeviceRepository;
import com.ninjaone.backendinterviewproject.model.Customer;
import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.service.impl.DeviceServiceImpl;
import com.ninjaone.backendinterviewproject.util.supplier.dto.SupplierDeviceCostDtoObjectFaker;
import com.ninjaone.backendinterviewproject.util.supplier.dto.SupplierDeviceDtoObjectFaker;
import com.ninjaone.backendinterviewproject.util.supplier.model.SupplierCustomerObjectFaker;
import com.ninjaone.backendinterviewproject.util.supplier.model.SupplierDeviceObjectFaker;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeviceServiceTest {

    public static final String CUSTOMER_ID = "12345";
    public static final String DEVICE_ID = "123";

    @Mock
    private DeviceRepository deviceRepository;

    private DeviceService deviceService;

    @BeforeEach
    @SneakyThrows
    public void init(){
        deviceService = new DeviceServiceImpl(deviceRepository);
    }

    @Test
    @SneakyThrows
    void getDeviceData() {
        final Device device = SupplierDeviceObjectFaker.getMacDevice(DEVICE_ID, CUSTOMER_ID);
        when(deviceRepository.findByIdAndCustomerId(DEVICE_ID, CUSTOMER_ID)).thenReturn(Optional.of(device));
        final DeviceDto expected = SupplierDeviceDtoObjectFaker.getMacDevice(DEVICE_ID, CUSTOMER_ID);
        final DeviceDto response = deviceService.getDevice(CUSTOMER_ID,DEVICE_ID);
        assert response != null;
        assertEquals(expected.getId(), response.getId());
        assertEquals(expected.getCustomerId(), response.getCustomerId());
        assertEquals(expected.getType().getId(), response.getType().getId());
        assertEquals(expected.getType().getDetail(), response.getType().getDetail());
        assertEquals(expected.getServices().size(), response.getServices().size());
        assertEquals(expected.getSystemName(), response.getSystemName());
    }

    @Test
    @SneakyThrows
    void getAllDevicesData() {
        final List<Device> devices = Collections.singletonList(SupplierDeviceObjectFaker.getWindowsDevice(DEVICE_ID, CUSTOMER_ID));
        when(deviceRepository.findAllByCustomerId(CUSTOMER_ID)).thenReturn(devices);
        final List<DeviceDto> expected = Collections.singletonList(SupplierDeviceDtoObjectFaker.getWindowsDevice(DEVICE_ID, CUSTOMER_ID));
        final List<DeviceDto> response = deviceService.getAllCustomerDevices(CUSTOMER_ID);
        assertNotNull(response);
        assertEquals(expected.get(0).getId(), response.get(0).getId());
        assertEquals(expected.get(0).getCustomerId(), response.get(0).getCustomerId());
        assertEquals(expected.get(0).getType().getId(), response.get(0).getType().getId());
        assertEquals(expected.get(0).getType().getDetail(), response.get(0).getType().getDetail());
        assertEquals(expected.get(0).getServices().size(), response.get(0).getServices().size());
        assertEquals(expected.get(0).getSystemName(), response.get(0).getSystemName());
    }

    @Test
    @SneakyThrows
    void createDevice(){
        final Device device = SupplierDeviceObjectFaker.getWindowsDevice(DEVICE_ID,CUSTOMER_ID);
        when(deviceRepository.save(any(Device.class))).thenReturn(device);
        final DeviceDto expected = SupplierDeviceDtoObjectFaker.getWindowsDevice(DEVICE_ID,CUSTOMER_ID);
        final DeviceDto response = deviceService.createDevice(CUSTOMER_ID,SupplierDeviceDtoObjectFaker.getWindowsDevice(DEVICE_ID,CUSTOMER_ID));
        assert response != null;
        assertEquals(expected.getId(), response.getId());
        assertEquals(expected.getCustomerId(), response.getCustomerId());
        assertEquals(expected.getType().getId(), response.getType().getId());
        assertEquals(expected.getType().getDetail(), response.getType().getDetail());
        assertEquals(expected.getServices().size(), response.getServices().size());
        assertEquals(expected.getSystemName(), response.getSystemName());
    }

    @Test
    @SneakyThrows
    void updateDevice_DeviceExists(){
        final Device device = SupplierDeviceObjectFaker.getMacDevice(DEVICE_ID,CUSTOMER_ID);
        when(deviceRepository.findByIdAndCustomerId(DEVICE_ID, CUSTOMER_ID)).thenReturn(Optional.of(device));
        when(deviceRepository.save(any(Device.class))).thenReturn(device);
        final DeviceDto expected = SupplierDeviceDtoObjectFaker.getMacDevice(DEVICE_ID,CUSTOMER_ID);
        final DeviceDto response = deviceService.updateDevice(CUSTOMER_ID, expected, DEVICE_ID);
        assertEquals(expected.getId(), response.getId());
        assertEquals(expected.getCustomerId(), response.getCustomerId());
        assertEquals(expected.getType().getId(), response.getType().getId());
        assertEquals(expected.getType().getDetail(), response.getType().getDetail());
        assertEquals(expected.getServices().size(), response.getServices().size());
        assertEquals(expected.getSystemName(), response.getSystemName());
    }

    @Test
    @SneakyThrows
    void updateDevice_DeviceNotExists(){

        when(deviceRepository.findByIdAndCustomerId(DEVICE_ID, CUSTOMER_ID)).thenReturn(Optional.empty());
        final DeviceDto device = SupplierDeviceDtoObjectFaker.getWindowsDevice(DEVICE_ID,CUSTOMER_ID);
        final DeviceDto response = deviceService.updateDevice(CUSTOMER_ID, device, DEVICE_ID);
        assertNull(response);
    }

    @Test
    @SneakyThrows
    void deleteDeviceData(){
        final Device device = SupplierDeviceObjectFaker.getMacDevice(DEVICE_ID,CUSTOMER_ID);
        when(deviceRepository.findByIdAndCustomerId(DEVICE_ID, CUSTOMER_ID)).thenReturn(Optional.of(device));
        doNothing().when(deviceRepository).deleteById(DEVICE_ID);
        deviceService.deleteDevice(CUSTOMER_ID,DEVICE_ID);
        Mockito.verify(deviceRepository, times(1)).deleteById(DEVICE_ID);
    }

    @Test
    @SneakyThrows
    void deleteDeviceData_DeviceNotExists(){
        when(deviceRepository.findByIdAndCustomerId(DEVICE_ID, CUSTOMER_ID)).thenReturn(Optional.empty());
        deviceService.deleteDevice(CUSTOMER_ID,DEVICE_ID);
        verify(deviceRepository,never()).deleteById(DEVICE_ID);
    }

    @Test
    @SneakyThrows
    void getDeviceCost(){
        final Device device = SupplierDeviceObjectFaker.getMacDevice(DEVICE_ID,CUSTOMER_ID);
        when(deviceRepository.findByIdAndCustomerId(DEVICE_ID, CUSTOMER_ID)).thenReturn(Optional.of(device));
        final DeviceCostDto expected = SupplierDeviceCostDtoObjectFaker.getDefaultDeviceCost(DEVICE_ID,CUSTOMER_ID);
        final DeviceCostDto response = deviceService.getDeviceTotalCost(CUSTOMER_ID, DEVICE_ID);
        assertEquals(expected.getDeviceCost(),response.getDeviceCost());
        assertEquals(expected.getDeviceId(),response.getDeviceId());
        assertEquals(expected.getDeviceType(),response.getDeviceType());
        assertEquals(expected.getServices().size(),response.getServices().size());
    }

    @Test
    @SneakyThrows
    void getDeviceDevicesCost_DeviceNotExists(){
        when(deviceRepository.findByIdAndCustomerId(DEVICE_ID, CUSTOMER_ID)).thenReturn(Optional.empty());
        final DeviceCostDto response = deviceService.getDeviceTotalCost(CUSTOMER_ID, DEVICE_ID);
        assertNull(response);
    }
}

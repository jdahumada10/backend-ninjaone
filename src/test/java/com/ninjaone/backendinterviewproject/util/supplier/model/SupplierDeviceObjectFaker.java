package com.ninjaone.backendinterviewproject.util.supplier.model;

import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.model.DeviceType;
import com.ninjaone.backendinterviewproject.model.Service;
import org.mockito.internal.util.collections.Sets;

import java.util.Set;

public class SupplierDeviceObjectFaker {

    public static Device getWindowsDevice(final String deviceId, final String customerId){

        final DeviceType deviceType = SupplierDeviceTypeObjectFaker.getWindowsWorkstationDeviceType();
        final Set<Service> services = Sets.newSet(
                SupplierServiceObjectFaker.getDeviceService(),
                SupplierServiceObjectFaker.getAntivirusService(),
                SupplierServiceObjectFaker.getBackupService(),
                SupplierServiceObjectFaker.getScreenShareService()
        );
        return Device.builder().id(deviceId).customerId(customerId).systemName("Windows").type(deviceType).services(services).build();
    }

    public static Device getMacDevice(final String deviceId, final String customerId){

        final DeviceType deviceType = SupplierDeviceTypeObjectFaker.getMacDeviceType();
        final Set<Service> services = Sets.newSet(
                SupplierServiceObjectFaker.getDeviceService(),
                SupplierServiceObjectFaker.getAntivirusService(),
                SupplierServiceObjectFaker.getBackupService(),
                SupplierServiceObjectFaker.getScreenShareService()
        );
        return Device.builder().id(deviceId).customerId(customerId).systemName("Mac").type(deviceType).services(services).build();
    }
}

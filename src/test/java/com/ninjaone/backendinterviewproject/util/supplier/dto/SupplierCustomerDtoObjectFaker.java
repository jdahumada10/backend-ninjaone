package com.ninjaone.backendinterviewproject.util.supplier.dto;

import com.ninjaone.backendinterviewproject.controller.dto.CustomerDto;
import com.ninjaone.backendinterviewproject.controller.dto.DeviceDto;
import com.ninjaone.backendinterviewproject.controller.dto.RoleDto;
import org.mockito.internal.util.collections.Sets;

import java.util.Set;

public class SupplierCustomerDtoObjectFaker {

    public static CustomerDto getCustomer(final String id) {

        final Set<RoleDto> roles = Sets.newSet(SupplierRoleDtoObjectFaker.getCustomerRole());
        final Set<DeviceDto> devices = Sets.newSet(
                SupplierDeviceDtoObjectFaker.getWindowsDevice("1",id),
                SupplierDeviceDtoObjectFaker.getWindowsDevice("2",id),
                SupplierDeviceDtoObjectFaker.getMacDevice("3",id),
                SupplierDeviceDtoObjectFaker.getMacDevice("4",id),
                SupplierDeviceDtoObjectFaker.getMacDevice("5",id)
        );
        return CustomerDto.builder().id(id).email("test@test.com").password("test").roles(roles).devices(devices).build();
    }
}

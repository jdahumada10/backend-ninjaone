package com.ninjaone.backendinterviewproject.util.supplier.model;

import com.ninjaone.backendinterviewproject.model.Customer;
import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.model.Role;
import org.mockito.internal.util.collections.Sets;

import java.util.Set;

public class SupplierCustomerObjectFaker {

    public static Customer getCustomer(final String id) {

        final Set<Role> roles = Sets.newSet(SupplierRoleObjectFaker.getCustomerRole());
        final Set<Device> devices = Sets.newSet(
                SupplierDeviceObjectFaker.getWindowsDevice("1",id),
                SupplierDeviceObjectFaker.getWindowsDevice("2",id),
                SupplierDeviceObjectFaker.getMacDevice("3",id),
                SupplierDeviceObjectFaker.getMacDevice("4",id),
                SupplierDeviceObjectFaker.getMacDevice("5",id)
        );
        return Customer.builder().id(id).email("test@test.com").password("test").roles(roles).devices(devices).build();
    }
}

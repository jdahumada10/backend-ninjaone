package com.ninjaone.backendinterviewproject.util.supplier.model;

import com.ninjaone.backendinterviewproject.model.Role;

public class SupplierRoleObjectFaker {

    public static Role getAdminRole() {

        return Role.builder().id("1").name("ADMIN").build();
    }

    public static Role getCustomerRole() {

        return Role.builder().id("2").name("CUSTOMER").build();
    }
}

package com.ninjaone.backendinterviewproject.util.supplier.dto;

import com.ninjaone.backendinterviewproject.controller.dto.RoleDto;

public class SupplierRoleDtoObjectFaker {

    public static RoleDto getAdminRole() {

        return RoleDto.builder().id("1").name("ADMIN").build();
    }

    public static RoleDto getCustomerRole() {

        return RoleDto.builder().id("2").name("CUSTOMER").build();
    }
}

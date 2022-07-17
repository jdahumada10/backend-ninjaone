package com.ninjaone.backendinterviewproject.controller.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@SuperBuilder
public class CustomerDto extends UserDto {

    public CustomerDto(final String id, final String email, final String password, final Set<RoleDto> roles, final Set<DeviceDto> devices){
        super(id,email,password,roles);
        this.devices = devices;
    }

    private Set<DeviceDto> devices;
}

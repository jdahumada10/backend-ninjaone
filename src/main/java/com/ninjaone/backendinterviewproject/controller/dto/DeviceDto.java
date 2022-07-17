package com.ninjaone.backendinterviewproject.controller.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Builder
public class DeviceDto {
    private String id;
    @NotNull
    private String systemName;
    private String customerId;
    @NotNull
    private DeviceTypeDto type;
    @NotNull
    private Set<ServiceDto> services;

    public void setId(String id) {
        this.id = id;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}

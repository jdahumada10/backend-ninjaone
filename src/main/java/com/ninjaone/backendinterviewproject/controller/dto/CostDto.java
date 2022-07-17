package com.ninjaone.backendinterviewproject.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CostDto {
    private String id;
    private double value;
    private String currency;
    private String serviceId;
    private DeviceTypeDto deviceType;
}

package com.ninjaone.backendinterviewproject.controller.dto;

import lombok.Builder;
import lombok.Getter;
import java.util.Set;

@Getter
@Builder
public class DeviceCostDto {
    private String deviceId;
    private String deviceType;
    private Set<String> services;
    private double deviceCost;
}

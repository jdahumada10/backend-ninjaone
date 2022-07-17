package com.ninjaone.backendinterviewproject.controller.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TotalCostDto {
    private List<DeviceCostDto> devices;
    private double totalCost;
}

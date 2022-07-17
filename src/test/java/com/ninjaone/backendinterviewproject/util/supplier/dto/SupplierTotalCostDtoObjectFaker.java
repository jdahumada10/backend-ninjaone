package com.ninjaone.backendinterviewproject.util.supplier.dto;

import com.ninjaone.backendinterviewproject.controller.dto.DeviceCostDto;
import com.ninjaone.backendinterviewproject.controller.dto.TotalCostDto;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;

public class SupplierTotalCostDtoObjectFaker {

    public static TotalCostDto getDefaultTotalCost(){

        final DeviceCostDto device = DeviceCostDto.builder()
                .deviceId("1")
                .deviceType(SupplierDeviceTypeDtoObjectFaker.getWindowsWorkstationDeviceType().getDetail())
                .deviceCost(10)
                .services(Sets.set("Antivirus", "Device")).build();
        return TotalCostDto.builder().devices(Lists.list(device)).totalCost(10).build();
    }
}

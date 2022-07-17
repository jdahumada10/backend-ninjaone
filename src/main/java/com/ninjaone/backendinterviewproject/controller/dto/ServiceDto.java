package com.ninjaone.backendinterviewproject.controller.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class ServiceDto {
    private String id;
    private String description;
    private Set<CostDto> costs;

    public void setId(String id) {
        this.id = id;
    }
}

package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.controller.dto.ServiceDto;

import java.util.List;

public interface ServiceService {
    ServiceDto createService(ServiceDto service);

    ServiceDto updateService(ServiceDto service, String serviceId);

    ServiceDto getService(String serviceId);

    void deleteService(String serviceId);

    List<ServiceDto> getAllServices();
}

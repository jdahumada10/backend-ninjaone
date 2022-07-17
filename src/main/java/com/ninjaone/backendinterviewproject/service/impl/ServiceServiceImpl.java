package com.ninjaone.backendinterviewproject.service.impl;

import com.ninjaone.backendinterviewproject.controller.dto.ServiceDto;
import com.ninjaone.backendinterviewproject.database.ServiceRepository;
import com.ninjaone.backendinterviewproject.model.Service;
import com.ninjaone.backendinterviewproject.service.ServiceService;
import com.ninjaone.backendinterviewproject.service.mapper.ServiceMapper;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;


    @Override
    public ServiceDto createService(final ServiceDto serviceDto) {

        final Service service = ServiceMapper.INSTANCE.serviceDtoToService(serviceDto);
        service.setId(null);
        final Service savedService = serviceRepository.save(service);
        return ServiceMapper.INSTANCE.serviceToServiceDto(savedService);
    }

    @Override
    public ServiceDto updateService(final ServiceDto serviceDto, final String serviceId) {

        final Optional<Service> optionalService = serviceRepository.findById(serviceId);
        if(optionalService.isPresent()){
            final Service savedService = serviceRepository.save(ServiceMapper.INSTANCE.serviceDtoToService(serviceDto));
            return ServiceMapper.INSTANCE.serviceToServiceDto(savedService);
        }
        return null;
    }

    @Override
    public ServiceDto getService(String serviceId) {

        final Optional<Service> service = serviceRepository.findById(serviceId);
        return service.map(ServiceMapper.INSTANCE::serviceToServiceDto).orElse(null);
    }

    @Override
    public void deleteService(String serviceId) {

        final Optional<Service> optionalService = serviceRepository.findById(serviceId);
        if(optionalService.isPresent()){
            serviceRepository.deleteById(serviceId);
        }
    }

    @Override
    public List<ServiceDto> getAllServices() {

        List<ServiceDto> services = new ArrayList<>();
        serviceRepository.findAll().forEach(service -> services.add(ServiceMapper.INSTANCE.serviceToServiceDto(service)));
        return services;
    }
}

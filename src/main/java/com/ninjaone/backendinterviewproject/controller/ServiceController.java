package com.ninjaone.backendinterviewproject.controller;

import com.ninjaone.backendinterviewproject.controller.dto.ServiceDto;
import com.ninjaone.backendinterviewproject.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1.0/")
public class ServiceController {
    private final ServiceService serviceService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("services")
    public ResponseEntity<ServiceDto> createService(@RequestBody ServiceDto service) {

        final ServiceDto createdService = serviceService.createService(service);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdService);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("services/{serviceId}")
    public ResponseEntity<ServiceDto> updateService(@RequestBody ServiceDto service, @PathVariable String serviceId) {

        service.setId(serviceId);
        final ServiceDto updatedService = serviceService.updateService(service, serviceId);
        return updatedService != null ? ResponseEntity.ok(updatedService) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("services/{serviceId}")
    public ResponseEntity<ServiceDto> getService(@PathVariable String serviceId) {

        final ServiceDto service = serviceService.getService(serviceId);
        return service != null ? ResponseEntity.ok(service) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("services")
    public ResponseEntity<List<ServiceDto>> getAllServices() {

        final List<ServiceDto> services = serviceService.getAllServices();
        return services != null ? ResponseEntity.ok(services) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("services/{serviceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteService(@PathVariable String serviceId) {

        serviceService.deleteService(serviceId);
    }
}

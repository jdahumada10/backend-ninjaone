package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.controller.dto.ServiceDto;
import com.ninjaone.backendinterviewproject.database.CostRepository;
import com.ninjaone.backendinterviewproject.database.ServiceRepository;
import com.ninjaone.backendinterviewproject.model.Customer;
import com.ninjaone.backendinterviewproject.model.Service;
import com.ninjaone.backendinterviewproject.service.impl.ServiceServiceImpl;
import com.ninjaone.backendinterviewproject.util.supplier.dto.SupplierServiceDtoObjectFaker;
import com.ninjaone.backendinterviewproject.util.supplier.model.SupplierCustomerObjectFaker;
import com.ninjaone.backendinterviewproject.util.supplier.model.SupplierServiceObjectFaker;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceServiceTest {

    @Mock
    private ServiceRepository serviceRepository;

    private ServiceService serviceService;

    @BeforeEach
    @SneakyThrows
    public void init(){
        serviceService = new ServiceServiceImpl(serviceRepository);
    }

    @Test
    void getServiceData() {
        final Service service = SupplierServiceObjectFaker.getAntivirusService();
        when(serviceRepository.findById(SupplierServiceObjectFaker.ANTIVIRUS_ID)).thenReturn(Optional.of(service));
        final ServiceDto expected = SupplierServiceDtoObjectFaker.getAntivirusService();
        final ServiceDto response = serviceService.getService(SupplierServiceObjectFaker.ANTIVIRUS_ID);
        assert response != null;
        assertEquals(expected.getId(), response.getId());
        assertEquals(expected.getDescription(), response.getDescription());
        assertEquals(expected.getCosts().size(), response.getCosts().size());
    }

    @Test
    void getAllServicesData() {
        final List<Service> services = Collections.singletonList(SupplierServiceObjectFaker.getBackupService());
        when(serviceRepository.findAll()).thenReturn(services);
        final List<ServiceDto> expected = Collections.singletonList(SupplierServiceDtoObjectFaker.getBackupService());
        final List<ServiceDto> response = serviceService.getAllServices();
        assertNotNull(response);
        assertEquals(expected.get(0).getId(), response.get(0).getId());
        assertEquals(expected.get(0).getDescription(), response.get(0).getDescription());
        assertEquals(expected.get(0).getCosts().size(), response.get(0).getCosts().size());
    }

    @Test
    void createService(){
        final Service service = SupplierServiceObjectFaker.getScreenShareService();
        when(serviceRepository.save(any(Service.class))).thenReturn(service);
        final ServiceDto expected = SupplierServiceDtoObjectFaker.getScreenShareService();
        final ServiceDto response = serviceService.createService(expected);
        assert response != null;
        assertEquals(expected.getId(), response.getId());
        assertEquals(expected.getDescription(), response.getDescription());
        assertEquals(expected.getCosts().size(), response.getCosts().size());
    }

    @Test
    void updateService_ServiceExists(){
        final Service service = SupplierServiceObjectFaker.getDeviceService();
        when(serviceRepository.findById(SupplierServiceObjectFaker.DEVICE_SERVICE_ID)).thenReturn(Optional.of(service));
        when(serviceRepository.save(any(Service.class))).thenReturn(service);
        final ServiceDto expected = SupplierServiceDtoObjectFaker.getDeviceService();
        final ServiceDto response = serviceService.updateService(expected, SupplierServiceObjectFaker.DEVICE_SERVICE_ID);
        assertEquals(expected.getId(), response.getId());
        assertEquals(expected.getDescription(), response.getDescription());
        assertEquals(expected.getCosts().size(), response.getCosts().size());
    }

    @Test
    void updateService_ServiceNotExists(){
        when(serviceRepository.findById(SupplierServiceObjectFaker.ANTIVIRUS_ID)).thenReturn(Optional.empty());
        final ServiceDto service = SupplierServiceDtoObjectFaker.getAntivirusService();
        final ServiceDto response = serviceService.updateService(service, SupplierServiceObjectFaker.ANTIVIRUS_ID);
        assertNull(response);
    }


    @Test
    void deleteServiceData(){
        final Service service = SupplierServiceObjectFaker.getAntivirusService();
        when(serviceRepository.findById(SupplierServiceObjectFaker.ANTIVIRUS_ID)).thenReturn(Optional.of(service));
        doNothing().when(serviceRepository).deleteById(SupplierServiceObjectFaker.ANTIVIRUS_ID);
        serviceService.deleteService(SupplierServiceObjectFaker.ANTIVIRUS_ID);
        Mockito.verify(serviceRepository, times(1)).deleteById(SupplierServiceObjectFaker.ANTIVIRUS_ID);
    }

    @Test
    void deleteServiceData_ServiceNotExists(){
        when(serviceRepository.findById(SupplierServiceObjectFaker.ANTIVIRUS_ID)).thenReturn(Optional.empty());
        serviceService.deleteService(SupplierServiceObjectFaker.ANTIVIRUS_ID);
        verify(serviceRepository,never()).deleteById(SupplierServiceObjectFaker.ANTIVIRUS_ID);
    }
}
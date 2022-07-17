package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.controller.dto.CustomerDto;
import com.ninjaone.backendinterviewproject.controller.dto.TotalCostDto;
import com.ninjaone.backendinterviewproject.database.CustomerRepository;
import com.ninjaone.backendinterviewproject.model.Customer;
import com.ninjaone.backendinterviewproject.service.impl.CustomerServiceImpl;
import com.ninjaone.backendinterviewproject.util.supplier.dto.SupplierCustomerDtoObjectFaker;
import com.ninjaone.backendinterviewproject.util.supplier.model.SupplierCustomerObjectFaker;
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

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    public static final String CUSTOMER_ID = "12345";

    @Mock
    private CustomerRepository customerRepository;

    private CustomerService customerService;

    @BeforeEach
    @SneakyThrows
    public void init(){
        customerService = new CustomerServiceImpl(customerRepository);
    }

    @Test
    void getCustomerData() {
        final Customer customer = SupplierCustomerObjectFaker.getCustomer(CUSTOMER_ID);
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        final CustomerDto expected = SupplierCustomerDtoObjectFaker.getCustomer(CUSTOMER_ID);
        final CustomerDto response = customerService.getCustomer(CUSTOMER_ID);
        assert response != null;
        assertEquals(expected.getId(), response.getId());
        assertEquals(expected.getEmail(), response.getEmail());
        assertEquals(expected.getPassword(), response.getPassword());
        assertEquals(expected.getDevices().size(), response.getDevices().size());
        assertEquals(expected.getRoles().size(), response.getRoles().size());
    }

    @Test
    void getAllCustomersData() {
        final List<Customer> customers = Collections.singletonList(SupplierCustomerObjectFaker.getCustomer(CUSTOMER_ID));
        when(customerRepository.findAll()).thenReturn(customers);
        final List<CustomerDto> expected = Collections.singletonList(SupplierCustomerDtoObjectFaker.getCustomer(CUSTOMER_ID));
        final List<CustomerDto> response = customerService.getAllCustomers();
        assertNotNull(response);
        assertEquals(expected.get(0).getId(), response.get(0).getId());
        assertEquals(expected.get(0).getEmail(), response.get(0).getEmail());
        assertEquals(expected.get(0).getPassword(), response.get(0).getPassword());
        assertEquals(expected.get(0).getDevices().size(), response.get(0).getDevices().size());
        assertEquals(expected.get(0).getRoles().size(), response.get(0).getRoles().size());
    }

    @Test
    void createCustomer(){
        final Customer customer = SupplierCustomerObjectFaker.getCustomer(CUSTOMER_ID);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        final CustomerDto expected = SupplierCustomerDtoObjectFaker.getCustomer(CUSTOMER_ID);
        final CustomerDto response = customerService.createCustomer(expected);
        assert response != null;
        assertEquals(expected.getId(), response.getId());
        assertEquals(expected.getEmail(), response.getEmail());
        assertEquals(expected.getPassword(), response.getPassword());
        assertEquals(expected.getDevices().size(), response.getDevices().size());
        assertEquals(expected.getRoles().size(), response.getRoles().size());
    }

    @Test
    void updateCustomer_CustomerExists(){
        final Customer customer = SupplierCustomerObjectFaker.getCustomer(CUSTOMER_ID);
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        final CustomerDto expected = SupplierCustomerDtoObjectFaker.getCustomer(CUSTOMER_ID);
        final CustomerDto response = customerService.updateCustomer(expected, CUSTOMER_ID);
        assertEquals(expected.getId(), response.getId());
        assertEquals(expected.getEmail(), response.getEmail());
        assertEquals(expected.getPassword(), response.getPassword());
        assertEquals(expected.getDevices().size(), response.getDevices().size());
        assertEquals(expected.getRoles().size(), response.getRoles().size());
    }

    @Test
    void updateCustomer_CustomerNotExists(){

        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.empty());
        final CustomerDto customer = SupplierCustomerDtoObjectFaker.getCustomer(CUSTOMER_ID);
        final CustomerDto response = customerService.updateCustomer(customer, CUSTOMER_ID);
        assertNull(response);
    }

    @Test
    void deleteCustomerData(){
        final Customer customer = SupplierCustomerObjectFaker.getCustomer(CUSTOMER_ID);
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        doNothing().when(customerRepository).deleteById(CUSTOMER_ID);
        customerService.deleteCustomer(CUSTOMER_ID);
        Mockito.verify(customerRepository, times(1)).deleteById(CUSTOMER_ID);
    }

    @Test
    void deleteCustomerData_CustomerNotExists(){
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.empty());
        customerService.deleteCustomer(CUSTOMER_ID);
        verify(customerRepository,never()).deleteById(CUSTOMER_ID);
    }

    @Test
    @SneakyThrows
    void getCustomerDevicesCost(){
        final Customer customer = SupplierCustomerObjectFaker.getCustomer(CUSTOMER_ID);
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        final TotalCostDto response = customerService.getDevicesTotalCost(CUSTOMER_ID);
        assertEquals(71,response.getTotalCost());
        assertEquals(5,response.getDevices().size());
    }

    @Test
    @SneakyThrows
    void getCustomerDevicesCost_CustomerNotExists(){
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.empty());
        final TotalCostDto response = customerService.getDevicesTotalCost(CUSTOMER_ID);
        assertNull(response);
    }
}

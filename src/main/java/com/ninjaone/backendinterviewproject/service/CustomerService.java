package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.controller.dto.CustomerDto;
import com.ninjaone.backendinterviewproject.controller.dto.TotalCostDto;
import com.ninjaone.backendinterviewproject.controller.exception.model.NotAuthorizedException;

import java.util.List;

public interface CustomerService {
    CustomerDto createCustomer(CustomerDto customerDto);
    CustomerDto updateCustomer(CustomerDto customerDto, String customerId);
    CustomerDto getCustomer(String customerId);
    List<CustomerDto> getAllCustomers();
    void deleteCustomer(String customerId);
    TotalCostDto getDevicesTotalCost(String customerId) throws NotAuthorizedException;
}

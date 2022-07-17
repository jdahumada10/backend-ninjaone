package com.ninjaone.backendinterviewproject.service.impl;

import com.ninjaone.backendinterviewproject.controller.dto.CustomerDto;
import com.ninjaone.backendinterviewproject.controller.dto.DeviceCostDto;
import com.ninjaone.backendinterviewproject.controller.dto.TotalCostDto;
import com.ninjaone.backendinterviewproject.controller.exception.model.NotAuthorizedException;
import com.ninjaone.backendinterviewproject.database.CustomerRepository;
import com.ninjaone.backendinterviewproject.model.Customer;
import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.service.CustomerService;
import com.ninjaone.backendinterviewproject.service.mapper.CustomerMapper;
import com.ninjaone.backendinterviewproject.util.UserUtilities;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerDto createCustomer(final CustomerDto customerDto) {

        final Customer customer = CustomerMapper.INSTANCE.customerDtoToCustomer(customerDto);
        customer.setId(null);
        final Customer savedCustomer = customerRepository.save(customer);
        return CustomerMapper.INSTANCE.customerToCustomerDto(savedCustomer);
    }

    @Override
    public CustomerDto updateCustomer(final CustomerDto customerDto, final String customerId) {

        final Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if(optionalCustomer.isPresent()){
            final Customer savedCustomer = customerRepository.save(CustomerMapper.INSTANCE.customerDtoToCustomer(customerDto));
            return CustomerMapper.INSTANCE.customerToCustomerDto(savedCustomer);
        }
        return null;
    }

    @Override
    public CustomerDto getCustomer(final String customerId) {
        final Optional<Customer> customer = customerRepository.findById(customerId);
        return customer.map(CustomerMapper.INSTANCE::customerToCustomerDto).orElse(null);
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        List<CustomerDto> customers = new ArrayList<>();
        customerRepository.findAll().forEach(customer -> customers.add(CustomerMapper.INSTANCE.customerToCustomerDto(customer)));
        return customers;
    }

    @Override
    public void deleteCustomer(final String customerId) {

        final Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if(optionalCustomer.isPresent()){
            customerRepository.deleteById(customerId);
        }
    }

    @Override
    public TotalCostDto getDevicesTotalCost(final String customerId) throws NotAuthorizedException {

        UserUtilities.validateConsistency(customerId);
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if(optionalCustomer.isPresent()){
            final Set<Device> devices = optionalCustomer.get().getDevices();
            final List<DeviceCostDto> deviceCosts = getDeviceCosts(devices);
            final double totalCost = deviceCosts.stream().map(DeviceCostDto::getDeviceCost).reduce(0.0, Double::sum);
            return TotalCostDto.builder().devices(deviceCosts).totalCost(totalCost).build();
        }
        return null;
    }

    private List<DeviceCostDto> getDeviceCosts(final Set<Device> devices) {
        List<DeviceCostDto> deviceCosts = new ArrayList<>();
        for (Device device : devices) {
            final double deviceCost = device.calculateDeviceCost();
            deviceCosts.add(DeviceCostDto.builder()
                    .deviceId(device.getId())
                    .services(device.getServicesNames())
                    .deviceCost(deviceCost)
                    .deviceType(device.getType().getDetail())
                    .build());
        }
        return deviceCosts;
    }
}

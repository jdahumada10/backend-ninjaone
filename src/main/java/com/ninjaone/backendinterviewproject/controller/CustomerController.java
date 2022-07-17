package com.ninjaone.backendinterviewproject.controller;

import com.ninjaone.backendinterviewproject.controller.dto.CustomerDto;
import com.ninjaone.backendinterviewproject.controller.dto.TotalCostDto;
import com.ninjaone.backendinterviewproject.controller.exception.model.NotAuthorizedException;
import com.ninjaone.backendinterviewproject.service.CustomerService;
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

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1.0/")
public class CustomerController {

    private final CustomerService customerService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("customers")
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CustomerDto customerDto){

        final CustomerDto updatedCustomer = customerService.createCustomer(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedCustomer);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("customers/{customerId}")
    public ResponseEntity<CustomerDto> updateCustomer(@Valid @RequestBody CustomerDto customerDto, @PathVariable String customerId){

        customerDto.setId(customerId);
        final CustomerDto updatedCustomer = customerService.updateCustomer(customerDto, customerId);
        return updatedCustomer!=null ? ResponseEntity.ok(updatedCustomer) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("customers/{customerId}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable String customerId){

        final CustomerDto customer = customerService.getCustomer(customerId);
        return customer != null ? ResponseEntity.ok(customer) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('CUSTOMER')")
    @GetMapping("customers/{customerId}/totalCost")
    public ResponseEntity<TotalCostDto> getDevicesTotalCost(@PathVariable String customerId) throws NotAuthorizedException {

        final TotalCostDto totalCostDto = customerService.getDevicesTotalCost(customerId);
        return totalCostDto != null ? ResponseEntity.ok(totalCostDto) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("customers")
    public ResponseEntity<List<CustomerDto>> getAllCustomers(){

        final List<CustomerDto> customers = customerService.getAllCustomers();
        return customers != null ? ResponseEntity.ok(customers) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("customers/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable String customerId){

        customerService.deleteCustomer(customerId);
    }
}

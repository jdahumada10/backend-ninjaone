package com.ninjaone.backendinterviewproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaone.backendinterviewproject.BackendInterviewProjectApplication;
import com.ninjaone.backendinterviewproject.controller.dto.CustomerDto;
import com.ninjaone.backendinterviewproject.controller.dto.TotalCostDto;
import com.ninjaone.backendinterviewproject.security.ApplicationSecurity;
import com.ninjaone.backendinterviewproject.security.JwtTokenFilter;
import com.ninjaone.backendinterviewproject.security.JwtTokenUtil;
import com.ninjaone.backendinterviewproject.service.CustomerService;
import com.ninjaone.backendinterviewproject.util.supplier.dto.SupplierCustomerDtoObjectFaker;
import com.ninjaone.backendinterviewproject.util.supplier.dto.SupplierTotalCostDtoObjectFaker;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BackendInterviewProjectApplication.class, ApplicationSecurity.class, JwtTokenFilter.class, JwtTokenUtil.class})
@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureDataJpa
@ActiveProfiles(value = "test")
class CustomerControllerTest {
    public static final String ID = "12345";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getCustomerData_WhenCustomerExists() throws Exception {
        final CustomerDto expected = SupplierCustomerDtoObjectFaker.getCustomer(ID);
        when(customerService.getCustomer(ID)).thenReturn(expected);
        mockMvc.perform(get("/v1.0/customers/" + ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(expected)));
    }

    @Test
    void getCustomerData_WhenCustomerNotExists() throws Exception {
        when(customerService.getCustomer(ID)).thenReturn(null);
        mockMvc.perform(get("/v1.0/customers/" + ID))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    void postCustomerData() throws Exception {
        final CustomerDto expected = SupplierCustomerDtoObjectFaker.getCustomer(ID);
        when(customerService.createCustomer(any(CustomerDto.class))).thenReturn(expected);
        String sampleEntityString = objectMapper.writeValueAsString(expected);
        mockMvc.perform(post("/v1.0/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sampleEntityString))
                .andExpect(status().isCreated())
                .andExpect(content().string(sampleEntityString));
    }

    @Test
    void updateCustomerData() throws Exception {
        final CustomerDto expected = SupplierCustomerDtoObjectFaker.getCustomer(ID);
        when(customerService.updateCustomer(any(CustomerDto.class),anyString())).thenReturn(expected);
        String sampleEntityString = objectMapper.writeValueAsString(expected);
        mockMvc.perform(put("/v1.0/customers/" + ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sampleEntityString))
                .andExpect(status().isOk())
                .andExpect(content().string(sampleEntityString));
    }

    @Test
    void getAllCustomersData() throws Exception {
        final List<CustomerDto> expected = Lists.list(SupplierCustomerDtoObjectFaker.getCustomer(ID));
        when(customerService.getAllCustomers()).thenReturn(expected);
        mockMvc.perform(get("/v1.0/customers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(expected)));
    }

    @Test
    void getCustomerTotalCostData() throws Exception {
        final TotalCostDto expected = SupplierTotalCostDtoObjectFaker.getDefaultTotalCost();
        when(customerService.getDevicesTotalCost(ID)).thenReturn(expected);
        mockMvc.perform(get("/v1.0/customers/"+ID+"/totalCost"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(expected)));
    }

    @Test
    void deleteCustomerData() throws Exception {

        doNothing().when(customerService).deleteCustomer(ID);
        mockMvc.perform(delete("/v1.0/customers/" + ID))
                .andExpect(status().isNoContent());
    }
}

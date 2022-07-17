package com.ninjaone.backendinterviewproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaone.backendinterviewproject.BackendInterviewProjectApplication;
import com.ninjaone.backendinterviewproject.controller.dto.ServiceDto;
import com.ninjaone.backendinterviewproject.util.supplier.dto.SupplierServiceDtoObjectFaker;
import com.ninjaone.backendinterviewproject.security.ApplicationSecurity;
import com.ninjaone.backendinterviewproject.security.JwtTokenFilter;
import com.ninjaone.backendinterviewproject.security.JwtTokenUtil;
import com.ninjaone.backendinterviewproject.service.ServiceService;
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
@WebMvcTest(ServiceController.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureDataJpa
@ActiveProfiles(value = "test")
class ServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceService serviceService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getServiceData_WhenServiceExists() throws Exception {
        final ServiceDto expected = SupplierServiceDtoObjectFaker.getAntivirusService();
        when(serviceService.getService(SupplierServiceDtoObjectFaker.ANTIVIRUS_ID)).thenReturn(expected);
        mockMvc.perform(get("/v1.0/services/" + SupplierServiceDtoObjectFaker.ANTIVIRUS_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(expected)));
    }

    @Test
    void getServiceData_WhenServiceNotExists() throws Exception {
        when(serviceService.getService(SupplierServiceDtoObjectFaker.ANTIVIRUS_ID)).thenReturn(null);
        mockMvc.perform(get("/v1.0/services/" + SupplierServiceDtoObjectFaker.ANTIVIRUS_ID))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    void postServiceData() throws Exception {
        final ServiceDto expected = SupplierServiceDtoObjectFaker.getDeviceService();
        when(serviceService.createService(any())).thenReturn(expected);
        String sampleEntityString = objectMapper.writeValueAsString(expected);
        mockMvc.perform(post("/v1.0/services")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sampleEntityString))
                .andExpect(status().isCreated())
                .andExpect(content().string(sampleEntityString));
    }

    @Test
    void updateServiceData() throws Exception {
        final ServiceDto expected = SupplierServiceDtoObjectFaker.getScreenShareService();
        when(serviceService.updateService(any(),anyString())).thenReturn(expected);
        String sampleEntityString = objectMapper.writeValueAsString(expected);
        mockMvc.perform(put("/v1.0/services/" + SupplierServiceDtoObjectFaker.SCREEN_SHARE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sampleEntityString))
                .andExpect(status().isOk())
                .andExpect(content().string(sampleEntityString));
    }

    @Test
    void getAllServicesData() throws Exception {
        final List<ServiceDto> expected = Lists.list(SupplierServiceDtoObjectFaker.getDeviceService());
        when(serviceService.getAllServices()).thenReturn(expected);
        mockMvc.perform(get("/v1.0/services"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(expected)));
    }

    @Test
    void deleteServiceData() throws Exception {

        doNothing().when(serviceService).deleteService(SupplierServiceDtoObjectFaker.ANTIVIRUS_ID);
        mockMvc.perform(delete("/v1.0/services/" + SupplierServiceDtoObjectFaker.ANTIVIRUS_ID))
                .andExpect(status().isNoContent());
    }
}
package com.ninjaone.backendinterviewproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaone.backendinterviewproject.BackendInterviewProjectApplication;
import com.ninjaone.backendinterviewproject.controller.dto.DeviceCostDto;
import com.ninjaone.backendinterviewproject.controller.dto.DeviceDto;
import com.ninjaone.backendinterviewproject.util.supplier.dto.SupplierDeviceDtoObjectFaker;
import com.ninjaone.backendinterviewproject.util.supplier.dto.SupplierDeviceTypeDtoObjectFaker;
import com.ninjaone.backendinterviewproject.security.ApplicationSecurity;
import com.ninjaone.backendinterviewproject.security.JwtTokenFilter;
import com.ninjaone.backendinterviewproject.security.JwtTokenUtil;
import com.ninjaone.backendinterviewproject.service.DeviceService;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
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
@WebMvcTest(DeviceController.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureDataJpa
@ActiveProfiles(value = "test")
class DeviceControllerTest {
    public static final String DEVICE_ID = "12345";
    public static final String CUSTOMER_ID = "123";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeviceService deviceService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getDeviceData_WhenDeviceExists() throws Exception {
        final DeviceDto expected = SupplierDeviceDtoObjectFaker.getWindowsDevice(DEVICE_ID, CUSTOMER_ID);
        when(deviceService.getDevice(CUSTOMER_ID,DEVICE_ID)).thenReturn(expected);
        mockMvc.perform(get("/v1.0/customers/" + CUSTOMER_ID +"/devices/"+DEVICE_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(expected)));
    }

    @Test
    void getDeviceData_WhenDeviceNotExists() throws Exception {
        when(deviceService.getDevice(CUSTOMER_ID,DEVICE_ID)).thenReturn(null);
        mockMvc.perform(get("/v1.0/customers/" + CUSTOMER_ID +"/devices/"+DEVICE_ID))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    void postDeviceData() throws Exception {
        final DeviceDto expected = SupplierDeviceDtoObjectFaker.getMacDevice(DEVICE_ID,CUSTOMER_ID);
        when(deviceService.createDevice(anyString(),any(DeviceDto.class))).thenReturn(expected);
        String sampleEntityString = objectMapper.writeValueAsString(expected);
        mockMvc.perform(post("/v1.0/customers/" + CUSTOMER_ID +"/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sampleEntityString))
                .andExpect(status().isCreated())
                .andExpect(content().string(sampleEntityString));
    }

    @Test
    void updateDeviceData() throws Exception {

        final DeviceDto expected = SupplierDeviceDtoObjectFaker.getMacDevice(DEVICE_ID,CUSTOMER_ID);
        when(deviceService.updateDevice(anyString(),any(DeviceDto.class),anyString())).thenReturn(expected);
        String sampleEntityString = objectMapper.writeValueAsString(expected);
        mockMvc.perform(put("/v1.0/customers/" + CUSTOMER_ID +"/devices/"+DEVICE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sampleEntityString))
                .andExpect(status().isOk())
                .andExpect(content().string(sampleEntityString));
    }

    @Test
    void getAllCustomersData() throws Exception {
        final List<DeviceDto> expected = Lists.list(SupplierDeviceDtoObjectFaker.getMacDevice(DEVICE_ID,CUSTOMER_ID));
        when(deviceService.getAllCustomerDevices(CUSTOMER_ID)).thenReturn(expected);
        mockMvc.perform(get("/v1.0/customers/" + CUSTOMER_ID +"/devices"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(expected)));
    }

    @Test
    void getDeviceTotalCostData() throws Exception {
        final DeviceCostDto expected = DeviceCostDto.builder()
                .deviceId("1")
                .deviceType(SupplierDeviceTypeDtoObjectFaker.getWindowsWorkstationDeviceType().getDetail())
                .deviceCost(10)
                .services(Sets.set("Antivirus", "Device")).build();
        when(deviceService.getDeviceTotalCost(CUSTOMER_ID,DEVICE_ID)).thenReturn(expected);
        mockMvc.perform(get("/v1.0/customers/"+CUSTOMER_ID+"/devices/"+DEVICE_ID+"/totalCost"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(expected)));
    }

    @Test
    void deleteCustomerData() throws Exception {

        doNothing().when(deviceService).deleteDevice(CUSTOMER_ID,DEVICE_ID);
        mockMvc.perform(delete("/v1.0/customers/" + CUSTOMER_ID +"/devices/"+DEVICE_ID))
                .andExpect(status().isNoContent());
    }
}

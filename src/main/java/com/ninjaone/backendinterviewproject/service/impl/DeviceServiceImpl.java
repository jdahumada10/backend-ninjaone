package com.ninjaone.backendinterviewproject.service.impl;

import com.ninjaone.backendinterviewproject.controller.dto.DeviceCostDto;
import com.ninjaone.backendinterviewproject.controller.dto.DeviceDto;
import com.ninjaone.backendinterviewproject.controller.exception.model.NotAuthorizedException;
import com.ninjaone.backendinterviewproject.database.DeviceRepository;
import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.service.DeviceService;
import com.ninjaone.backendinterviewproject.service.mapper.DeviceMapper;
import com.ninjaone.backendinterviewproject.util.UserUtilities;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;

    @Override
    public DeviceDto updateDevice(final String customerId, final DeviceDto deviceDto, final String deviceId) throws NotAuthorizedException {

        UserUtilities.validateConsistency(customerId);
        return updateDevice(deviceDto, deviceId, customerId);
    }

    private DeviceDto updateDevice(final DeviceDto deviceDto, final String deviceId, final String customerId) {

        final Optional<Device> optionalDevice = deviceRepository.findByIdAndCustomerId(deviceId, customerId);
        if(optionalDevice.isPresent()){
            final Device savedDevice = deviceRepository.save(DeviceMapper.INSTANCE.deviceDtoToDevice(deviceDto));
            return DeviceMapper.INSTANCE.deviceToDeviceDto(savedDevice);
        }
        return null;
    }

    @Override
    public DeviceDto createDevice(String customerId, DeviceDto deviceDto) throws NotAuthorizedException {

        UserUtilities.validateConsistency(customerId);
        deviceDto.setId(null);
        return createDevice(deviceDto, customerId);
    }

    private DeviceDto createDevice(final DeviceDto deviceDto, final String customerId) {

        final Device device = DeviceMapper.INSTANCE.deviceDtoToDevice(deviceDto);
        device.setCustomerId(customerId);
        final Device savedDevice = deviceRepository.save(device);
        return DeviceMapper.INSTANCE.deviceToDeviceDto(savedDevice);
    }

    @Override
    public DeviceDto getDevice(final String customerId, final String deviceId) throws NotAuthorizedException{

        UserUtilities.validateConsistency(customerId);
        final Optional<Device> optionalDevice = deviceRepository.findByIdAndCustomerId(deviceId, customerId);
        return optionalDevice.map(DeviceMapper.INSTANCE::deviceToDeviceDto).orElse(null);
    }

    @Override
    public void deleteDevice(final String customerId, final String deviceId) throws NotAuthorizedException{

        UserUtilities.validateConsistency(customerId);
        final Optional<Device> optionalDevice = deviceRepository.findByIdAndCustomerId(deviceId, customerId);
        if(optionalDevice.isPresent()){
            deviceRepository.deleteById(deviceId);
        }
    }

    @Override
    public DeviceCostDto getDeviceTotalCost(String customerId, String deviceId) throws NotAuthorizedException {

        UserUtilities.validateConsistency(customerId);
        final Optional<Device> deviceOptional = deviceRepository.findByIdAndCustomerId(deviceId, customerId);
        if(deviceOptional.isPresent()){
            final Device device = deviceOptional.get();
            return DeviceCostDto.builder()
                    .deviceId(device.getId())
                    .deviceCost(device.calculateDeviceCost())
                    .deviceType(device.getType().getDetail())
                    .services(device.getServicesNames())
                    .build();
        }
        return null;
    }

    @Override
    public List<DeviceDto> getAllCustomerDevices(String customerId) throws NotAuthorizedException {

        UserUtilities.validateConsistency(customerId);
        List<DeviceDto> devices = new ArrayList<>();
        deviceRepository.findAllByCustomerId(customerId).forEach(device -> devices.add(DeviceMapper.INSTANCE.deviceToDeviceDto(device)));
        return devices;
    }
}

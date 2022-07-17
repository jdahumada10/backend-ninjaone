package com.ninjaone.backendinterviewproject.service.mapper;

import com.ninjaone.backendinterviewproject.controller.dto.DeviceDto;
import com.ninjaone.backendinterviewproject.model.Device;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DeviceMapper {

    DeviceMapper INSTANCE = Mappers.getMapper( DeviceMapper.class );

    DeviceDto deviceToDeviceDto(final Device device);

    Device deviceDtoToDevice(final DeviceDto deviceDto);
}

package com.ninjaone.backendinterviewproject.service.mapper;

import com.ninjaone.backendinterviewproject.controller.dto.ServiceDto;
import com.ninjaone.backendinterviewproject.model.Service;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ServiceMapper {

    ServiceMapper INSTANCE = Mappers.getMapper( ServiceMapper.class );

    ServiceDto serviceToServiceDto(final Service service);

    Service serviceDtoToService(final ServiceDto serviceDto);
}

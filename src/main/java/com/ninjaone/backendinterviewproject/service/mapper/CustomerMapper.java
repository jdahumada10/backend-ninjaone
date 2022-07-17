package com.ninjaone.backendinterviewproject.service.mapper;

import com.ninjaone.backendinterviewproject.controller.dto.CustomerDto;
import com.ninjaone.backendinterviewproject.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Mapper
public interface CustomerMapper {
    
    CustomerMapper INSTANCE = Mappers.getMapper( CustomerMapper.class );

    CustomerDto customerToCustomerDto(final Customer customer);

    @Mapping(source = "password", target = "password", qualifiedByName = "encodePassword")
    Customer customerDtoToCustomer(final CustomerDto customerDto);

    @Named("encodePassword")
    static String passwordEncode(String password) {
        if(password!=null){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            return passwordEncoder.encode(password);
        }
        return null;
    }
}

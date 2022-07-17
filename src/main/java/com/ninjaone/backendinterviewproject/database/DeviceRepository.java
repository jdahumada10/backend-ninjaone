package com.ninjaone.backendinterviewproject.database;

import com.ninjaone.backendinterviewproject.model.Device;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceRepository extends CrudRepository<Device, String> {

    Iterable<Device> findAllByCustomerId(String customerId);

    Optional<Device> findByIdAndCustomerId(String deviceId, String customerId);
}

package com.ninjaone.backendinterviewproject.database;

import com.ninjaone.backendinterviewproject.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer> {

}
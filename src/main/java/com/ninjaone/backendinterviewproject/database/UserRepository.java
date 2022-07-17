package com.ninjaone.backendinterviewproject.database;

import com.ninjaone.backendinterviewproject.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findByEmail(String email);
}

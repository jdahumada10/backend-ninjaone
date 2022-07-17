package com.ninjaone.backendinterviewproject.database;

import com.ninjaone.backendinterviewproject.model.Cost;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostRepository extends CrudRepository<Cost, String> {
}

package com.ps.spring.finalex.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.ps.spring.finalex.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {}

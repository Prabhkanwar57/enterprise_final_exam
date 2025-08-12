package com.ps.spring.finalex.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.ps.spring.finalex.model.Payment;

public interface PaymentRepository extends MongoRepository<Payment, String> {}

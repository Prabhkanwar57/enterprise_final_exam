
package com.ps.spring.finalex.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.ps.spring.finalex.model.Reservation;

public interface ReservationRepository extends MongoRepository<Reservation, String> { }

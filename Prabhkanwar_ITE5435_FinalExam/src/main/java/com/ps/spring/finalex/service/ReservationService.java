package com.ps.spring.finalex.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ps.spring.finalex.dto.ReservationRequest;
import com.ps.spring.finalex.model.Customer;
import com.ps.spring.finalex.model.Payment;
import com.ps.spring.finalex.model.Reservation;
import com.ps.spring.finalex.repo.CustomerRepository;
import com.ps.spring.finalex.repo.PaymentRepository;
import com.ps.spring.finalex.repo.ReservationRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;

@Service
public class ReservationService {

  private final ReservationRepository reservationRepo;
  private final CustomerRepository customerRepo;
  private final PaymentRepository paymentRepo;
  private final ObjectMapper mapper;

  public ReservationService(ReservationRepository reservationRepo,
                            CustomerRepository customerRepo,
                            PaymentRepository paymentRepo,
                            ObjectMapper mapper) {
    this.reservationRepo = reservationRepo;
    this.customerRepo = customerRepo;
    this.paymentRepo = paymentRepo;
    this.mapper = mapper;
  }

  /** CREATE from form */
  public Reservation createFromForm(ReservationRequest req) throws Exception {
    // Convert form to JSON (optional proof for the assignment)
    String jsonPayload = mapper.writeValueAsString(req); // can log if you want

    double amount = computeFareWithJackson(req.getTravelClass(), req.getPassengers());

    // Customer
    Customer customer = new Customer();
    customer.setFirstName(req.getFirstName());
    customer.setLastName(req.getLastName());
    customer.setPhone(req.getPhone());
    customer = customerRepo.save(customer);

    // Payment
    Payment payment = new Payment();
    payment.setAmount(amount);
    payment.setMethod(req.getPaymentMethod());
    payment.setPaidAt(LocalDateTime.now());
    payment.setStatus("PAID");
    payment = paymentRepo.save(payment);

    // Reservation
    Reservation r = new Reservation();
    r.setFirstName(req.getFirstName());
    r.setLastName(req.getLastName());
    r.setPassengers(req.getPassengers());
    r.setTravelClass(req.getTravelClass());
    r.setPhone(req.getPhone());
    r.setTime(req.getTime());
    r.setDateOfDeparting(req.getDateOfDeparting());
    r.setCustomerId(customer.getId());
    r.setPaymentId(payment.getId());
    r.setTotalAmount(amount);

    return reservationRepo.save(r);
  }


  public Reservation updateFromForm(String id, ReservationRequest req) throws Exception {
    Reservation existing = reservationRepo.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Reservation not found: " + id));

    double amount = computeFareWithJackson(req.getTravelClass(), req.getPassengers());

    Customer customer = (existing.getCustomerId() != null)
        ? customerRepo.findById(existing.getCustomerId()).orElse(new Customer())
        : new Customer();
    customer.setFirstName(req.getFirstName());
    customer.setLastName(req.getLastName());
    customer.setPhone(req.getPhone());
    customer = customerRepo.save(customer);
    existing.setCustomerId(customer.getId());

    Payment payment = (existing.getPaymentId() != null)
        ? paymentRepo.findById(existing.getPaymentId()).orElse(new Payment())
        : new Payment();
    payment.setAmount(amount);
    payment.setMethod(req.getPaymentMethod());
    payment.setStatus("PAID");
    payment = paymentRepo.save(payment);
    existing.setPaymentId(payment.getId());

    // Update reservation fields
    existing.setFirstName(req.getFirstName());
    existing.setLastName(req.getLastName());
    existing.setPassengers(req.getPassengers());
    existing.setTravelClass(req.getTravelClass());
    existing.setPhone(req.getPhone());
    existing.setTime(req.getTime());
    existing.setDateOfDeparting(req.getDateOfDeparting());
    existing.setTotalAmount(amount);

    return reservationRepo.save(existing);
  }

  
  public void deleteCascade(String reservationId) {
    Reservation r = reservationRepo.findById(reservationId)
        .orElseThrow(() -> new IllegalArgumentException("Reservation not found: " + reservationId));

    if (r.getPaymentId() != null) {
      paymentRepo.deleteById(r.getPaymentId());
    }

   

    reservationRepo.deleteById(reservationId);
  }

  /** Price = base * classMultiplier * passengers (loaded via Jackson) */
  private double computeFareWithJackson(String travelClass, int passengers) throws Exception {
    try (InputStream is = new ClassPathResource("fares.json").getInputStream()) {
      JsonNode root = mapper.readTree(is);
      double base = root.path("baseFare").asDouble(120.0);
      double classMultiplier = root.path("classMultiplier")
                                   .path(travelClass.toUpperCase())
                                   .asDouble(1.0);
      return base * classMultiplier * passengers;
    }
  }
}

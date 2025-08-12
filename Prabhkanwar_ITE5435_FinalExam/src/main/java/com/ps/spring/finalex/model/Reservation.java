package com.ps.spring.finalex.model;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.*;

@Document(collection = "reservations")
public class Reservation {
  @Id private String id;

  @NotBlank private String firstName;
  @NotBlank private String lastName;
  @Min(1) @Max(10) private int passengers;
  @NotBlank private String travelClass;            // ECONOMY/BUSINESS/FIRST
  @Pattern(regexp="^[0-9]{10}$", message="Phone must be 10 digits")
  private String phone;
  @NotNull private LocalTime time;
  @NotNull private LocalDate dateOfDeparting;

  private String customerId;  // saved document id
  private String paymentId;   // saved document id
  private double totalAmount;

  // getters & setters
  public String getId(){return id;} public void setId(String id){this.id=id;}
  public String getFirstName(){return firstName;} public void setFirstName(String v){this.firstName=v;}
  public String getLastName(){return lastName;} public void setLastName(String v){this.lastName=v;}
  public int getPassengers(){return passengers;} public void setPassengers(int v){this.passengers=v;}
  public String getTravelClass(){return travelClass;} public void setTravelClass(String v){this.travelClass=v;}
  public String getPhone(){return phone;} public void setPhone(String v){this.phone=v;}
  public LocalTime getTime(){return time;} public void setTime(LocalTime v){this.time=v;}
  public LocalDate getDateOfDeparting(){return dateOfDeparting;} public void setDateOfDeparting(LocalDate v){this.dateOfDeparting=v;}
  public String getCustomerId(){return customerId;} public void setCustomerId(String v){this.customerId=v;}
  public String getPaymentId(){return paymentId;} public void setPaymentId(String v){this.paymentId=v;}
  public double getTotalAmount(){return totalAmount;} public void setTotalAmount(double v){this.totalAmount=v;}
}

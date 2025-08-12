package com.ps.spring.finalex.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;

@Document(collection = "customers")
public class Customer {
  @Id private String id;
  @NotBlank private String firstName;
  @NotBlank private String lastName;
  private String address;
  private String email;
  private String phone;

  public String getId(){return id;} public void setId(String id){this.id=id;}
  public String getFirstName(){return firstName;} public void setFirstName(String v){this.firstName=v;}
  public String getLastName(){return lastName;} public void setLastName(String v){this.lastName=v;}
  public String getAddress(){return address;} public void setAddress(String v){this.address=v;}
  public String getEmail(){return email;} public void setEmail(String v){this.email=v;}
  public String getPhone(){return phone;} public void setPhone(String v){this.phone=v;}
}

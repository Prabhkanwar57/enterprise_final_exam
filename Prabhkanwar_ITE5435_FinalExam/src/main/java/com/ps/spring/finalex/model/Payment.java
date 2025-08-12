package com.ps.spring.finalex.model;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "payments")
public class Payment {
  @Id private String id;
  private double amount;
  private String method;          // CARD / CASH
  private LocalDateTime paidAt;
  private String status;          // PAID

  public String getId(){return id;} public void setId(String id){this.id=id;}
  public double getAmount(){return amount;} public void setAmount(double v){this.amount=v;}
  public String getMethod(){return method;} public void setMethod(String v){this.method=v;}
  public LocalDateTime getPaidAt(){return paidAt;} public void setPaidAt(LocalDateTime v){this.paidAt=v;}
  public String getStatus(){return status;} public void setStatus(String v){this.status=v;}
}

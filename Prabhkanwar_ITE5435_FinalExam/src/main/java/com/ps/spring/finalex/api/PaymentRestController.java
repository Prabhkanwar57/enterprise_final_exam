package com.ps.spring.finalex.api;

import com.ps.spring.finalex.model.Payment;
import com.ps.spring.finalex.repo.PaymentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentRestController {

  private final PaymentRepository repo;

  public PaymentRestController(PaymentRepository repo) { this.repo = repo; }

  @GetMapping
  public List<Payment> all() { return repo.findAll(); }

  @PostMapping
  public Payment create(@RequestBody Payment p) { return repo.save(p); }

  @GetMapping("/{id}")
  public ResponseEntity<Payment> byId(@PathVariable String id) {
    return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    if (!repo.existsById(id)) return ResponseEntity.notFound().build();
    repo.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}

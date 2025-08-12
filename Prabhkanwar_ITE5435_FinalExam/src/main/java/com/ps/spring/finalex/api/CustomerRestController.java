package com.ps.spring.finalex.api;

import com.ps.spring.finalex.model.Customer;
import com.ps.spring.finalex.repo.CustomerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

  private final CustomerRepository repo;

  public CustomerRestController(CustomerRepository repo) { this.repo = repo; }

  @GetMapping
  public List<Customer> all() { return repo.findAll(); }

  @PostMapping
  public Customer create(@RequestBody Customer c) { return repo.save(c); }

  @GetMapping("/{id}")
  public ResponseEntity<Customer> byId(@PathVariable String id) {
    return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  public ResponseEntity<Customer> update(@PathVariable String id, @RequestBody Customer c) {
    return repo.findById(id).map(ex -> {
      ex.setFirstName(c.getFirstName());
      ex.setLastName(c.getLastName());
      ex.setAddress(c.getAddress());
      ex.setEmail(c.getEmail());
      ex.setPhone(c.getPhone());
      return ResponseEntity.ok(repo.save(ex));
    }).orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    if (!repo.existsById(id)) return ResponseEntity.notFound().build();
    repo.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}

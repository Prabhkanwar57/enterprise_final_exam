package com.ps.spring.finalex.api;

import com.ps.spring.finalex.model.Reservation;
import com.ps.spring.finalex.repo.ReservationRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/reservations")
public class ReservationRestController {
  private final ReservationRepository repo;
  public ReservationRestController(ReservationRepository repo){this.repo=repo;}

  @GetMapping public List<Reservation> all(){return repo.findAll();}
  @PostMapping public Reservation create(@Valid @RequestBody Reservation r){return repo.save(r);}
  @GetMapping("/{id}") public ResponseEntity<Reservation> byId(@PathVariable String id){
    return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }
  @DeleteMapping("/{id}") public ResponseEntity<Void> del(@PathVariable String id){
    if(!repo.existsById(id)) return ResponseEntity.notFound().build();
    repo.deleteById(id); return ResponseEntity.noContent().build();
  }
}

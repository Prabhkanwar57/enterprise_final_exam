package com.ps.spring.finalex.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ps.spring.finalex.dto.ReservationRequest;
import com.ps.spring.finalex.model.Reservation;
import com.ps.spring.finalex.repo.ReservationRepository;
import com.ps.spring.finalex.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReservationMvcController {

  private final ReservationService reservationService;
  private final ReservationRepository reservationRepo;
  private final ObjectMapper mapper;

  public ReservationMvcController(ReservationService reservationService,
                                  ReservationRepository reservationRepo,
                                  ObjectMapper mapper) {
    this.reservationService = reservationService;
    this.reservationRepo = reservationRepo;
    this.mapper = mapper;
  }

 
  @ModelAttribute("req")
  public ReservationRequest initReq() { return new ReservationRequest(); }


  @GetMapping({"/", "/reservations/new"})
  public String showForm() { return "reservation_form"; }


  @PostMapping("/reservations")
  public String create(@Valid @ModelAttribute("req") ReservationRequest req,
                       BindingResult errors,
                       Model model) {
    try {
      if (errors.hasErrors()) return "reservation_form";
      Reservation saved = reservationService.createFromForm(req);
      model.addAttribute("reservation", saved);
      model.addAttribute("json", mapper.writeValueAsString(req));
      return "reservation_success";
    } catch (Exception ex) {
      model.addAttribute("error", ex.getMessage());
      return "reservation_form";
    }
  }


  @GetMapping("/reservations")
  public String list(Model model) {
    model.addAttribute("reservations", reservationRepo.findAll());
    return "reservations_list";
  }

  @GetMapping("/reservations/{id}/edit")
  public String editForm(@PathVariable String id, Model model) {
    Reservation r = reservationRepo.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Reservation not found: " + id));

    ReservationRequest req = new ReservationRequest();
    req.setFirstName(r.getFirstName());
    req.setLastName(r.getLastName());
    req.setPassengers(r.getPassengers());
    req.setTravelClass(r.getTravelClass());
    req.setPhone(r.getPhone());
    req.setTime(r.getTime());
    req.setDateOfDeparting(r.getDateOfDeparting());

    model.addAttribute("req", req);
    model.addAttribute("reservationId", r.getId());
    model.addAttribute("editing", true);
    return "reservation_form";
  }


  @PostMapping("/reservations/{id}/update")
  public String update(@PathVariable String id,
                       @Valid @ModelAttribute("req") ReservationRequest req,
                       BindingResult errors,
                       Model model) {
    try {
      if (errors.hasErrors()) {
        model.addAttribute("editing", true);
        model.addAttribute("reservationId", id);
        return "reservation_form";
      }
      Reservation updated = reservationService.updateFromForm(id, req);
      model.addAttribute("reservation", updated);
      model.addAttribute("json", mapper.writeValueAsString(req));
      return "reservation_success";
    } catch (Exception ex) {
      model.addAttribute("editing", true);
      model.addAttribute("reservationId", id);
      model.addAttribute("error", ex.getMessage());
      return "reservation_form";
    }
  }


  @PostMapping("/reservations/{id}/delete")
  public String delete(@PathVariable String id) {
    reservationService.deleteCascade(id);
    return "redirect:/reservations";
  }
}

package com.hyperlocal.marketplace.controller;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.hyperlocal.marketplace.model.Appointment;
import com.hyperlocal.marketplace.model.ServiceProvider;
import com.hyperlocal.marketplace.model.User;
import com.hyperlocal.marketplace.repository.AppointmentRepository;
import com.hyperlocal.marketplace.repository.ServiceProviderRepository;

@Controller
public class BookingController {

    private final ServiceProviderRepository providerRepo;
    private final AppointmentRepository appointmentRepo;

    public BookingController(ServiceProviderRepository p, AppointmentRepository a) {
        this.providerRepo = p;
        this.appointmentRepo = a;
    }

    @GetMapping("/book/{id}")
    public String bookingForm(@PathVariable Long id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) {
            return "redirect:/login";
        }

        ServiceProvider provider = providerRepo.findById(id).orElseThrow();
        List<Appointment> appointments = appointmentRepo.findByProviderId(id);

        model.addAttribute("provider", provider);
        model.addAttribute("appointments", appointments);
        model.addAttribute("loggedUser", user);

        return "book";
    }

    @PostMapping("/book/{id}")
    public String saveBooking(
            @PathVariable Long id,
            @RequestParam String clientName,
            @RequestParam String clientEmail,
            @RequestParam String freelancerEmail,
            @RequestParam String homeAddress,
            @RequestParam String date,
            @RequestParam String start,
            @RequestParam String end,
            Model model
    ) {
        LocalDate d = LocalDate.parse(date);
        LocalTime s = LocalTime.parse(start);
        LocalTime e = LocalTime.parse(end);

        boolean conflict =
                appointmentRepo.existsByProviderIdAndDateAndStartTimeLessThanAndEndTimeGreaterThan(
                        id, d, e, s);

        if (conflict) {
            model.addAttribute("error", "Time slot already booked!");
            return "error-booking";
        }

        Appointment ap = new Appointment();
        ap.setClientName(clientName);
        ap.setClientEmail(clientEmail);
        ap.setFreelancerEmail(freelancerEmail);
        ap.setHomeAddress(homeAddress);
        ap.setDate(d);
        ap.setStartTime(s);
        ap.setEndTime(e);
        ap.setProvider(providerRepo.findById(id).orElseThrow());

        appointmentRepo.save(ap);

        return "redirect:/success";
    }

    @GetMapping("/success")
    public String successPage() {
        return "success";
    }
}
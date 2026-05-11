package com.hyperlocal.marketplace.controller;

import com.hyperlocal.marketplace.model.User;
import com.hyperlocal.marketplace.model.Appointment;
import com.hyperlocal.marketplace.repository.AppointmentRepository;
import com.hyperlocal.marketplace.repository.ServiceProviderRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/freelancer")
public class FreelancerController {

    @Autowired
    private AppointmentRepository appointmentRepository; // ✅ use Appointment not Booking

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) {
            return "redirect:/login";
        }

        // ✅ Only this freelancer's appointments
        List<Appointment> myAppointments = appointmentRepository.findByFreelancerEmail(user.getEmail());

        long total = myAppointments.size();
        long pending = myAppointments.stream()
                .filter(a -> a.getStatus() == null || "PENDING".equalsIgnoreCase(a.getStatus()))
                .count();
        long completed = myAppointments.stream()
                .filter(a -> "ACCEPTED".equalsIgnoreCase(a.getStatus()))
                .count();

        model.addAttribute("user", user);
        model.addAttribute("totalBookings", total);
        model.addAttribute("pendingRequests", pending);
        model.addAttribute("completedJobs", completed);

        return "freelancer-dashboard";
    }

    @GetMapping("/bookings")
    public String viewBookings(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) {
            return "redirect:/login";
        }

        // ✅ Only this freelancer's appointments
        List<Appointment> myAppointments = appointmentRepository.findByFreelancerEmail(user.getEmail());
        model.addAttribute("bookings", myAppointments);

        return "freelancer-bookings";
    }

    @GetMapping("/accept/{id}")
    public String acceptBooking(@PathVariable Long id) {
        Appointment ap = appointmentRepository.findById(id).orElse(null);
        if (ap != null) {
            ap.setStatus("ACCEPTED");
            appointmentRepository.save(ap);
        }
        return "redirect:/freelancer/bookings";
    }

    @GetMapping("/reject/{id}")
    public String rejectBooking(@PathVariable Long id) {
        Appointment ap = appointmentRepository.findById(id).orElse(null);
        if (ap != null) {
            ap.setStatus("REJECTED");
            appointmentRepository.save(ap);
        }
        return "redirect:/freelancer/bookings";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "freelancer-profile";
    }
}
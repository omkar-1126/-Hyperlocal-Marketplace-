package com.hyperlocal.marketplace.controller;

import com.hyperlocal.marketplace.model.Booking;
import com.hyperlocal.marketplace.model.User;
import com.hyperlocal.marketplace.repository.AppointmentRepository;
import com.hyperlocal.marketplace.repository.BookingRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @GetMapping("/dashboard")
    public String clientDashboard() {
        return "client-dashboard";
    }

    @GetMapping("/bookings")
    public String myBookings(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";

        model.addAttribute("bookings", appointmentRepository.findByClientEmail(user.getEmail()));
        return "client-bookings";
    }

    @GetMapping("/book")
    public String showBookingPage() {
        return "client-book-service";
    }

    @PostMapping("/book")
    public String bookService(@RequestParam String clientName,
                              @RequestParam String serviceName,
                              @RequestParam String freelancerEmail, // ← NEW
                              @RequestParam String date,
                              @RequestParam String timeSlot) {

        Booking booking = new Booking();
        booking.setClientName(clientName);
        booking.setServiceName(serviceName);
        booking.setFreelancerEmail(freelancerEmail); // ← NEW
        booking.setDate(date);
        booking.setTimeSlot(timeSlot);
        booking.setStatus("PENDING");

        bookingRepository.save(booking);

        return "redirect:/success";
    }
}
package com.hyperlocal.marketplace.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.hyperlocal.marketplace.model.ServiceProvider;
import com.hyperlocal.marketplace.model.User;
import com.hyperlocal.marketplace.repository.ServiceProviderRepository;

@Controller
public class ServiceController {

    @Autowired
    private ServiceProviderRepository repo;

    @GetMapping("/post-service")
    public String postServicePage(HttpSession session) {
        if (session.getAttribute("loggedUser") == null) {
            return "redirect:/register-freelancer";
        }
        return "post-service";
    }

    @PostMapping("/save-service")
    public String saveService(ServiceProvider provider, HttpSession session) {
        // ✅ Auto-save freelancer's email from session
        User user = (User) session.getAttribute("loggedUser");
        if (user != null) {
            provider.setEmail(user.getEmail());
        }
        repo.save(provider);
        return "redirect:/freelancer/dashboard";
    }

    @GetMapping("/view-services")
    public String viewServices(@RequestParam(required = false) String type,
                               HttpSession session, Model model) {
        List<ServiceProvider> list;
        if (type == null || type.equalsIgnoreCase("all")) {
            list = repo.findAll();
            model.addAttribute("pageTitle", "All Freelancers");
        } else {
            list = repo.findByServiceType(type);
            model.addAttribute("pageTitle", type + " Freelancers");
        }
        model.addAttribute("providers", list);
        model.addAttribute("type", type);
        model.addAttribute("loggedUser", session.getAttribute("loggedUser"));
        return "view-services";
    }
}
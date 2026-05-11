package com.hyperlocal.marketplace.controller;

import com.hyperlocal.marketplace.model.User;
import com.hyperlocal.marketplace.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // LOGIN PAGE
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    // LOGIN LOGIC
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        User user = userRepository.findByEmail(email);

        if (user == null || !user.getPassword().equals(password)) {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }

        session.setAttribute("loggedUser", user);

        if ("CLIENT".equalsIgnoreCase(user.getRole())) {
            return "redirect:/client/dashboard";
        } else if ("FREELANCER".equalsIgnoreCase(user.getRole())) {
            return "redirect:/freelancer/dashboard";
        }

        return "redirect:/login";
    }

    // ================= CLIENT =================

    @GetMapping("/register-client")
    public String showClientRegister() {
        return "register-client";
    }

    @PostMapping("/register-client")
    public String registerClient(@RequestParam String email,
                                 @RequestParam String password,
                                 HttpSession session,
                                 Model model) {

        User existingUser = userRepository.findByEmail(email);

        if (existingUser != null) {
            model.addAttribute("error", "User already exists!");
            return "register-client";
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRole("CLIENT");

        userRepository.save(user);
        session.setAttribute("loggedUser", user);

        return "redirect:/client/dashboard";
    }

    // ================= FREELANCER =================

    @GetMapping("/register-freelancer")
    public String showFreelancerRegister() {
        return "register-freelancer";
    }

    @PostMapping("/register-freelancer")
    public String registerFreelancer(@RequestParam String email,
                                     @RequestParam String password,
                                     HttpSession session,
                                     Model model) {

        User existingUser = userRepository.findByEmail(email);

        if (existingUser != null) {
            model.addAttribute("error", "User already exists!");
            return "register-freelancer";
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRole("FREELANCER");

        userRepository.save(user);
        session.setAttribute("loggedUser", user);

        // After credentials saved, go fill service provider details
        return "redirect:/post-service";
    }
}
package com.hyperlocal.marketplace.controller;

import com.hyperlocal.marketplace.model.Review;
import com.hyperlocal.marketplace.model.User;
import com.hyperlocal.marketplace.repository.ReviewRepository;
import com.hyperlocal.marketplace.repository.ServiceProviderRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ServiceProviderRepository providerRepository;

    // Show all reviews for a provider + add-review form
    @GetMapping("/reviews/{providerId}")
    public String reviewsPage(@PathVariable Long providerId, HttpSession session, Model model) {
        List<Review> reviews = reviewRepository.findByProviderId(providerId);
        model.addAttribute("provider", providerRepository.findById(providerId).orElseThrow());
        model.addAttribute("reviews", reviews);
        model.addAttribute("loggedUser", session.getAttribute("loggedUser"));
        return "reviews";
    }

    // Create a new review
    @PostMapping("/reviews/{providerId}")
    public String addReview(@PathVariable Long providerId,
                            @RequestParam String comment,
                            @RequestParam int rating,
                            HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";

        Review review = new Review();
        review.setClientEmail(user.getEmail());
        review.setClientName(user.getName() != null ? user.getName() : user.getEmail());
        review.setComment(comment);
        review.setRating(rating);
        review.setProvider(providerRepository.findById(providerId).orElseThrow());
        reviewRepository.save(review);

        return "redirect:/reviews/" + providerId;
    }

    // Show edit form for a review
    @GetMapping("/reviews/{id}/edit")
    public String editReviewForm(@PathVariable Long id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";

        Review review = reviewRepository.findById(id).orElse(null);
        if (review == null || !review.getClientEmail().equals(user.getEmail())) {
            return "redirect:/view-services";
        }

        model.addAttribute("review", review);
        model.addAttribute("loggedUser", user);
        return "edit-review";
    }

    // Save edited review (PUT via hidden _method or direct POST)
    @PostMapping("/reviews/{id}/edit")
    public String updateReview(@PathVariable Long id,
                               @RequestParam String comment,
                               @RequestParam int rating,
                               HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";

        Review review = reviewRepository.findById(id).orElse(null);
        if (review == null || !review.getClientEmail().equals(user.getEmail())) {
            return "redirect:/view-services";
        }

        review.setComment(comment);
        review.setRating(rating);
        reviewRepository.save(review);

        return "redirect:/reviews/" + review.getProvider().getId();
    }

    // Delete a review (owner only)
    @GetMapping("/reviews/{id}/delete")
    public String deleteReview(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";

        Review review = reviewRepository.findById(id).orElse(null);
        if (review == null || !review.getClientEmail().equals(user.getEmail())) {
            return "redirect:/view-services";
        }

        Long providerId = review.getProvider().getId();
        reviewRepository.delete(review);
        return "redirect:/reviews/" + providerId;
    }
}

package com.loginForm.springboot.web;

import com.loginForm.springboot.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping
    public String showForm(Model model) {
        // Add any model attributes here if needed
        return "feedback"; // Thymeleaf template name
    }

    @PostMapping
    @ResponseBody
    public String submitRating(@RequestParam int rating) {
        ratingService.saveRating(rating);
        return "Thank you for your feedback!";
    }
}

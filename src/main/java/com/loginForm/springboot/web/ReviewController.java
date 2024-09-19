package com.loginForm.springboot.web;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.loginForm.springboot.service.*;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/submitReview")
    public String submitReview(
            @RequestParam("name") String name,
            @RequestParam("review") String review,
            @RequestParam("rating") int rating,
            Model model) {
        
        // Save the review data into the database using the service
        reviewService.saveReview(name, review, rating);
        
        // Add success message to the model for the view
        model.addAttribute("message", "Review submitted successfully!");
        
        // Return the name of the HTML page to be displayed
        return "index";  // This should correspond to your reviewSuccess.html file
    }
}


package com.loginForm.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.loginForm.springboot.repository.ReviewRepository;
import com.loginForm.springboot.model.Review;

@Service
public class ReviewService {

	 @Autowired
	    private ReviewRepository reviewRepository;

	    public void saveReview(String name, String reviewContent, int rating) {
	        // Create a new Review entity and set its properties
	        Review review = new Review();
	        review.setName(name);
	        review.setReview(reviewContent);
	        review.setRating(rating);

	        // Save the Review object to the database
	        reviewRepository.save(review);
	    }	
}


package com.loginForm.springboot.service;

import com.loginForm.springboot.model.Rating;
import com.loginForm.springboot.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public void saveRating(int ratingValue) {
        Rating rating = new Rating();
        rating.setRating(ratingValue);
        ratingRepository.save(rating);
    }
}

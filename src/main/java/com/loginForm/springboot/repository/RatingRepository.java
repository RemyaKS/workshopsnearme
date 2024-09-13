package com.loginForm.springboot.repository;

import com.loginForm.springboot.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}

	package com.loginForm.springboot.repository;

	import org.springframework.data.jpa.repository.JpaRepository;
	import com.loginForm.springboot.model.Review;

	public interface ReviewRepository extends JpaRepository<Review, Long> {
	}

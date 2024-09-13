package com.loginForm.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.loginForm.springboot.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}

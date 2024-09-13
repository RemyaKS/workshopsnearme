package com.loginForm.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.loginForm.springboot.model.Booking;
import com.loginForm.springboot.repository.BookingRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Transactional;@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Transactional
    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Transactional
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}

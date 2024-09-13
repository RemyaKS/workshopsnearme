package com.loginForm.springboot.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.loginForm.springboot.model.Booking;
import com.loginForm.springboot.service.BookingService;
import com.loginForm.springboot.service.EmailService;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/book")
    public ResponseEntity<Map<String, Object>> bookService(@RequestParam String name, @RequestParam String email, 
                                                           @RequestParam String phone, @RequestParam String altPhone, // New parameter
                                                           @RequestParam String date, @RequestParam String service, 
                                                           @RequestParam String location, @RequestParam String request, 
                                                           @RequestParam String workshop, @RequestParam String vehicleType, 
                                                           @RequestParam String timeSlot) {
        Map<String, Object> response = new HashMap<>();
        try {
            Booking booking = new Booking();
            booking.setName(name);
            booking.setEmail(email);
            booking.setPhone(phone);
            booking.setAltPhone(altPhone); // Set the alternative phone number
            booking.setDate(date);
            booking.setService(service);
            booking.setLocation(location);
            booking.setRequest(request);
            booking.setWorkshop(workshop);
            booking.setVehicleType(vehicleType);
            booking.setTimeSlot(timeSlot);
            
            

            Booking savedBooking = bookingService.saveBooking(booking);

            // Send confirmation email
            emailService.sendConfirmationEmail(email, name, date, service,timeSlot,workshop,location);

            response.put("success", true);
            response.put("message", "Booking successful!");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Booking failed. Please try again.");
            e.printStackTrace();
        }

        return ResponseEntity.ok(response);
    }
}

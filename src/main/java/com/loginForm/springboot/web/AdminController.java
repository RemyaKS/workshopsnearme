package com.loginForm.springboot.web;

import com.loginForm.springboot.model.Username;

import com.loginForm.springboot.model.Booking;
import com.loginForm.springboot.repository.BookingRepository;
import com.loginForm.springboot.repository.UsernameRepository;
import com.loginForm.springboot.service.EmailService;

import ch.qos.logback.core.status.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AdminController {

	@Autowired
    private EmailService emailService;
	
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UsernameRepository usernameRepository;

    @GetMapping("/adminlogin")
    public String showAdminLogin() {
        return "adminlogin"; // Loads admin-login.html
    }

    @PostMapping("/adminlogin")
    public ModelAndView adminLogin(@RequestParam("username") String username,
                                   @RequestParam("password") String password,
                                   HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        if ("admin".equals(username) && "admin".equals(password)) {
            // Store admin username in session
            session.setAttribute("username", username);
            // Redirect to admin-dashboard for specific credentials
            modelAndView.setViewName("redirect:/admin");
        } else {
        	Username workshop = usernameRepository.findByUsername(username);
            if (workshop != null && password.equals(workshop.getPassword())) {
                // Store workshop username in session
                session.setAttribute("username", username);
                // Redirect to workshopowners for valid workshop credentials
                modelAndView.setViewName("redirect:/workshopowners");
            } else {
                // Return to login page with error message
                modelAndView.setViewName("adminlogin");
                modelAndView.addObject("error", "Invalid username or password.");
            }
        }
        return modelAndView;
    }

    @GetMapping("/admin")
    public String viewBookings(@RequestParam(name = "id", required = false) Long id, Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/adminlogin"; // Redirect to login if session is not present
        }

        // Fetch all bookings
        List<Booking> bookings = bookingRepository.findAll();
        model.addAttribute("bookings", bookings);
        model.addAttribute("username", username); // Add username to the model

        // If an ID is provided, fetch and add specific booking details to the model
        if (id != null) {
            Booking booking = bookingRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid booking Id:" + id));
            model.addAttribute("selectedBooking", booking);
        }

        return "admin-dashboard"; // Loads admin-dashboard.html
    }

    

    @PostMapping("/deleteBooking")
    public String deleteBooking(@RequestParam("id") Long id, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            bookingRepository.deleteById(id);
        }
        return "redirect:/admin"; // Redirect back to the admin dashboard after deletion
    }
    @GetMapping("/editBooking/{id}")
    public String editBooking(@PathVariable("id") Long id, Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/adminlogin"; // Redirect to login if session is not present
        }

        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid booking Id:" + id));
        model.addAttribute("booking", booking);
        return "edit-booking"; // Loads edit-booking.html
    }

    @PostMapping("/updateBooking/{id}")
    public String updateBooking(@PathVariable("id") Long id, @RequestParam("name") String name,
                         
                                @RequestParam("date") String date, @RequestParam("service") String service,
                                @RequestParam("workshop") String workshop, @RequestParam("location") String location,
                                @RequestParam("request") String request, @RequestParam("timeSlot") String timeSlot, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/adminlogin"; // Redirect to login if session is not present
        }

        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid booking Id:" + id));
        booking.setName(name);
      
        booking.setDate(date);
        booking.setService(service);
        booking.setWorkshop(workshop);
        booking.setLocation(location);
        booking.setRequest(request);
        booking.setTimeSlot(timeSlot);

        bookingRepository.save(booking);

        return "redirect:/admin"; // Redirect back to the admin dashboard after update
    }
    @GetMapping("/workshopowners")
    public String viewWorkshopOwners(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/adminlogin"; // Redirect to login if session is not present
        }

        // Fetch all bookings and filter by the logged-in username
        List<Booking> allBookings = bookingRepository.findAll();
        List<Booking> userBookings = allBookings.stream()
                                                .filter(booking -> username.equals(booking.getWorkshop()))
                                                .toList();

        model.addAttribute("username", username); // Add username to the model
        model.addAttribute("bookings", userBookings); // Add filtered bookings to the model

        // Add a message if no bookings are found
        if (userBookings.isEmpty()) {
            model.addAttribute("message", "No bookings yet.");
        }

        return "workshopowners"; // Loads workshopowners.html
    }
    @PostMapping("/updateStatus")
    public String updateStatus(@RequestParam Long id, @RequestParam("status") Booking.Status status, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/adminlogin"; // Redirect to login if session is not present
        }
        
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid booking ID: " + id));
        booking.setStatus(status);
        bookingRepository.save(booking);
        // Get the email address of the booking
        String email = booking.getEmail();
        
        booking.setStatus(status);
        bookingRepository.save(booking);

        // Send email based on status
        if (status == Booking.Status.CONFIRMED) {
            emailService.sendConfirmationEmails(email);
        } else if (status == Booking.Status.DECLINED) {
            emailService.sendDeclineEmail(email);
        }

        
        return "redirect:/workshopowners";
    }
    


    
    
}

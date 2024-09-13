package com.loginForm.springboot.web;

import com.loginForm.springboot.model.CallbackRequest;
import com.loginForm.springboot.repository.CallbackRequestRepository;
import com.loginForm.springboot.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CallbackRequestController {

    @Autowired
    private CallbackRequestRepository callbackRequestRepository;

    @Autowired
    private EmailService emailService;

    @GetMapping("/form")
    public String showForm() {
        return "form";
    }

    @PostMapping("/submitForm")
    public String submitForm(CallbackRequest callbackRequest) {
        callbackRequestRepository.save(callbackRequest);
        emailService.sendCallbackRequestEmail(callbackRequest); // Send email after saving the request
        return "redirect:/";
    }

    @GetMapping("/showRequests")
    public String showAllRequests(Model model) {
        List<CallbackRequest> requests = callbackRequestRepository.findAll();
        model.addAttribute("requests", requests);
        return "requests-for-call";
    }

    @PostMapping("/deleteRequest/{id}")
    public String deleteRequest(@PathVariable Long id) {
        callbackRequestRepository.deleteById(id);
        return "redirect:/showRequests";
    }
}
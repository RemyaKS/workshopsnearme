package com.loginForm.springboot.web;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class MainController {

	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/")
	public String home() {
		return "index";
	}
	@GetMapping("/check")
	public String check() {
		return "check";
	}
	
	 @GetMapping("/hello")
	    public String hello() {
	        // Print a message to the console
	        System.out.println("Hello from the Spring Boot Controller!");

	        // Return a simple string as the response
	        return "hello";
	    }
 
	
}
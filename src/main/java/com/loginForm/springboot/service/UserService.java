   package com.loginForm.springboot.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.loginForm.springboot.model.User;
import com.loginForm.springboot.web.dto.UserRegistrationDto;
import com.loginForm.springboot.model.User;
import com.loginForm.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

public interface UserService extends UserDetailsService{

	//save user's registered data
	User save(UserRegistrationDto registrationDto);

	
}

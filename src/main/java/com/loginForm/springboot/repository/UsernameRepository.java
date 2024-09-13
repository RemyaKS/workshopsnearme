package com.loginForm.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loginForm.springboot.model.Username;


public interface UsernameRepository extends JpaRepository<Username, Long> {
	Username findByUsername(String username);
	Username findById(String id);
	Username findByContactNumber(String contactNumber);
	
}
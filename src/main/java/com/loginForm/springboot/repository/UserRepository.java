package com.loginForm.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loginForm.springboot.model.User;
import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	//This is method ,Retrieve the information according to the user email from the database
	User findByEmail(String email);
	 User findByPhone(String phone);
	
	    List<User> findAll();  // Fetch all users
}

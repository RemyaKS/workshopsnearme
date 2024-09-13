package com.loginForm.springboot.repository;



import com.loginForm.springboot.model.CallbackRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallbackRequestRepository extends JpaRepository<CallbackRequest, Long> {
}

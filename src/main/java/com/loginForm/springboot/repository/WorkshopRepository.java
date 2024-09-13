package com.loginForm.springboot.repository;



import com.loginForm.springboot.model.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkshopRepository extends JpaRepository<Workshop, Long> {
    Workshop findByUsername(String username);
}

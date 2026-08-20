package com.johanwork.job.controller;

import com.johanwork.job.domain.UserRole;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public ResponseEntity<String> homeController(){
        return ResponseEntity.ok("Service for application management " + UserRole.ROLE_EMPLOYER);
    }
}

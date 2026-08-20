package com.johanwork.job.controller;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public ResponseEntity<String> getHome(){
        return ResponseEntity.ok("Service for managing candidate resumes, including resume builder");
    }

}

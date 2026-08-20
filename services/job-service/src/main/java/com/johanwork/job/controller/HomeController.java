package com.johanwork.job.controller;

import com.johanwork.job.domain.UserRole;
import com.johanwork.job.dto.response.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public ResponseEntity<GenericResponse<Void>> home(){
        return ResponseEntity.ok(new GenericResponse(
                null,
                "Service for managing job posting, search and filtering -- " + UserRole.ROLE_EMPLOYER
        ));
    }

}

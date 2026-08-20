package com.johanwork.job.controller;

import com.johanwork.job.domain.ApplicationStatus;
import com.johanwork.job.dto.ApplicationRequest;
import com.johanwork.job.dto.CompanyApplicationFilterRequest;
import com.johanwork.job.dto.response.ApplicationResponse;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.service.IApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApplicationController {

    private final IApplicationService service;

    @PostMapping
    public ResponseEntity<GenericResponse<ApplicationResponse>> createApplication(
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid ApplicationRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createApplication(candidateId, req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<ApplicationResponse>> getApplicationById(
            @PathVariable Long id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getApplicationById(id));
    }

    @GetMapping("/my")
    public ResponseEntity<GenericResponse<List<ApplicationResponse>>> getMyApplications(
            @RequestHeader("X-User-Id") Long candidateId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getMyApplication(candidateId));
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<GenericResponse<List<ApplicationResponse>>> getApplicationForJob(
            @PathVariable Long jobId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getApplicationForJob(jobId));
    }

    @GetMapping("/company")
    public ResponseEntity<GenericResponse<List<ApplicationResponse>>> getApplicationsForCompany(
            @RequestHeader("X-User-Id") Long userId,
            @ModelAttribute CompanyApplicationFilterRequest req
            ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getApplicationForCompany(userId, req));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GenericResponse<ApplicationResponse>> updateStatus(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long employerId,
            @RequestParam ApplicationStatus status
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.updateStatus(id, employerId, status));
    }

    @PatchMapping("/{id}/withdraw")
    public ResponseEntity<GenericResponse<ApplicationResponse>> withdrawn(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestParam String reason
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.withdrawnApplication(id, candidateId, reason));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Void>> deleteApplication(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long candidateId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.deleteApplication(id, candidateId));
    }
}

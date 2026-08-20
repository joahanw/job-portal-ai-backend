package com.johanwork.job.controller;

import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.PersonalInfoResponse;
import com.johanwork.job.dto.response.ResumeResponse;
import com.johanwork.job.dto.CreateResumeRequest;
import com.johanwork.job.model.Resume;
import com.johanwork.job.service.IResumeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resumes")
public class ResumeController {

    private final IResumeService service;

    @PostMapping
    public ResponseEntity<GenericResponse<ResumeResponse>> createResume(
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid CreateResumeRequest req){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createResume(candidateId, req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<ResumeResponse>> getResumeById(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long candidateId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getResumeById(id, candidateId));
    }

    @GetMapping("/my")
    public ResponseEntity<GenericResponse<List<ResumeResponse>>> getMyResume(
            @RequestHeader("X-User-Id") Long candidateId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getMyResumes(candidateId));
    }

    @PutMapping("/{id}/personal-info")
    public ResponseEntity<GenericResponse<ResumeResponse>> updatePersonalInfo(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid PersonalInfoResponse req
            ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.updatePersonalInfo(id, candidateId, req));
    }

    @PatchMapping("/{id}/summary")
    public ResponseEntity<GenericResponse<ResumeResponse>> updateSummary(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestParam String summary
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.updateSummary(id, candidateId, summary));
    }

    @PatchMapping("/{id}/set-default")
    public ResponseEntity<GenericResponse<ResumeResponse>> setDefaultResume(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long candidateId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.setDefaultResume(id, candidateId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Void>> deleteResume(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long candidateId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.deleteResume(id, candidateId));
    }

}

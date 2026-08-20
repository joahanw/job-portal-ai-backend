package com.johanwork.job.controller;

import com.johanwork.job.dto.response.EducationResponse;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.AddEducationRequest;
import com.johanwork.job.service.IEducationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resumes/{resumeId}/educations")
public class EducationController {

    private final IEducationService service;

    @PostMapping
    public ResponseEntity<GenericResponse<EducationResponse>> addEducation(
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid AddEducationRequest req
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.addEducation(resumeId, candidateId, req));
    }

    @GetMapping
    public ResponseEntity<GenericResponse<List<EducationResponse>>> getEducations(
            @PathVariable Long resumeId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getEducation(resumeId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<EducationResponse>> updateEducation(
            @PathVariable Long id,
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid AddEducationRequest req
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.updateEducation(id, resumeId, candidateId, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Void>> deleteEducation(
            @PathVariable Long id,
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.deleteEducation(id, resumeId, candidateId));
    }

}

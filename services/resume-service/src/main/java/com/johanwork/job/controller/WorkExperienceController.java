package com.johanwork.job.controller;

import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.WorkExperienceResponse;
import com.johanwork.job.dto.AddWorkExperienceRequest;
import com.johanwork.job.service.IWorkExperienceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resumes/{resumeId}/work-experiences")
@RequiredArgsConstructor
public class WorkExperienceController {

    private final IWorkExperienceService service;

    @PostMapping
    public ResponseEntity<GenericResponse<WorkExperienceResponse>> addWorkExperience(
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid AddWorkExperienceRequest req
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.addWorkExperience(resumeId, candidateId, req));
    }

    @GetMapping
    public ResponseEntity<GenericResponse<List<WorkExperienceResponse>>> getWorkExperiences(
            @PathVariable Long resumeId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getWorkExperiences(resumeId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<WorkExperienceResponse>> updateWorkExperience(
            @PathVariable Long id,
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid AddWorkExperienceRequest req
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.updateWorkExperience(id, resumeId, candidateId, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Void>> deleteWorkExperience(
            @PathVariable Long id,
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.deleteWorkExperience(id, resumeId, candidateId));
    }

}

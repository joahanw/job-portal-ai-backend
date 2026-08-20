package com.johanwork.job.controller;

import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.ResumeSkillResponse;
import com.johanwork.job.dto.AddResumeSkillRequest;
import com.johanwork.job.model.ResumeSkill;
import com.johanwork.job.service.IResumeSkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resumes/{resumeId}/resume-skills")
public class ResumeSkillController {

    private final IResumeSkillService service;

    @PostMapping
    public ResponseEntity<GenericResponse<ResumeSkillResponse>> addSkill(
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid AddResumeSkillRequest req
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.addSkill(resumeId, candidateId, req));
    }

    @GetMapping
    public ResponseEntity<GenericResponse<List<ResumeSkillResponse>>> getSkills(
            @PathVariable Long resumeId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getSkills(resumeId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<ResumeSkillResponse>> updateSkills(
            @PathVariable Long id,
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid AddResumeSkillRequest req
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.updateSkill(id, resumeId, candidateId, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Void>> deleteSkill(
            @PathVariable Long id,
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.deleteSkill(id, resumeId, candidateId));
    }
}

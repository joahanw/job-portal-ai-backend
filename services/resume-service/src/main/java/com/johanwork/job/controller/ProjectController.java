package com.johanwork.job.controller;

import com.johanwork.job.dto.AddProjectRequest;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.ProjectResponse;
import com.johanwork.job.model.Project;
import com.johanwork.job.service.IProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resumes/{resumeId}/projects")
public class ProjectController {

    private final IProjectService service;

    @PostMapping
    public ResponseEntity<GenericResponse<ProjectResponse>> createProject(
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid AddProjectRequest req
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.addProject(resumeId,candidateId, req));
    }

    @GetMapping
    public ResponseEntity<GenericResponse<List<ProjectResponse>>> getAllProjects(
            @PathVariable Long resumeId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getAllProjects(resumeId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<ProjectResponse>> updateProjects(
            @PathVariable Long resumeId,
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid AddProjectRequest req
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.updateProject(id, resumeId, candidateId, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Void>> deleteProject(
            @PathVariable Long resumeId,
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long candidateId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.deleteProject(id, resumeId, candidateId));
    }

}

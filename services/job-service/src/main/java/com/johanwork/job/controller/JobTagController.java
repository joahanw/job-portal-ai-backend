package com.johanwork.job.controller;

import com.johanwork.job.dto.request.JobTagRequest;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.JobTagResponse;
import com.johanwork.job.service.impl.JobTagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job-tags")
@RequiredArgsConstructor
public class JobTagController {

    private final JobTagService service;

    @PostMapping
    public ResponseEntity<GenericResponse<JobTagResponse>> createTag(
            @RequestBody @Valid JobTagRequest req) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.createTag(req));
    }

    @GetMapping
    public ResponseEntity<GenericResponse<List<JobTagResponse>>> getAllTags() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getAllTags());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<JobTagResponse>> getJobTagById(
            @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getTagById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<JobTagResponse>> updateTag(
            @PathVariable Long id,
            @RequestBody JobTagRequest req){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.updateTag(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Void>> deleteTag(
            @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.deleteTag(id));
    }

}

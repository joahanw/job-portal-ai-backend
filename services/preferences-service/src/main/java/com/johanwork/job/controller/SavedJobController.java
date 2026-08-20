package com.johanwork.job.controller;

import com.johanwork.job.dto.SavedJobRequest;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.SavedJobResponse;
import com.johanwork.job.service.ISavedJobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/preferences/saved-jobs")
public class SavedJobController {

    private final ISavedJobService service;

    @PostMapping
    public ResponseEntity<GenericResponse<SavedJobResponse>> saveJob(
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid SavedJobRequest req
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.saveJob(candidateId,req));
    }

    @GetMapping
    public ResponseEntity<GenericResponse<List<SavedJobResponse>>> getMySavedJobs(
            @RequestHeader("X-User-Id") Long candidateId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getSavedJobs(candidateId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Void>> unsaveJob(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long candidateId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.unsaveJob(id, candidateId));
    }

}

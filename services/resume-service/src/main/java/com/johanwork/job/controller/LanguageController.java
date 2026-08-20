package com.johanwork.job.controller;

import com.johanwork.job.dto.AddLanguageRequest;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.LanguageResponse;
import com.johanwork.job.service.ILanguageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resumes/{resumeId}/languages")
public class LanguageController {

    private final ILanguageService service;

    @PostMapping
    public ResponseEntity<GenericResponse<LanguageResponse>> addLanguage(
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody AddLanguageRequest req
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.addLanguage(resumeId, candidateId, req));
    }

    @GetMapping
    public ResponseEntity<GenericResponse<List<LanguageResponse>>> getLanguages(
            @PathVariable Long resumeId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getLanguages(resumeId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<LanguageResponse>> updateLanguage(
            @PathVariable Long id,
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid AddLanguageRequest req
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.updateLanguage(id, resumeId, candidateId, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Void>> deleteLanguage(
            @PathVariable Long id,
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.deleteLanguage(id, resumeId, candidateId));
    }

}

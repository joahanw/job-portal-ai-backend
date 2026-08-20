package com.johanwork.job.controller;

import com.johanwork.job.dto.JobCategoryRequest;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.JobCategoryResponse;
import com.johanwork.job.service.IJobCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/job-categories")
public class JobCategoryController {

    private final IJobCategoryService jobCategoryService;

    @PostMapping
    public ResponseEntity<GenericResponse<JobCategoryResponse>> createCategory(
            @RequestBody @Valid JobCategoryRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(jobCategoryService.createCategory(req));
    }

    @GetMapping
    public ResponseEntity<GenericResponse<List<JobCategoryResponse>>> getAllCategories() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(jobCategoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<JobCategoryResponse>> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(jobCategoryService.getCategoryById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<JobCategoryResponse>> updateCategory(
            @PathVariable Long id,
            @RequestBody @Valid JobCategoryRequest request
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(jobCategoryService.updateCategory(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Void>> deleteCategory(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(jobCategoryService.deleteCategory(id));
    }

}

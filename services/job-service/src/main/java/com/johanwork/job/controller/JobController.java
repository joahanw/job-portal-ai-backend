package com.johanwork.job.controller;

import com.johanwork.job.dto.JobSearchRequest;
import com.johanwork.job.dto.request.JobRequest;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.JobResponse;
import com.johanwork.job.dto.response.PageResponse;
import com.johanwork.job.service.IJobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {

    private final IJobService jobService;

    @GetMapping
    public ResponseEntity<GenericResponse<PageResponse<JobResponse>>> getAllJobs(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortDirection,
            @ModelAttribute JobSearchRequest searchRequest
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(jobService.getAllJobs(pageNumber, pageSize, sortBy, sortDirection, searchRequest));
    }

    @PostMapping
    public ResponseEntity<GenericResponse<JobResponse>> createJob(
            @RequestHeader("X-User-Id") Long employerId,
            @RequestBody @Valid JobRequest jobRequest
            ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(jobService.createJob(employerId, jobRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<JobResponse>> getJobById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(jobService.getJobById(id));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<GenericResponse<List<JobResponse>>> getJobByCompanyId(@PathVariable Long companyId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(jobService.getJobByCompany(companyId));
    }

    @GetMapping("/admin")
    public ResponseEntity<GenericResponse<PageResponse<JobResponse>>> getAllJobAdmin(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortDirection
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(jobService.getAllJobAdmin(pageNumber, pageSize, sortBy, sortDirection));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<JobResponse>> updateJob(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long employerId,
            @RequestBody @Valid JobRequest jobRequest){
        return ResponseEntity.status(HttpStatus.OK)
                .body(jobService.updateJob(id, employerId, jobRequest));
    }

    @PatchMapping("/{id}/publish")
    public ResponseEntity<GenericResponse<JobResponse>> publishJob(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long employerId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(jobService.publishJob(id, employerId));
    }

    @PatchMapping("/{id}/close")
    public ResponseEntity<GenericResponse<JobResponse>> closeJob(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long employerId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(jobService.closeJob(id, employerId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Void>> deleteJob(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long employerId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(jobService.deleteJob(id, employerId));
    }

}

package com.johanwork.job.client;

import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.ResumeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "job-portal-resume-service", path = "/api/resumes")
public interface ResumeClient {

    @GetMapping("/{id}")
    public GenericResponse<ResumeResponse> getResumeById(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long candidateId
    );

}

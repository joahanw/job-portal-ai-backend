package com.johanwork.job.client;

import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.JobResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "job-portal-job-service", path = "/api/jobs")
public interface JobClient {

    @GetMapping("/{id}")
    public GenericResponse<JobResponse> getJobById(@PathVariable Long id);

}

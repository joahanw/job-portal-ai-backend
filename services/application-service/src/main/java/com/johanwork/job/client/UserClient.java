package com.johanwork.job.client;

import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "job-portal-user-service", path = "/api/users")
public interface UserClient {

    @GetMapping("/{id}")
    public GenericResponse<UserResponse> getUserById(@PathVariable Long id);

}

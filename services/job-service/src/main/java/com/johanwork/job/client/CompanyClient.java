package com.johanwork.job.client;

import com.johanwork.job.dto.response.CompanyResponse;
import com.johanwork.job.dto.response.GenericResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "job-portal-company-service", path = "/api/companies")
public interface CompanyClient {

    @GetMapping("/{id}")
    public GenericResponse<CompanyResponse> getCompanyById(@PathVariable Long id);

    @GetMapping("/my")
    public GenericResponse<CompanyResponse> getCompaniesByOwner(
            @RequestHeader("X-User-Id") Long ownerId
    );

}

package com.johanwork.job.controller;

import com.johanwork.job.domain.CompanyStatus;
import com.johanwork.job.domain.CompanyType;
import com.johanwork.job.domain.IndustryType;
import com.johanwork.job.dto.request.CompanyRequest;
import com.johanwork.job.dto.response.CompanyResponse;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.PageResponse;
import com.johanwork.job.service.ICompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {

    private final ICompanyService companyService;

    @GetMapping
    public ResponseEntity<GenericResponse<PageResponse<CompanyResponse>>> getAllCompanies(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortDirection,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) CompanyType companyType,
            @RequestParam(required = false) IndustryType industryType,
            @RequestParam(required = false) CompanyStatus companyStatus
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(companyService.getAllCompanies(pageNumber, pageSize, sortBy, sortDirection, search,
                companyType, industryType, companyStatus));
    }

    @PostMapping
    public ResponseEntity<GenericResponse<CompanyResponse>> createCompany(
            @RequestHeader("X-User-Id") Long ownerId,
            @RequestBody @Valid CompanyRequest companyRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(companyService.createCompany(ownerId, companyRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<CompanyResponse>> getCompanyById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(companyService.getCompanyById(id));
    }

    @GetMapping("/my")
    public ResponseEntity<GenericResponse<CompanyResponse>> getCompaniesByOwner(
            @RequestHeader("X-User-Id") Long ownerId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(companyService.getMyCompany(ownerId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<CompanyResponse>> updateCompany(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long ownerId,
            @RequestBody @Valid CompanyRequest companyRequest
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(companyService.updateCompany(id, ownerId, companyRequest));
    }

    @PatchMapping("/{id}/verify")
    public ResponseEntity<GenericResponse<CompanyResponse>> verifyCompany(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(companyService.verifyCompany(id));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<GenericResponse<CompanyResponse>> deactivateCompany(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(companyService.deactivateCompany(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Void>> deleteCompany(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long ownerId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(companyService.deleteCompany(id, ownerId));
    }

}

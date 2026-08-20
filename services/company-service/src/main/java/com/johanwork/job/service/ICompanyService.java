package com.johanwork.job.service;

import com.johanwork.job.domain.CompanyStatus;
import com.johanwork.job.domain.CompanyType;
import com.johanwork.job.domain.IndustryType;
import com.johanwork.job.dto.request.CompanyRequest;
import com.johanwork.job.dto.response.CompanyResponse;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.PageResponse;

public interface ICompanyService {

    GenericResponse<PageResponse<CompanyResponse>> getAllCompanies(int pageNumber, int pageSize, String sortBy,
                                                                  String sortDirection, String search,
                                                                  CompanyType companyType,
                                                                  IndustryType industryType,
                                                                  CompanyStatus companyStatus);

    GenericResponse<CompanyResponse> createCompany(Long ownerId, CompanyRequest req);
    GenericResponse<CompanyResponse> getCompanyById(Long id);
    GenericResponse<CompanyResponse> getMyCompany(Long ownerId);

    GenericResponse<CompanyResponse> updateCompany(Long id, Long ownerId, CompanyRequest req);
    GenericResponse<CompanyResponse> verifyCompany(Long id);
    GenericResponse<CompanyResponse> deactivateCompany(Long id);
    GenericResponse<Void> deleteCompany(Long id, Long ownerId);
}

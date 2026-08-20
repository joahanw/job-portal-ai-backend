package com.johanwork.job.service.impl;

import com.johanwork.job.constant.AppConstant;
import com.johanwork.job.domain.CompanyStatus;
import com.johanwork.job.domain.CompanyType;
import com.johanwork.job.domain.IndustryType;
import com.johanwork.job.dto.request.CompanyRequest;
import com.johanwork.job.dto.response.CompanyResponse;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.PageResponse;
import com.johanwork.job.exception.CustomException;
import com.johanwork.job.mapper.CompanyMapper;
import com.johanwork.job.model.Company;
import com.johanwork.job.repository.CompanyRepository;
import com.johanwork.job.service.ICompanyDomainService;
import com.johanwork.job.service.ICompanyService;
import com.johanwork.job.specification.CompanySpecification;
import com.johanwork.job.util.SlugGenerator;
import com.johanwork.job.util.Violations;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService implements ICompanyService {

    private final ICompanyDomainService domain;
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    public GenericResponse<PageResponse<CompanyResponse>> getAllCompanies(int pageNumber, int pageSize, String sortBy,
                                                                         String sortDirection, String search,
                                                                         CompanyType companyType, IndustryType industryType,
                                                                         CompanyStatus companyStatus) {
        Sort sort = sortBy.equalsIgnoreCase("desc")
                ? Sort.by(Sort.Direction.DESC, sortBy)
                : Sort.by(Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Company> companies = companyRepository.findAll(
                CompanySpecification.filter(search, companyType, industryType, companyStatus), pageable);
        return companyMapper.mapToPageGenericResponse(companies,
                String.format(AppConstant.Success.FETCHED, "Companies"));
    }

    @Transactional
    @Override
    public GenericResponse<CompanyResponse> createCompany(Long ownerId, CompanyRequest req) {
        validation(ownerId, req);
        String slug = SlugGenerator.generate(req.name(), companyRepository::existsBySlug);
        Company company = companyMapper.mapRequestToEntity(new Company(), req);
        company.setSlug(slug);
        company.setOwnerId(ownerId);
        company.setCompanyStatus(CompanyStatus.PENDING_VERIFICATION);

        return companyMapper.mapToGenericResponse(
                companyRepository.save(company),
                String.format(AppConstant.Success.CREATED, "Company")
        );
    }

    @Override
    public GenericResponse<CompanyResponse> getCompanyById(Long id) {
        return companyMapper.mapToGenericResponse(
                domain.getById(id),
                String.format(AppConstant.Success.FETCHED, "Company")
        );
    }

    @Override
    public GenericResponse<CompanyResponse> getMyCompany(Long ownerId) {
        return companyMapper.mapToGenericResponse(
                domain.getByOwnerId(ownerId),
                String.format(AppConstant.Success.FETCHED, "Company")
        );
    }

    @Transactional
    @Override
    public GenericResponse<CompanyResponse> updateCompany(Long id, Long ownerId, CompanyRequest req) {
        Company company = domain.getById(id);
        validationUpdate(company, req, ownerId);
        company = companyMapper.mapRequestToEntity(company, req);

        return companyMapper.mapToGenericResponse(
                company,
                String.format(AppConstant.Success.UPDATED, "Company")
        );
    }

    @Transactional
    @Override
    public GenericResponse<CompanyResponse> verifyCompany(Long id) {
        Company company = domain.getById(id);
        company.setCompanyStatus(CompanyStatus.ACTIVE);
        company.setVerified(true);
        return companyMapper.mapToGenericResponse(
                company, String.format(AppConstant.Success.UPDATED, "Company")
        );
    }

    @Transactional
    @Override
    public GenericResponse<CompanyResponse> deactivateCompany(Long id) {
        Company company = domain.getById(id);
        company.setCompanyStatus(CompanyStatus.SUSPENDED);
        company.setVerified(false);
        return companyMapper.mapToGenericResponse(
                company, String.format(AppConstant.Success.UPDATED, "Company")
        );
    }

    @Transactional
    @Override
    public GenericResponse<Void> deleteCompany(Long id, Long ownerId) {
        Company company = domain.getById(id);
        assertOwner(company, ownerId);
        companyRepository.delete(company);
        return companyMapper.mapToGenericResponse(
                String.format(AppConstant.Success.DELETED, "Company")
        );
    }


    private void assertOwner(Company company, Long ownerId) {
        if (!company.getOwnerId().equals(ownerId)){
            throw new CustomException(HttpStatus.FORBIDDEN,
                    AppConstant.Error.TITLE_FORBIDDEN,
                    AppConstant.Error.MESSAGE_FORBIDDEN);
        }
    }

    private void validation(Long ownerId, CompanyRequest req){
        new Violations()
            .check(companyRepository.existsByOwnerId(ownerId), "ownerId",
                    "You already have a company registered")
            .check(companyRepository.existsByName(req.name()),"name",
                    "Company name already exists. Please choose a different name")
            .check(null != req.registrationNumber() &&
                    companyRepository.existsByRegistrationNumber(req.registrationNumber()),"registrationNumber",
                    "Company registration number already exists. Please choose a different registration number")
            .throwIfAny();
    }

    private void validationUpdate(Company company, CompanyRequest req, Long ownerId){
        assertOwner(company, ownerId);
        new Violations()
                .check(!company.getName().equals(req.name()) &&
                        companyRepository.existsByName(req.name()), "name",
                        "Company already exists. Please choose a different name")
                .check(null != req.registrationNumber()
                        && !req.registrationNumber().equals(company.getRegistrationNumber())
                        && companyRepository.existsByRegistrationNumber(req.registrationNumber()),
                        "registrationNumber", "Company registration number already exists. Please choose a different registration number")
                .throwIfAny();
    }

}

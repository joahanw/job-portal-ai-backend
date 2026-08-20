package com.johanwork.job.service.impl;

import com.johanwork.job.constant.AppConstant;
import com.johanwork.job.exception.CustomException;
import com.johanwork.job.model.Company;
import com.johanwork.job.repository.CompanyRepository;
import com.johanwork.job.service.ICompanyDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyDomainService implements ICompanyDomainService {

    private final CompanyRepository companyRepository;

    @Override
    public Company getById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new CustomException(
                        HttpStatus.NOT_FOUND,
                        String.format(AppConstant.Error.TITLE_NOT_FOUND, "Company"),
                        String.format(AppConstant.Error.MESSAGE_NOT_FOUND, "Company", id)
                ));
    }

    @Override
    public Company getByOwnerId(Long ownerId) {
        return companyRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new CustomException(
                        HttpStatus.NOT_FOUND,
                        String.format(AppConstant.Error.TITLE_NOT_FOUND, "Company"),
                        String.format(AppConstant.Error.MESSAGE_NOT_FOUND, "Company", ownerId)
                ));
    }
}

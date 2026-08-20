package com.johanwork.job.service;

import com.johanwork.job.model.Company;

public interface ICompanyDomainService {
    Company getById(Long id);
    Company getByOwnerId(Long ownerId);
}

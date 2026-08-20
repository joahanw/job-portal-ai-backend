package com.johanwork.job.service;

import com.johanwork.job.model.Job;

import java.util.List;

public interface IJobDomainService {
    Job getById(Long id);
    List<Job> getByCompany(Long companyId);
}

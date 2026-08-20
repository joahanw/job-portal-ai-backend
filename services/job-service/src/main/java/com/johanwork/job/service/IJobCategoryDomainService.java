package com.johanwork.job.service;

import com.johanwork.job.model.JobCategory;

public interface IJobCategoryDomainService {
    JobCategory getById(Long id);
}

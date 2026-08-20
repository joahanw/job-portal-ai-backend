package com.johanwork.job.service;

import com.johanwork.job.model.JobTag;

import java.util.Set;

public interface IJobTagDomainService {
    JobTag getById(Long id);
    Set<JobTag> getJobIds(Set<Long> jobIds);
}

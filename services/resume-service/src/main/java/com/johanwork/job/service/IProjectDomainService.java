package com.johanwork.job.service;

import com.johanwork.job.model.Project;

public interface IProjectDomainService {
    Project getById(Long id);
}

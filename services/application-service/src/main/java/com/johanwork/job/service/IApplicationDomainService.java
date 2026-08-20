package com.johanwork.job.service;

import com.johanwork.job.model.Application;

public interface IApplicationDomainService {
    Application getById(Long id);
}

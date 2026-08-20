package com.johanwork.job.service;

import com.johanwork.job.model.Resume;

public interface IResumeDomainService {
    Resume getById(Long id);
}

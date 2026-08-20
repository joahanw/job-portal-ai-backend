package com.johanwork.job.service;

import com.johanwork.job.model.User;

public interface IUserDomainService {
    User getByEmail(String email);
    User getById(Long id);
}

package com.johanwork.job.service;

import com.johanwork.job.dto.AuthResponse;
import com.johanwork.job.dto.LoginRequest;
import com.johanwork.job.dto.SignupRequest;
import com.johanwork.job.dto.response.GenericResponse;

public interface IAuthService {
    GenericResponse<AuthResponse> signup(SignupRequest request);
    GenericResponse<AuthResponse> login(LoginRequest request);
}

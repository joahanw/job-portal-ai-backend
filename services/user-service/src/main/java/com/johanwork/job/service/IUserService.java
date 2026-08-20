package com.johanwork.job.service;

import com.johanwork.job.dto.UserRequest;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.PageResponse;
import com.johanwork.job.dto.response.UserResponse;
import com.johanwork.job.model.User;

import java.util.List;

public interface IUserService {

    GenericResponse<PageResponse<UserResponse>> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDirection, String search);
    GenericResponse<UserResponse> updateProfile(String email, UserRequest request);
    GenericResponse<UserResponse> getProfile(String email);
    GenericResponse<UserResponse> getUserById(Long id);

    // admin action
    GenericResponse<UserResponse> suspendUser(Long id);
    GenericResponse<UserResponse> activateUser(Long id);
    GenericResponse<UserResponse> deleteUser(Long id);
}

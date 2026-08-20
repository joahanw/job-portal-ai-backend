package com.johanwork.job.service.impl;

import com.johanwork.job.constant.AppConstant;
import com.johanwork.job.domain.UserStatus;
import com.johanwork.job.dto.UserRequest;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.PageResponse;
import com.johanwork.job.dto.response.UserResponse;
import com.johanwork.job.mapper.UserMapper;
import com.johanwork.job.model.User;
import com.johanwork.job.repository.UserRepository;
import com.johanwork.job.service.IUserDomainService;
import com.johanwork.job.service.IUserService;
import com.johanwork.job.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final IUserDomainService domain;


    @Override
    public GenericResponse<PageResponse<UserResponse>> getAllUser(int pageNumber, int pageSize,
                                                                 String sortBy, String sortDirection,
                                                                 String search) {
        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(Sort.Direction.DESC, sortBy)
                : Sort.by(Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Specification<User> specification =
                UserSpecification.filter(search);
        Page<User> users = userRepository.findAll(specification, pageable);
        return userMapper.mapToPageUserResponse(users,
                String.format(AppConstant.Success.FETCHED, "Users"));
    }

    @Transactional
    @Override
    public GenericResponse<UserResponse> updateProfile(String email, UserRequest request) {
        User user = domain.getByEmail(email);
        user.setFullName(request.fullName());
        user.setPhone(request.phone());
        user.setProfileImage(request.profileImage());
        return userMapper.mapToUserResponse(user,
                String.format(AppConstant.Success.UPDATED,"User"));
    }

    @Override
    public GenericResponse<UserResponse> getProfile(String email) {
        return userMapper.mapToUserResponse(domain.getByEmail(email),
                String.format(AppConstant.Success.FETCHED, "User"));
    }

    @Override
    public GenericResponse<UserResponse> getUserById(Long id) {
        return userMapper.mapToUserResponse(domain.getById(id),
                String.format(AppConstant.Success.FETCHED, "User"));
    }

    @Transactional
    @Override
    public GenericResponse<UserResponse> suspendUser(Long id) {
        User user = domain.getById(id);
        user.setStatus(UserStatus.SUSPENDED);
        user.setSuspendedAt(Instant.now());
        return userMapper.mapToUserResponse(user,
                String.format(AppConstant.Success.UPDATED, "User"));
    }

    @Transactional
    @Override
    public GenericResponse<UserResponse> activateUser(Long id) {
        User user = domain.getById(id);
        user.setStatus(UserStatus.ACTIVE);
        user.setSuspendedAt(null);
        return userMapper.mapToUserResponse(user,
                String.format(AppConstant.Success.UPDATED, "User"));
    }

    @Transactional
    @Override
    public GenericResponse<UserResponse> deleteUser(Long id) {
        User user = domain.getById(id);
        user.setStatus(UserStatus.DELETED);
        user.setDeletedAt(Instant.now());
        return userMapper.mapToUserResponse(user,
                String.format(AppConstant.Success.DELETED, "User"));
    }
}

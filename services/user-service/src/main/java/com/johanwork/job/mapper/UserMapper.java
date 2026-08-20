package com.johanwork.job.mapper;

import com.johanwork.job.domain.UserStatus;
import com.johanwork.job.dto.UserRequest;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.PageResponse;
import com.johanwork.job.dto.response.UserResponse;
import com.johanwork.job.model.User;
import com.johanwork.job.dto.AuthResponse;
import com.johanwork.job.dto.SignupRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class UserMapper{

    public User mapRequestToEntity(SignupRequest req){
        return User.builder()
                .fullName(req.fullName())
                .email(req.email())
                .password(req.password())
                .phone(req.phone())
                .role(req.role())
                .lastLogin(Instant.now())
                .status(UserStatus.ACTIVE)
                .build();
    }

    public UserResponse mapEntityToUserResponse(User user){
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhone(),
                user.getProfileImage(),
                user.getRole(),
                user.getStatus(),
                user.getLastLogin(),
                user.getCreatedAt()
        );
    }

    public GenericResponse<AuthResponse> mapEntityToAuthResponse(User user, String token, String message){
        return new GenericResponse<>(new AuthResponse(
                token,
                "Welcome " + user.getFullName(),
                mapEntityToUserResponse(user)
        ) , message) ;
    }

    public GenericResponse<UserResponse> mapToUserResponse(User user, String message) {
        return new GenericResponse<>(mapEntityToUserResponse(user), message);
    }


    public GenericResponse<PageResponse<UserResponse>> mapToPageUserResponse(Page<User> users, String message) {
        return new GenericResponse<>(
                new PageResponse<>(
                        users.map(this::mapEntityToUserResponse).getContent(),
                        users.getNumber(),
                        users.getSize(),
                        users.getTotalElements(),
                        users.getTotalPages(),
                        users.hasNext(),
                        users.hasPrevious()
                ),
                message
        );
    }
}

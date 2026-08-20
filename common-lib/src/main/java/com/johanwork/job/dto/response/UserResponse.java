package com.johanwork.job.dto.response;

import com.johanwork.job.domain.UserRole;
import com.johanwork.job.domain.UserStatus;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String profileImage;
    private UserRole role;
    private UserStatus status;
    private Instant lastLogin;
    private Instant createdAt;
}

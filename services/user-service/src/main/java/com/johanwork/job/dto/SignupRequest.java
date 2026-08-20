package com.johanwork.job.dto;

import com.johanwork.job.domain.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignupRequest(

        @NotBlank(message = "fullName is mandatory")
        String fullName,

        @NotBlank(message = "email is mandatory")
        @Email(message = "email is invalid")
        String email,

        @NotBlank(message = "phone is mandatory")
        String phone,

        @NotBlank(message = "password is mandatory")
        String password,

        @NotNull(message = "role is mandatory")
        UserRole role
) {
}

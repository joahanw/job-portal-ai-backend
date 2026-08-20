package com.johanwork.job.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @NotBlank(message = "email is mandatory")
        @Email(message = "email is invalid")
        String email,

        @NotBlank(message = "password is mandatory")
        String password
) {
}

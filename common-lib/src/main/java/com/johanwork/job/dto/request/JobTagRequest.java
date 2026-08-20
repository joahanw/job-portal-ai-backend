package com.johanwork.job.dto.request;

import jakarta.validation.constraints.NotBlank;

public record JobTagRequest(

        @NotBlank(message = "Name is required")
        String name

) {
}

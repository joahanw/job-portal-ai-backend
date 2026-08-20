package com.johanwork.job.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddApplicationNoteRequest(

        @NotBlank(message = "Content is required")
        @Size(max = 2000, message = "Content must be less than 2000 characters")
        String content
) {
}

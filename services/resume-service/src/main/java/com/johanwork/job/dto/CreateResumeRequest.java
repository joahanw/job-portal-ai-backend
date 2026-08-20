package com.johanwork.job.dto;

import com.johanwork.job.domain.ResumeTemplate;
import com.johanwork.job.domain.ResumeVisibility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateResumeRequest(

        @NotBlank(message = "Resume title is required")
        String title,

        ResumeTemplate template,

        ResumeVisibility visibility,

        Boolean isDefault

) {
}

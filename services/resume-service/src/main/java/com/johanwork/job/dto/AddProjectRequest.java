package com.johanwork.job.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.List;

public record AddProjectRequest(

        @NotBlank(message = "Title is required")
        String title,

        String description,
        List<String> technologies,

        @Pattern(regexp = "^(https?://).*", message = "Project URL must start with http or https")
        String projectUrl,

        @Pattern(regexp = "^(https?://).*", message = "Source code URL must start with http or https")
        String sourceCodeUrl,

        @NotNull(message = "Start date is required")
        LocalDate startDate,
        LocalDate endDate,

        Boolean isOngoing,

        Integer displayOrder

) {
}

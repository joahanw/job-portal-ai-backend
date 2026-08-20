package com.johanwork.job.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AddEducationRequest(

        @NotBlank(message = "institution name is required")
        String institutionName,

        @NotBlank(message = "degree is required")
        String degree,

        String fieldOfStudy,
        String grade,

        @NotNull(message = "start date is required")
        LocalDate startDate,
        LocalDate endDate,

        Boolean isCurrentlyStudying,

        String description,

        Integer displayOrder
) {
}

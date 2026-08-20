package com.johanwork.job.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ApplicationRequest(

        @NotNull(message = "Job Id is required")
        Long jobId,

        @NotNull(message = "Resume Id is required")
        Long resumeId,

        @Size(max = 3000, message = "Cover latter must not exceed 3000 characters")
        String coverLetter,

        @DecimalMin(value = "0.0", message = "Expected salary must be a positive number")
        BigDecimal expectedSalary,

        LocalDate availableFrom
) {
}

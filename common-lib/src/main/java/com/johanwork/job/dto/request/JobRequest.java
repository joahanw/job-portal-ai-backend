package com.johanwork.job.dto.request;

import com.johanwork.job.domain.ExperienceLevel;
import com.johanwork.job.domain.JobType;
import com.johanwork.job.domain.WorkMode;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public record JobRequest(

        @NotBlank(message = "Job title is required")
        String title,

        @NotBlank(message = "Description is required")
        String description,

        String requirements,
        String responsibilities,
        String benefits,

        @NotNull(message = "Category ID is required")
        Long categoryId,

        /** IDs of the skills associated with the job_skills table. */
        Set<Long> skillIds,

        /** IDs of the tags associated with the job_tags table. */
        Set<Long> tagIds,

        //======================================================
        // Location Details
        //======================================================
        String address,
        String city,
        String state,
        String country,
        String zipCode,

        //======================================================
        // Salary Details
        //======================================================

        @DecimalMin(value = "0.0", inclusive = true, message = "Min Salary must be greater than or equal to 0")
        BigDecimal minSalary,

        @DecimalMin(value = "0.0", inclusive = true, message = "Max Salary must be greater than or equal to 0")
        BigDecimal maxSalary,

        //======================================================
        // Classification
        //======================================================

        @NotNull(message = "Job type is required")
        JobType jobType,

        @NotNull(message = "Work mode is required")
        WorkMode workMode,

        @NotNull(message = "Experience level is required")
        ExperienceLevel experienceLevel,

        //======================================================
        // Posting Details
        //======================================================

        @Min(value = 1, message = "Openings must be at least 1")
        Integer openings,

        LocalDate applicationDeadline,

        LocalDate expiresAt
) {
}

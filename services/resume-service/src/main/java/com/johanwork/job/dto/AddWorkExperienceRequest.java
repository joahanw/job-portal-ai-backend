package com.johanwork.job.dto;

import com.johanwork.job.domain.JobType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record AddWorkExperienceRequest(

        @NotBlank(message = "Company name is required")
        String companyName,

        String companyLogoUrl,

        @NotBlank(message = "Job title is required")
        String jobTitle,

        JobType employmentType,

        String location,

        @NotNull(message = "Start date is required")
        LocalDate startDate,

        LocalDate endDate,

        Boolean isCurrentJob,

        String description,

        List<String> technologies,

        Integer displayOrder

) {
}

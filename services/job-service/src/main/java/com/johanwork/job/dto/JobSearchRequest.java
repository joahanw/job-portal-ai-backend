package com.johanwork.job.dto;

import com.johanwork.job.domain.ExperienceLevel;
import com.johanwork.job.domain.JobStatus;
import com.johanwork.job.domain.JobType;
import com.johanwork.job.domain.WorkMode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

public record JobSearchRequest(
        String keyword,
        Long categoryId,
        List<Long> skillIds,
        List<Long> tagIds,
        Long companyId,

        /** Match city, state or country (case-insensitive) */
        String location,

        /** Salary overlap - job max salary must be  >= minSalary */
        BigDecimal minSalary,
        BigDecimal maxSalary,

        JobType jobType,

        WorkMode workMode,

        ExperienceLevel experienceLevel,

        /** Default to OPEN in the service */
        JobStatus status,

        Integer minOpenings,
        Integer maxOpenings
) {
}

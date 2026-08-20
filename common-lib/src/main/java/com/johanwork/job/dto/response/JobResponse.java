package com.johanwork.job.dto.response;

import com.johanwork.job.domain.ExperienceLevel;
import com.johanwork.job.domain.JobStatus;
import com.johanwork.job.domain.JobType;
import com.johanwork.job.domain.WorkMode;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class JobResponse {

    private Long id;
    private String title;
    private String description;
    private String requirements;
    private String responsibilities;
    private String benefits;

    private CompanyResponse company;
    private Long employerId;

    private JobCategoryResponse category;
    private Set<JobSkillResponse> skills;
    private Set<JobTagResponse> tags;

    // Location
    private String address;
    private String city;
    private String state;
    private String country;
    private String zipCode;

    // Salary
    private BigDecimal minSalary;
    private BigDecimal maxSalary;
    private String currency;
    private Boolean salaryNegotiable;
    private Boolean salaryDisclosed;

    // Classification
    private JobType jobType;
    private WorkMode workMode;
    private ExperienceLevel experienceLevel;
    private JobStatus status;

    // Posting details
    private Integer openings;
    private LocalDate applicationDeadline;
    private LocalDate expiresAt;
    private Boolean active;

    // Analytics
    private Long viewCount;
    private Long applicationCount;

    // Timestamps
    private Instant createdAt;
    private Instant updatedAt;
    private Instant publishedAt;
    private Instant closedAt;

}

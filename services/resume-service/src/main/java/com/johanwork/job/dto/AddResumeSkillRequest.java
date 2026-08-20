package com.johanwork.job.dto;

import com.johanwork.job.domain.ProficiencyLevel;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AddResumeSkillRequest(

        @NotBlank(message = "Skill name is required")
        @Size(max = 100, message = "Skill name must be less than 100 characters")
        String skillName,

        @NotNull(message = "Proficiency level is required")
        ProficiencyLevel proficiencyLevel,

        @Min(value = 0, message = "Years of experience must be non-negative")
        Integer yearsOfExperience,

        Integer displayOrder

) {
}

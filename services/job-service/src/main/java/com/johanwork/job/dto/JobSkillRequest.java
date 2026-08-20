package com.johanwork.job.dto;

import com.johanwork.job.domain.SkillCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record JobSkillRequest(

        @NotBlank(message = "Skill name is required")
        @Size(max=100, message = "Name must be not exceed 100 characters")
        String name,

        @NotNull(message = "Skill category is required")
        SkillCategory category) {
}

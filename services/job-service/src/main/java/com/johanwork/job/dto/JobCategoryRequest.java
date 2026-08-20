package com.johanwork.job.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record JobCategoryRequest(
        @NotBlank(message = "Category name is required")
        String name,

        @Size(max = 500, message = "Description must be less than 500 characters")
        String description,

        String iconUrl,

        /** null means root-level */
        Long parentId
) {
}

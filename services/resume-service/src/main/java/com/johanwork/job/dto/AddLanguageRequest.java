package com.johanwork.job.dto;

import com.johanwork.job.domain.LanguageProficiency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddLanguageRequest(

        @NotBlank(message = "Language name is required")
        String languageName,

        @NotNull(message = "Proficiency is required")
        LanguageProficiency proficiency,

        Integer displayOrder

) {
}

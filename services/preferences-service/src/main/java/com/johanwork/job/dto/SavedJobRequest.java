package com.johanwork.job.dto;

import jakarta.validation.constraints.NotNull;

public record SavedJobRequest(
        @NotNull(message = "jobId cannot be null")
        Long jobId
) {
}

package com.johanwork.job.dto;

import com.johanwork.job.domain.AiShortListStatus;
import com.johanwork.job.domain.ApplicationStatus;

public record CompanyApplicationFilterRequest(
        Long jobId,
        ApplicationStatus status,
        boolean isStarred,
        AiShortListStatus aiShortListStatus,
        Integer aiScore,
        String sortBy
) {
}

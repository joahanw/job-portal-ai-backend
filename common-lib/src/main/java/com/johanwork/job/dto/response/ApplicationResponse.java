package com.johanwork.job.dto.response;

import com.johanwork.job.domain.ApplicationStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
@Builder
public class ApplicationResponse {
    private Long id;
    private UserResponse candidate;
    private Long employerId;

    private JobResponse job;
    private CompanyResponse company;

    private ApplicationStatus status;

    // Submission content
    private Long resumeId;
    private String coverLetter;

    // Candidate preferences
    private BigDecimal expectedSalary;
    private LocalDate availableFrom;

    // Tracking
    private Boolean isStarred;

    private List<ApplicationNoteResponse> notes;

    // Withdrawal
    private Instant withdrawnAt;
    private String withdrawnReason;

    private Instant appliedAt;
    private Instant updatedAt;

    // AI Screening result - null until background scoring completes
//    private ApplicationScreeningResponse screening;
}

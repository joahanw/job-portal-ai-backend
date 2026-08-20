package com.johanwork.job.dto.response;


import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class SavedJobResponse {
    private Long id;
    private Long candidateId;
    private Long jobId;
    private Instant savedAt;
}

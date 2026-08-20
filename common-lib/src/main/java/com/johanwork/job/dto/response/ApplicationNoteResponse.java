package com.johanwork.job.dto.response;

import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class ApplicationNoteResponse {
    private Long id;
    private Long addByUserId;
    private String content;
    private Instant createdAt;
}

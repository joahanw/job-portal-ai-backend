package com.johanwork.job.dto.response;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class EducationResponse {
    private Long id;
    private String institutionName;
    private String degree;
    private String fieldOfStudy;
    private String grade;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isCurrentlyStudying;
    private String description;
    private Integer displayOrder;
}

package com.johanwork.job.dto.response;

import com.johanwork.job.domain.ResumeTemplate;
import com.johanwork.job.domain.ResumeVisibility;
import lombok.*;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class ResumeResponse {

    private Long id;
    private Long candidateId;
    private String title;
    private ResumeTemplate template;
    private ResumeVisibility visibility;
    private Boolean isDefault;
    private PersonalInfoResponse personalInfo;
    private String summary;
    private Integer completionScore;
    private Instant createdAt;
    private Instant updatedAt;

    private List<WorkExperienceResponse> workExperiences;
    private List<EducationResponse> educations;
    private List<ResumeSkillResponse> skills;
    private List<ProjectResponse> projects;
    private List<LanguageResponse> languages;
//    private List<CertificationResponse> certifications;
//    private List<AwardResponse> awards;

}

package com.johanwork.job.dto.response;

import com.johanwork.job.domain.SkillCategory;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class JobSkillResponse {

    private Long id;
    private String name;
    private String slug;
    private SkillCategory category;
    private Boolean active;

}

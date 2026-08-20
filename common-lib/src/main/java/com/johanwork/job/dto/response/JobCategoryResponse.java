package com.johanwork.job.dto.response;

import lombok.*;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class JobCategoryResponse {

    private Long id;
    private String name;
    private String slug;
    private String description;
    private String iconUrl;
    private Boolean active;

    private Long parentId;
    private String parentName;

    private List<JobCategoryResponse> subCategories;
    private Instant createdAt;

}

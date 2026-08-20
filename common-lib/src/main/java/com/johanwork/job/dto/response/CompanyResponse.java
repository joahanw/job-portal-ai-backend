package com.johanwork.job.dto.response;

import com.johanwork.job.domain.CompanySize;
import com.johanwork.job.domain.CompanyStatus;
import com.johanwork.job.domain.CompanyType;
import com.johanwork.job.domain.IndustryType;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyResponse {

    private Long id;
    private String name;
    private String slug;
    private String tagline;
    private String description;
    private String logoUrl;
    private String coverImageUrl;
    private String website;
    private String email;
    private String phone;
    private Integer foundedYear;

    private CompanySize companySize;
    private CompanyType companyType;
    private IndustryType industryType;
    private CompanyStatus status;
    private Boolean verified;
    private Boolean active;

    private Long ownerId;

    private List<SocialLinkResponse> socialLinks;

    private Instant createdAt;
    private Instant updatedAt;

}

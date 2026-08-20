package com.johanwork.job.dto.request;

import com.johanwork.job.domain.CompanySize;
import com.johanwork.job.domain.CompanyType;
import com.johanwork.job.domain.IndustryType;
import com.johanwork.job.dto.response.SocialLinkResponse;
import jakarta.validation.constraints.*;

import java.util.List;

public record CompanyRequest(

        @NotBlank(message = "Company name is required")
        String name,

        String tagline,

        String description,

        String logoUrl,

        String coverImageUrl,

        @Email(message = "Company email must be valid")
        String email,

        @Pattern(regexp = "^(https?://).*", message = "Website must be valid URL")
        String website,

        String phone,

        @Min(value = 1800, message = "Founded year must be after 1800")
        @Max(value = 2200, message = "Founded year is invalid")
        Integer foundedYear,

        @NotNull(message = "Company size is required")
        CompanySize companySize,

        @NotNull(message = "Company type is required")
        CompanyType companyType,

        @NotNull(message = "Industry type is required")
        IndustryType industryType,

        String registrationNumber,

        List<SocialLinkResponse> socialLinks

) {
}

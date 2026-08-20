package com.johanwork.job.mapper;

import com.johanwork.job.dto.request.CompanyRequest;
import com.johanwork.job.dto.response.CompanyResponse;
import com.johanwork.job.dto.response.PageResponse;
import com.johanwork.job.dto.response.SocialLinkResponse;
import com.johanwork.job.model.Company;
import com.johanwork.job.model.SocialLink;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompanyMapper implements GenericResponseMapper<Company, CompanyRequest, CompanyResponse>{

    @Override
    public CompanyResponse mapEntityToResponse(Company company) {
        return CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .slug(company.getSlug())
                .tagline(company.getTagline())
                .description(company.getDescription())
                .logoUrl(company.getLogoUrl())
                .coverImageUrl(company.getCoverImageUrl())
                .website(company.getWebsite())
                .email(company.getEmail())
                .phone(company.getPhone())
                .foundedYear(company.getFoundedYear())
                .companySize(company.getCompanySize())
                .companyType(company.getCompanyType())
                .industryType(company.getIndustryType())
                .status(company.getCompanyStatus())
                .active(company.getActive())
                .verified(company.getVerified())
                .ownerId(company.getOwnerId())
                .socialLinks(mapSocialLinkRes(company.getSocialLinks()))
                .createdAt(company.getCreatedAt())
                .updatedAt(company.getUpdatedAt())
                .build();
    }

    @Override
    public Company mapRequestToEntity(Company company, CompanyRequest req) {
        company.setName(req.name());
        company.setTagline(req.tagline());
        company.setDescription(req.description());
        company.setLogoUrl(req.logoUrl());
        company.setCoverImageUrl(req.coverImageUrl());
        company.setWebsite(req.website());
        company.setEmail(req.email());
        company.setPhone(req.phone());
        company.setFoundedYear(req.foundedYear());
        company.setCompanySize(req.companySize());
        company.setCompanyType(req.companyType());
        company.setIndustryType(req.industryType());
        company.setRegistrationNumber(req.registrationNumber());
        company.setSocialLinks(mapSocialLink(req.socialLinks()));
        return company;
    }

    @Override
    public List<CompanyResponse> mapListEntityToListResponse(List<Company> m) {
        return List.of();
    }

    @Override
    public PageResponse<CompanyResponse> mapPageEntityToPageResponse(Page<Company> m) {
        return new PageResponse<>(
                m.map(this::mapEntityToResponse).getContent(),
                m.getNumber(),
                m.getSize(),
                m.getTotalElements(),
                m.getTotalPages(),
                m.hasNext(),
                m.hasPrevious()
        );
    }

    private List<SocialLinkResponse> mapSocialLinkRes(List<SocialLink> socialLinks){
        if (null == socialLinks || socialLinks.isEmpty()) {
            return new ArrayList<SocialLinkResponse>();
        }
        return socialLinks.stream()
                .map(socialLink ->
                        new SocialLinkResponse(socialLink.getPlatform(), socialLink.getUrl()))
                .toList();
    }

    private List<SocialLink> mapSocialLink(List<SocialLinkResponse> socialLinks){
        if (null == socialLinks || socialLinks.isEmpty()) {
            return new ArrayList<SocialLink>();
        }
        return socialLinks.stream()
                .map(socialLink ->
                        new SocialLink(socialLink.getPlatform(), socialLink.getUrl()))
                .toList();
    }

}

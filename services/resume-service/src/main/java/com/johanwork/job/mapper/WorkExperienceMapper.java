package com.johanwork.job.mapper;

import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.PageResponse;
import com.johanwork.job.dto.response.WorkExperienceResponse;
import com.johanwork.job.mapper.GenericResponseMapper;
import com.johanwork.job.dto.AddWorkExperienceRequest;
import com.johanwork.job.model.WorkExperience;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WorkExperienceMapper implements GenericResponseMapper<WorkExperience, AddWorkExperienceRequest, WorkExperienceResponse> {

    @Override
    public WorkExperienceResponse mapEntityToResponse(WorkExperience experience) {
        return WorkExperienceResponse.builder()
                .id(experience.getId())
                .companyName(experience.getCompanyName())
                .companyLogoUrl(experience.getCompanyLogoUrl())
                .jobTitle(experience.getJobTitle())
                .employmentType(experience.getEmploymentType())
                .location(experience.getLocation())
                .startDate(experience.getStartDate())
                .endDate(experience.getEndDate())
                .isCurrentJob(experience.getIsCurrentJob())
                .description(experience.getDescription())
                .technologies(experience.getTechnologies())
                .displayOrder(experience.getDisplayOrder())
                .build();
    }

    @Override
    public WorkExperience mapRequestToEntity(WorkExperience experience, AddWorkExperienceRequest req) {
        experience.setCompanyName(req.companyName());
        experience.setCompanyLogoUrl(req.companyLogoUrl());
        experience.setJobTitle(req.jobTitle());
        experience.setEmploymentType(req.employmentType());
        experience.setLocation(req.location());
        experience.setStartDate(req.startDate());
        experience.setEndDate(req.endDate());
        experience.setIsCurrentJob(req.isCurrentJob());
        experience.setDescription(req.description());
        experience.setTechnologies(req.technologies() != null ? req.technologies() : List.of());
        experience.setDisplayOrder(req.displayOrder() != null ? req.displayOrder() : 0);
        return experience;
    }

    @Override
    public List<WorkExperienceResponse> mapListEntityToListResponse(List<WorkExperience> m) {
        if (m.isEmpty()) return List.of();
        return m.stream().map(this::mapEntityToResponse).toList();
    }

    @Override
    public PageResponse<WorkExperienceResponse> mapPageEntityToPageResponse(Page<WorkExperience> m) {
        return null;
    }

}

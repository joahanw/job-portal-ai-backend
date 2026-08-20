package com.johanwork.job.mapper;

import com.johanwork.job.dto.response.EducationResponse;
import com.johanwork.job.dto.response.PageResponse;
import com.johanwork.job.mapper.GenericResponseMapper;
import com.johanwork.job.dto.AddEducationRequest;
import com.johanwork.job.model.Education;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EducationMapper implements GenericResponseMapper<Education, AddEducationRequest, EducationResponse> {

    @Override
    public EducationResponse mapEntityToResponse(Education education) {
        return EducationResponse.builder()
                .id(education.getId())
                .institutionName(education.getInstitutionName())
                .degree(education.getDegree())
                .fieldOfStudy(education.getFieldOfStudy())
                .grade(education.getGrade())
                .startDate(education.getStartDate())
                .endDate(education.getEndDate())
                .isCurrentlyStudying(education.getIsCurrentlyStudying())
                .description(education.getDescription())
                .displayOrder(education.getDisplayOrder())
                .build();
    }

    @Override
    public Education mapRequestToEntity(Education education, AddEducationRequest req) {
        education.setInstitutionName(req.institutionName());
        education.setDegree(req.degree());
        education.setFieldOfStudy(req.fieldOfStudy());
        education.setGrade(req.grade());
        education.setStartDate(req.startDate());
        education.setEndDate(req.endDate());
        education.setIsCurrentlyStudying(Boolean.TRUE.equals(req.isCurrentlyStudying()));
        education.setDescription(req.description());
        education.setDisplayOrder(req.displayOrder() != null ? req.displayOrder() : 0);
        return education;
    }

    @Override
    public List<EducationResponse> mapListEntityToListResponse(List<Education> m) {
        if (m.isEmpty()) return List.of();
        return m.stream().map(this::mapEntityToResponse).toList();
    }

    @Override
    public PageResponse<EducationResponse> mapPageEntityToPageResponse(Page<Education> m) {
        return null;
    }
}

package com.johanwork.job.mapper;

import com.johanwork.job.dto.response.PageResponse;
import com.johanwork.job.dto.response.ProjectResponse;
import com.johanwork.job.mapper.GenericResponseMapper;
import com.johanwork.job.dto.AddProjectRequest;
import com.johanwork.job.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectMapper implements GenericResponseMapper<Project, AddProjectRequest, ProjectResponse> {

    @Override
    public ProjectResponse mapEntityToResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .technologies(project.getTechnologies())
                .projectUrl(project.getProjectUrl())
                .sourceCodeUrl(project.getSourceCodeUrl())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .isOngoing(project.getIsOngoing())
                .displayOrder(project.getDisplayOrder())
                .build();
    }

    @Override
    public Project mapRequestToEntity(Project project, AddProjectRequest req) {
        project.setTitle(req.title());
        project.setDescription(req.description());
        project.setTechnologies(req.technologies() != null ? req.technologies() : List.of());
        project.setProjectUrl(req.projectUrl());
        project.setSourceCodeUrl(req.sourceCodeUrl());
        project.setStartDate(req.startDate());
        project.setEndDate(req.endDate());
        project.setIsOngoing(Boolean.TRUE.equals(req.isOngoing()));
        project.setDisplayOrder(req.displayOrder() != null ? req.displayOrder() : 0);
        return project;
    }

    @Override
    public List<ProjectResponse> mapListEntityToListResponse(List<Project> m) {
        if (m.isEmpty()) return List.of();
        return m.stream().map(this::mapEntityToResponse).toList();
    }

    @Override
    public PageResponse<ProjectResponse> mapPageEntityToPageResponse(Page<Project> m) {
        return null;
    }
}

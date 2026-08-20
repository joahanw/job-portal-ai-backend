package com.johanwork.job.service.impl;

import com.johanwork.job.constant.AppConstant;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.ProjectResponse;
import com.johanwork.job.dto.AddProjectRequest;
import com.johanwork.job.mapper.ProjectMapper;
import com.johanwork.job.model.Project;
import com.johanwork.job.model.Resume;
import com.johanwork.job.repository.ProjectRepository;
import com.johanwork.job.service.IProjectDomainService;
import com.johanwork.job.service.IProjectService;
import com.johanwork.job.service.IResumeDomainService;
import com.johanwork.job.service.IResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.johanwork.job.util.ResumeUtil.assertOwner;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService implements IProjectService {

    private final IResumeDomainService resumeDomainService;
    private final IProjectDomainService domain;
    private final ProjectRepository repository;
    private final ProjectMapper mapper;

    @Transactional
    @Override
    public GenericResponse<ProjectResponse> addProject(Long resumeId, Long candidateId, AddProjectRequest req) {
        Resume resume = resumeDomainService.getById(resumeId);
        assertOwner(resume, candidateId);
        Project project = mapper.mapRequestToEntity(new Project(), req);
        project.setResume(resume);
        return mapper.mapToGenericResponse(
                repository.save(project),
                String.format(AppConstant.Success.CREATED, "Project")
        );
    }

    @Override
    public GenericResponse<List<ProjectResponse>> getAllProjects(Long resumeId) {
        return mapper.mapToListGenericResponse(
                repository.findByResume_IdOrderByDisplayOrderAsc(resumeId),
                String.format(AppConstant.Success.FETCHED, "Projects")
        );
    }

    @Transactional
    @Override
    public GenericResponse<ProjectResponse> updateProject(Long id, Long resumeId, Long candidateId, AddProjectRequest req) {
        Project project = domain.getById(id);
        assertOwner(project.getResume(), candidateId, resumeId);
        project = mapper.mapRequestToEntity(project, req);
        return mapper.mapToGenericResponse(
                project,
                String.format(AppConstant.Success.UPDATED, "Project")
        );
    }

    @Transactional
    @Override
    public GenericResponse<Void> deleteProject(Long id, Long resumeId, Long candidateId) {
        Project project = domain.getById(id);
        assertOwner(project.getResume(), candidateId, resumeId);
        project.getResume().getProjects().remove(project);
        return mapper.mapToGenericResponse(
                String.format(AppConstant.Success.DELETED, "Project")
        );
    }
}

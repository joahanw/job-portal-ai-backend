package com.johanwork.job.service;

import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.ProjectResponse;
import com.johanwork.job.dto.AddProjectRequest;

import java.util.List;

public interface IProjectService {

    GenericResponse<ProjectResponse> addProject(Long resumeId, Long candidateId, AddProjectRequest req);

    GenericResponse<List<ProjectResponse>> getAllProjects(Long resumeId);

    GenericResponse<ProjectResponse> updateProject(Long id, Long resumeId, Long candidateId, AddProjectRequest req);

    GenericResponse<Void> deleteProject(Long id, Long resumeId, Long candidateId);

}

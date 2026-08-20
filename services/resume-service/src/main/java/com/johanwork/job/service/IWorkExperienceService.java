package com.johanwork.job.service;

import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.WorkExperienceResponse;
import com.johanwork.job.dto.AddWorkExperienceRequest;

import java.util.List;

public interface IWorkExperienceService {

    GenericResponse<WorkExperienceResponse> addWorkExperience(Long resumeId, Long candidateId, AddWorkExperienceRequest req);

    GenericResponse<List<WorkExperienceResponse>> getWorkExperiences(Long resumeId);

    GenericResponse<WorkExperienceResponse> updateWorkExperience(Long id, Long resumeId, Long candidateId, AddWorkExperienceRequest req);

    GenericResponse<Void> deleteWorkExperience(Long id, Long resumeId, Long candidateId);

}

package com.johanwork.job.service;

import com.johanwork.job.dto.response.EducationResponse;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.AddEducationRequest;

import java.util.List;

public interface IEducationService {

    GenericResponse<EducationResponse> addEducation(Long resumeId, Long candidateId, AddEducationRequest req);
    GenericResponse<List<EducationResponse>> getEducation(Long resumeId);
    GenericResponse<EducationResponse> updateEducation(Long id, Long resumeId, Long candidateId, AddEducationRequest req);
    GenericResponse<Void>deleteEducation(Long id, Long resumeId, Long candidateId);

}

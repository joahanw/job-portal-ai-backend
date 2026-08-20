package com.johanwork.job.service;

import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.PersonalInfoResponse;
import com.johanwork.job.dto.response.ResumeResponse;
import com.johanwork.job.dto.CreateResumeRequest;
import lombok.Getter;

import java.util.List;

public interface IResumeService {

    GenericResponse<ResumeResponse> createResume(Long candidateId, CreateResumeRequest req);

    GenericResponse<ResumeResponse> getResumeById(Long id, Long candidateId);

    GenericResponse<List<ResumeResponse>> getMyResumes(Long candidateId);

    GenericResponse<ResumeResponse> updatePersonalInfo(Long id, Long candidateId, PersonalInfoResponse req);

    GenericResponse<ResumeResponse> updateSummary(Long id, Long candidateId, String summary);

    GenericResponse<ResumeResponse> setDefaultResume(Long id, Long candidateId);

    GenericResponse<Void> deleteResume(Long id, Long candidateId);

    GenericResponse<List<ResumeResponse>> getAll();

}

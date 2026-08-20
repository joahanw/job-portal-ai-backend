package com.johanwork.job.service;

import com.johanwork.job.domain.ApplicationStatus;
import com.johanwork.job.dto.ApplicationRequest;
import com.johanwork.job.dto.CompanyApplicationFilterRequest;
import com.johanwork.job.dto.response.ApplicationResponse;
import com.johanwork.job.dto.response.GenericResponse;

import java.util.List;

public interface IApplicationService {

    GenericResponse<ApplicationResponse> createApplication(Long candidateId,
                                                          ApplicationRequest req);

    GenericResponse<ApplicationResponse> getApplicationById(Long id);

    GenericResponse<List<ApplicationResponse>> getMyApplication(Long candidateId);

    GenericResponse<List<ApplicationResponse>> getApplicationForJob(Long jobId);

    GenericResponse<List<ApplicationResponse>> getApplicationForCompany(Long userId, CompanyApplicationFilterRequest filter);

    GenericResponse<ApplicationResponse> updateStatus(Long id, Long employerId, ApplicationStatus status);

    GenericResponse<ApplicationResponse> withdrawnApplication(Long id, Long candidateId, String reason);

    GenericResponse<ApplicationResponse> toggleStart(Long id, Long employerId);

    GenericResponse<Void> deleteApplication(Long id, Long candidateId);

}

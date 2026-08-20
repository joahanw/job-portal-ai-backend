package com.johanwork.job.service;

import com.johanwork.job.dto.JobSearchRequest;
import com.johanwork.job.dto.request.JobRequest;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.JobResponse;
import com.johanwork.job.dto.response.PageResponse;

import java.util.List;

public interface IJobService {

    GenericResponse<PageResponse<JobResponse>> getAllJobs(int pageNumber, int pageSize,
                                                         String sortBy, String sortDirection,
                                                         JobSearchRequest searchRequest);
    GenericResponse<List<JobResponse>> getJobByCompany(Long companyId);

    GenericResponse<JobResponse> getJobById(Long id);
    GenericResponse<JobResponse> createJob(Long employerId, JobRequest req);
    GenericResponse<JobResponse> updateJob(Long id, Long employerId, JobRequest req);
    GenericResponse<JobResponse> publishJob(Long id, Long employerId);
    GenericResponse<JobResponse> closeJob(Long id, Long employerId);
    GenericResponse<Void> deleteJob(Long id, Long employerId);

    GenericResponse<PageResponse<JobResponse>> getAllJobAdmin(int pageNumber, int pageSize,
                                                          String sortBy, String sortDirection);
}

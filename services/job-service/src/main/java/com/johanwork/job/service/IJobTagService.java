package com.johanwork.job.service;

import com.johanwork.job.dto.request.JobTagRequest;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.JobTagResponse;

import java.util.List;

public interface IJobTagService {

    GenericResponse<JobTagResponse> createTag(JobTagRequest req);
    GenericResponse<List<JobTagResponse>> getAllTags();
    GenericResponse<JobTagResponse> getTagById(Long id);
    GenericResponse<JobTagResponse> updateTag(Long id, JobTagRequest req);
    GenericResponse<Void> deleteTag(Long id);

}

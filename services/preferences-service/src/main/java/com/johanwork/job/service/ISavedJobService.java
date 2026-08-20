package com.johanwork.job.service;

import com.johanwork.job.dto.SavedJobRequest;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.SavedJobResponse;

import java.util.List;

public interface ISavedJobService {
    GenericResponse<SavedJobResponse> saveJob(Long candidateId, SavedJobRequest req);
    GenericResponse<Void> unsaveJob(Long id, Long candidateId);
    GenericResponse<List<SavedJobResponse>> getSavedJobs(Long candidateId);
}

package com.johanwork.job.service.impl;

import com.johanwork.job.constant.AppConstant;
import com.johanwork.job.dto.SavedJobRequest;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.SavedJobResponse;
import com.johanwork.job.exception.CustomException;
import com.johanwork.job.mapper.SavedJobMapper;
import com.johanwork.job.model.SavedJob;
import com.johanwork.job.repository.SavedJobRepository;
import com.johanwork.job.service.ISavedJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SavedJobService implements ISavedJobService {

    private final SavedJobRepository repository;
    private final SavedJobMapper mapper;

    @Transactional
    @Override
    public GenericResponse<SavedJobResponse> saveJob(Long candidateId, SavedJobRequest req) {
        if (isSaved(candidateId,req.jobId())){
            throw new CustomException(HttpStatus.BAD_REQUEST,
                    AppConstant.Error.TITLE_JOB_ALREADY_SAVED,
                    AppConstant.Error.MESSAGE_JOB_ALREADY_SAVED);
        }
        SavedJob savedJob = SavedJob.builder()
                .candidateId(candidateId)
                .jobId(req.jobId())
                .build();
        return mapper.mapToGenericResponse(
                repository.save(savedJob),
                AppConstant.Success.IS_SAVED_JOB
        );
    }

    @Transactional
    @Override
    public GenericResponse<Void> unsaveJob(Long id, Long candidateId) {
        SavedJob savedJob = repository.findById(id).orElseThrow(() ->
                new CustomException(HttpStatus.NOT_FOUND,
                        String.format(AppConstant.Error.TITLE_NOT_FOUND, "Saved Job"),
                        String.format(AppConstant.Error.MESSAGE_NOT_FOUND, "Saved Job", id)
                ));
        if (!savedJob.getCandidateId().equals(candidateId)){
            throw new CustomException(HttpStatus.FORBIDDEN,
                    AppConstant.Error.TITLE_FORBIDDEN,
                    AppConstant.Error.MESSAGE_FORBIDDEN);
        }
        repository.delete(savedJob);
        return mapper.mapToGenericResponse(String.format(AppConstant.Success.IS_UNSAVED_JOB, "Saved Job"));
    }

    @Override
    public GenericResponse<List<SavedJobResponse>> getSavedJobs(Long candidateId) {
        return mapper.mapToListGenericResponse(
                repository.findByCandidateId(candidateId),
                String.format(AppConstant.Success.FETCHED, "Saved Jobs")
        );
    }

    private Boolean isSaved(Long candidateId, Long jobId) {
        return repository.existsByCandidateIdAndJobId(candidateId, jobId);
    }
}

package com.johanwork.job.mapper;

import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.SavedJobResponse;
import com.johanwork.job.model.SavedJob;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SavedJobMapper {

    public SavedJobResponse mapEntityToResponse(SavedJob savedJobs){
        return SavedJobResponse.builder()
                .id(savedJobs.getId())
                .candidateId(savedJobs.getCandidateId())
                .jobId(savedJobs.getJobId())
                .savedAt(savedJobs.getSavedAt())
                .build();
    }

    public GenericResponse<SavedJobResponse> mapToGenericResponse(SavedJob savedJobs, String message){
        var res = mapEntityToResponse(savedJobs);
        return new GenericResponse<>(res, message);
    }

    public GenericResponse<Void> mapToGenericResponse(String message){
        return new GenericResponse<>(null, message);
    }

    public GenericResponse<List<SavedJobResponse>> mapToListGenericResponse(List<SavedJob> savedJobs, String message){
        var res = savedJobs.stream().map(this::mapEntityToResponse).toList();
        return new GenericResponse<>(res, message);
    }

}

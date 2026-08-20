package com.johanwork.job.service.impl;

import com.johanwork.job.constant.AppConstant;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.WorkExperienceResponse;
import com.johanwork.job.exception.CustomException;
import com.johanwork.job.dto.AddWorkExperienceRequest;
import com.johanwork.job.mapper.WorkExperienceMapper;
import com.johanwork.job.model.Resume;
import com.johanwork.job.model.WorkExperience;
import com.johanwork.job.repository.WorkExperienceRepository;
import com.johanwork.job.service.IResumeDomainService;
import com.johanwork.job.service.IResumeService;
import com.johanwork.job.service.IWorkExperienceDomainService;
import com.johanwork.job.service.IWorkExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.johanwork.job.util.ResumeUtil.assertOwner;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkExperienceService implements IWorkExperienceService {

    private final IResumeDomainService resumeDomainService;
    private final IWorkExperienceDomainService domain;
    private final WorkExperienceRepository repository;
    private final WorkExperienceMapper mapper;

    @Transactional
    @Override
    public GenericResponse<WorkExperienceResponse> addWorkExperience(Long resumeId, Long candidateId, AddWorkExperienceRequest req) {
        Resume resume = resumeDomainService.getById(resumeId);
        assertOwner(resume, candidateId);
        WorkExperience workExperience = mapper.mapRequestToEntity(new WorkExperience(), req);
        workExperience.setResume(resume);
        return mapper.mapToGenericResponse(
                repository.save(workExperience),
                String.format(AppConstant.Success.CREATED, "Work Experience")
        );
    }

    @Override
    public GenericResponse<List<WorkExperienceResponse>> getWorkExperiences(Long resumeId) {
        return mapper.mapToListGenericResponse(
                repository.findByResume_IdOrderByDisplayOrderAsc(resumeId),
                String.format(AppConstant.Success.FETCHED, "Work Experiences")
        );
    }

    @Transactional
    @Override
    public GenericResponse<WorkExperienceResponse> updateWorkExperience(Long id, Long resumeId, Long candidateId, AddWorkExperienceRequest req) {
        WorkExperience workExperience = domain.getById(id);
        assertOwner(workExperience.getResume(), candidateId, resumeId);
        workExperience = mapper.mapRequestToEntity(workExperience, req);
        return mapper.mapToGenericResponse(
                workExperience,
                String.format(AppConstant.Success.UPDATED, "Work Experience")
        );
    }

    @Transactional
    @Override
    public GenericResponse<Void> deleteWorkExperience(Long id, Long resumeId, Long candidateId) {
        WorkExperience workExperience = domain.getById(id);
        assertOwner(workExperience.getResume(), candidateId, resumeId);
        workExperience.getResume().getWorkExperiences().remove(workExperience);
        return mapper.mapToGenericResponse(
                String.format(AppConstant.Success.DELETED, "Work Experience")
        );
    }

}

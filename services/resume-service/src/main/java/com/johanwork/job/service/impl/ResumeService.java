package com.johanwork.job.service.impl;

import com.johanwork.job.constant.AppConstant;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.PersonalInfoResponse;
import com.johanwork.job.dto.response.ResumeResponse;
import com.johanwork.job.exception.CustomException;
import com.johanwork.job.dto.CreateResumeRequest;
import com.johanwork.job.mapper.ResumeMapper;
import com.johanwork.job.model.Resume;
import com.johanwork.job.repository.ResumeRepository;
import com.johanwork.job.service.IResumeDomainService;
import com.johanwork.job.service.IResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.johanwork.job.util.ResumeUtil.assertOwner;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResumeService implements IResumeService {

    private final ResumeRepository repository;
    private final ResumeMapper mapper;
    private final IResumeDomainService domain;

    @Transactional
    @Override
    public GenericResponse<ResumeResponse> createResume(Long candidateId, CreateResumeRequest req) {

        if (Boolean.TRUE.equals(req.isDefault())){
            repository.findByCandidateIdAndIsDefaultTrue(candidateId)
                    .ifPresent(exist -> {
                        exist.setIsDefault(false);
                        repository.save(exist);
                    });
        }
        Resume resume = mapper.mapCreateResumeRequestToEntity(new Resume(), candidateId, req);
        return mapper.mapToGenericResponse(
                repository.save(resume),
                String.format(AppConstant.Success.CREATED, "Resume")
        );
    }

    @Override
    public GenericResponse<ResumeResponse> getResumeById(Long id, Long candidateId) {
        Resume resume = domain.getById(id);
        assertOwner(resume, candidateId);
        return mapper.mapToGenericResponse(
                resume,
                String.format(AppConstant.Success.FETCHED, "Resume")
        );
    }

    @Override
    public GenericResponse<List<ResumeResponse>> getMyResumes(Long candidateId) {
        return mapper.mapToListGenericResponse(
                repository.findByCandidateIdAndIsActiveTrue(candidateId),
                String.format(AppConstant.Success.FETCHED, "Resumes")
        );
    }

    @Transactional
    @Override
    public GenericResponse<ResumeResponse> updatePersonalInfo(Long id, Long candidateId, PersonalInfoResponse req) {
        Resume resume = domain.getById(id);
        assertOwner(resume, candidateId);
        resume.setPersonalInfo(mapper.mapToPersonalInfo(resume.getPersonalInfo(), req));
        return mapper.mapToGenericResponse(
                resume,
                String.format(AppConstant.Success.UPDATED, "Resume")
        );
    }

    @Transactional
    @Override
    public GenericResponse<ResumeResponse> updateSummary(Long id, Long candidateId, String summary) {
        Resume resume = domain.getById(id);
        assertOwner(resume, candidateId);
        resume.setSummary(summary);
        return mapper.mapToGenericResponse(
                resume,
                String.format(AppConstant.Success.UPDATED, "Resume")
        );
    }

    @Transactional
    @Override
    public GenericResponse<ResumeResponse> setDefaultResume(Long id, Long candidateId) {
        Resume resume = domain.getById(id);
        assertOwner(resume, candidateId);
        repository.findByCandidateIdAndIsDefaultTrue(candidateId)
                .ifPresent(exist -> {
                    exist.setIsDefault(false);
                    repository.save(exist);
                });
        resume.setIsDefault(true);
        return mapper.mapToGenericResponse(
                resume,
                String.format(AppConstant.Success.UPDATED, "Resume")
        );
    }

    @Transactional
    @Override
    public GenericResponse<Void> deleteResume(Long id, Long candidateId) {
        Resume resume = domain.getById(id);
        assertOwner(resume, candidateId);
        resume.setActive(false);
        resume.setIsDefault(false);
        return mapper.mapToGenericResponse(
                String.format(AppConstant.Success.DELETED, "Resume")
        );
    }

    @Override
    public GenericResponse<List<ResumeResponse>> getAll() {
        return mapper.mapToListGenericResponse(
                repository.findAll(),
                String.format(AppConstant.Success.FETCHED, "Resumes")
        );
    }

}

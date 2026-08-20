package com.johanwork.job.service.impl;

import com.johanwork.job.constant.AppConstant;
import com.johanwork.job.dto.response.EducationResponse;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.AddEducationRequest;
import com.johanwork.job.mapper.EducationMapper;
import com.johanwork.job.model.Education;
import com.johanwork.job.model.Resume;
import com.johanwork.job.repository.EducationRepository;
import com.johanwork.job.service.IEducationDomainService;
import com.johanwork.job.service.IEducationService;
import com.johanwork.job.service.IResumeDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.johanwork.job.util.ResumeUtil.assertOwner;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EducationService implements IEducationService {

    private final IResumeDomainService resumeDomainService;
    private final IEducationDomainService domain;
    private final EducationRepository repository;
    private final EducationMapper mapper;

    @Transactional
    @Override
    public GenericResponse<EducationResponse> addEducation(Long resumeId, Long candidateId, AddEducationRequest req) {
        Resume resume = resumeDomainService.getById(resumeId);
        assertOwner(resume, candidateId);
        Education education = mapper.mapRequestToEntity(new Education(), req);
        education.setResume(resume);
        return mapper.mapToGenericResponse(
                repository.save(education),
                String.format(AppConstant.Success.CREATED, "Education")
        );
    }

    @Override
    public GenericResponse<List<EducationResponse>> getEducation(Long resumeId) {
        return mapper.mapToListGenericResponse(
                repository.findByResume_IdOrderByDisplayOrderAsc(resumeId),
                String.format(AppConstant.Success.FETCHED, "Education")
        );
    }

    @Transactional
    @Override
    public GenericResponse<EducationResponse> updateEducation(Long id, Long resumeId, Long candidateId, AddEducationRequest req) {
        Education education = domain.getById(id);
        assertOwner(education.getResume(), candidateId, resumeId);
        education = mapper.mapRequestToEntity(education, req);
        return mapper.mapToGenericResponse(
                education,
                String.format(AppConstant.Success.UPDATED, "Education")
        );
    }

    @Transactional
    @Override
    public GenericResponse<Void> deleteEducation(Long id, Long resumeId, Long candidateId) {
        Education education = domain.getById(id);
        assertOwner(education.getResume(), candidateId, resumeId);
        education.getResume().getEducations().remove(education);
        return mapper.mapToGenericResponse(
                String.format(AppConstant.Success.DELETED, "Education")
        );
    }
}

package com.johanwork.job.service.impl;

import com.johanwork.job.constant.AppConstant;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.ResumeSkillResponse;
import com.johanwork.job.exception.CustomException;
import com.johanwork.job.dto.AddResumeSkillRequest;
import com.johanwork.job.mapper.ResumeSkillMapper;
import com.johanwork.job.model.Resume;
import com.johanwork.job.model.ResumeSkill;
import com.johanwork.job.repository.ResumeSkillRepository;
import com.johanwork.job.service.IResumeDomainService;
import com.johanwork.job.service.IResumeSkillDomainService;
import com.johanwork.job.service.IResumeSkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.johanwork.job.util.ResumeUtil.assertOwner;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResumeSkillService implements IResumeSkillService {

    private final IResumeDomainService resumeDomainService;
    private final IResumeSkillDomainService domain;
    private final ResumeSkillRepository repository;
    private final ResumeSkillMapper mapper;

    @Transactional
    @Override
    public GenericResponse<ResumeSkillResponse> addSkill(Long resumeId, Long candidateId, AddResumeSkillRequest req) {
        Resume resume = resumeDomainService.getById(resumeId);
        assertOwner(resume, candidateId);
        ResumeSkill skill = mapper.mapRequestToEntity(new ResumeSkill(), req);
        skill.setResume(resume);
        return mapper.mapToGenericResponse(
                repository.save(skill),
                String.format(AppConstant.Success.CREATED, "Resume Skill")
        );
    }

    @Override
    public GenericResponse<List<ResumeSkillResponse>> getSkills(Long resumeId) {
        return mapper.mapToListGenericResponse(
                repository.findByResume_IdOrderByDisplayOrderAsc(resumeId),
                String.format(AppConstant.Success.FETCHED, "Resume Skills")
        );
    }

    @Transactional
    @Override
    public GenericResponse<ResumeSkillResponse> updateSkill(Long id, Long resumeId, Long candidateId, AddResumeSkillRequest req) {
        ResumeSkill skill = domain.getById(id);
        assertOwner(skill.getResume(), candidateId, resumeId);
        skill = mapper.mapRequestToEntity(skill, req);
        return mapper.mapToGenericResponse(
                skill,
                String.format(AppConstant.Success.UPDATED, "Resume Skill")
        );
    }

    @Transactional
    @Override
    public GenericResponse<Void> deleteSkill(Long id, Long resumeId, Long candidateId) {
        ResumeSkill skill = domain.getById(id);
        assertOwner(skill.getResume(), candidateId, resumeId);
        skill.getResume().getSkills().remove(skill);
        return mapper.mapToGenericResponse(
                String.format(AppConstant.Success.DELETED, "Resume Skill")
        );
    }

}

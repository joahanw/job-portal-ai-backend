package com.johanwork.job.service.impl;

import com.johanwork.job.constant.AppConstant;
import com.johanwork.job.dto.JobSkillRequest;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.JobSkillResponse;
import com.johanwork.job.exception.CustomException;
import com.johanwork.job.mapper.JobSkillMapper;
import com.johanwork.job.model.JobSkill;
import com.johanwork.job.repository.JobSkillRepository;
import com.johanwork.job.service.IJobSkillDomainService;
import com.johanwork.job.service.IJobSkillService;
import com.johanwork.job.util.SlugGenerator;
import com.johanwork.job.util.Violations;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobSkillService implements IJobSkillService {

    private final JobSkillRepository jobSkillRepository;
    private final JobSkillMapper mapper;
    private final IJobSkillDomainService domain;

    @Transactional
    @Override
    public GenericResponse<JobSkillResponse> createSkill(JobSkillRequest req) {
        Map<String, String> violations = new HashMap<>();
        new Violations()
                .check(jobSkillRepository.existsByName(req.name()),
                        "name", "Skill name already exists")
                .throwIfAny();

        String slug =  SlugGenerator.generate(req.name(), jobSkillRepository::existsBySlug);
        JobSkill jobSkill = mapper.mapRequestToEntity(new JobSkill(), req);
        jobSkill.setSlug(slug);
        return mapper.mapToGenericResponse(
                jobSkillRepository.save(jobSkill),
                String.format(AppConstant.Success.CREATED, "Job Skill")
        );
    }

    @Override
    public GenericResponse<List<JobSkillResponse>> getAllSkills() {
        return mapper.mapToListGenericResponse(
                jobSkillRepository.findByActiveTrue(),
                String.format(AppConstant.Success.FETCHED, "Job Skills")
        );
    }

    @Override
    public GenericResponse<JobSkillResponse> getSkillById(Long id) {
        return mapper.mapToGenericResponse(
                domain.getById(id),
                String.format(AppConstant.Success.FETCHED, "Job Skill")
        );
    }

    @Transactional
    @Override
    public GenericResponse<JobSkillResponse> updatedSkill(Long id, JobSkillRequest req) {
        JobSkill jobSkill = domain.getById(id);
        new Violations()
                .check(!jobSkill.getName().equals(req.name()) &&
                        jobSkillRepository.existsByName(req.name()),
                        "name", "Skill name already exists")
                .throwIfAny();
        jobSkill = mapper.mapRequestToEntity(jobSkill, req);
        return mapper.mapToGenericResponse(jobSkill,
                String.format(AppConstant.Success.UPDATED, "Job Skill"));
    }

    @Transactional
    @Override
    public GenericResponse<Void> deleteSkill(Long id) {
        JobSkill jobSkill = domain.getById(id);
        jobSkill.setActive(false);
        return mapper.mapToGenericResponse(
                String.format(AppConstant.Success.DELETED, "Job Skill")
        );
    }

}

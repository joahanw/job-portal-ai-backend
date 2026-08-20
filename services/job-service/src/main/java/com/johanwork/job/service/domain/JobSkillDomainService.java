package com.johanwork.job.service.domain;

import com.johanwork.job.constant.AppConstant;
import com.johanwork.job.exception.CustomException;
import com.johanwork.job.model.JobSkill;
import com.johanwork.job.repository.JobSkillRepository;
import com.johanwork.job.service.IJobSkillDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobSkillDomainService implements IJobSkillDomainService {

    private final JobSkillRepository jobSkillRepository;

    @Override
    public JobSkill getById(Long id) {
        return jobSkillRepository.findById(id)
                .orElseThrow(() -> new CustomException(
                        HttpStatus.NOT_FOUND,
                        String.format(AppConstant.Error.TITLE_NOT_FOUND, "Job Skill"),
                        String.format(AppConstant.Error.MESSAGE_NOT_FOUND, "Job Skill", id)
                ));
    }

    @Override
    public Set<JobSkill> getSkills(Set<Long> ids) {
        Set<JobSkill> skills = new HashSet<>(jobSkillRepository.findAllById(ids));
        return skills;
    }

}

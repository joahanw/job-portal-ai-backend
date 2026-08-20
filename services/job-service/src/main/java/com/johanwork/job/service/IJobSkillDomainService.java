package com.johanwork.job.service;

import com.johanwork.job.model.JobSkill;

import java.util.Set;

public interface IJobSkillDomainService {
    JobSkill getById(Long id);
    Set<JobSkill> getSkills(Set<Long> ids);
}

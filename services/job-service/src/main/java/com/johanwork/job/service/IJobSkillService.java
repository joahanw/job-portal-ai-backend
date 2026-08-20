package com.johanwork.job.service;

import com.johanwork.job.dto.JobSkillRequest;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.JobSkillResponse;
import com.johanwork.job.model.JobSkill;

import java.util.List;
import java.util.Set;

public interface IJobSkillService {

    GenericResponse<JobSkillResponse> createSkill(JobSkillRequest req);
    GenericResponse<List<JobSkillResponse>> getAllSkills();
    GenericResponse<JobSkillResponse> getSkillById(Long id);
    GenericResponse<JobSkillResponse> updatedSkill(Long id, JobSkillRequest req);
    GenericResponse<Void> deleteSkill(Long id);

}

package com.johanwork.job.service;

import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.ResumeSkillResponse;
import com.johanwork.job.dto.AddResumeSkillRequest;

import java.util.List;

public interface IResumeSkillService {

    GenericResponse<ResumeSkillResponse> addSkill(Long resumeId, Long candidateId, AddResumeSkillRequest req);

    GenericResponse<List<ResumeSkillResponse>> getSkills(Long resumeId);

    GenericResponse<ResumeSkillResponse> updateSkill(Long id, Long resumeId, Long candidateId, AddResumeSkillRequest req);

    GenericResponse<Void> deleteSkill(Long id, Long resumeId, Long candidateId);
}

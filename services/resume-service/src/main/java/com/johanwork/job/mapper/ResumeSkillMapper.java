package com.johanwork.job.mapper;

import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.PageResponse;
import com.johanwork.job.dto.response.ResumeSkillResponse;
import com.johanwork.job.mapper.GenericResponseMapper;
import com.johanwork.job.dto.AddResumeSkillRequest;
import com.johanwork.job.model.ResumeSkill;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResumeSkillMapper implements GenericResponseMapper<ResumeSkill, AddResumeSkillRequest, ResumeSkillResponse> {

    @Override
    public ResumeSkillResponse mapEntityToResponse(ResumeSkill skill) {
        return ResumeSkillResponse.builder()
                .id(skill.getId())
                .skillName(skill.getSkillName())
                .proficiencyLevel(skill.getProficiencyLevel())
                .yearsOfExperience(skill.getYearsOfExperience())
                .displayOrder(skill.getDisplayOrder())
                .build();
    }

    @Override
    public ResumeSkill mapRequestToEntity(ResumeSkill skill, AddResumeSkillRequest req) {
        skill.setSkillName(req.skillName());
        skill.setProficiencyLevel(req.proficiencyLevel());
        skill.setYearsOfExperience(req.yearsOfExperience());
        skill.setDisplayOrder(req.displayOrder());
        return skill;
    }

    @Override
    public List<ResumeSkillResponse> mapListEntityToListResponse(List<ResumeSkill> m) {
        if (m.isEmpty()) return List.of();
        return m.stream().map(this::mapEntityToResponse).toList();
    }

    @Override
    public PageResponse<ResumeSkillResponse> mapPageEntityToPageResponse(Page<ResumeSkill> m) {
        return null;
    }

}

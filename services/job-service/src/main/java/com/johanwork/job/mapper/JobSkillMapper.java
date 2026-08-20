package com.johanwork.job.mapper;

import com.johanwork.job.dto.JobSkillRequest;
import com.johanwork.job.dto.response.JobSkillResponse;
import com.johanwork.job.dto.response.PageResponse;
import com.johanwork.job.model.JobSkill;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobSkillMapper implements GenericResponseMapper<JobSkill, JobSkillRequest, JobSkillResponse> {

    @Override
    public JobSkillResponse mapEntityToResponse(JobSkill jobSkill) {
        return JobSkillResponse.builder()
                .id(jobSkill.getId())
                .name(jobSkill.getName())
                .category(jobSkill.getCategory())
                .slug(jobSkill.getSlug())
                .active(jobSkill.getActive())
                .build();
    }

    @Override
    public JobSkill mapRequestToEntity(JobSkill jobSkill, JobSkillRequest req) {
        jobSkill.setName(req.name());
        jobSkill.setCategory(req.category());
        return jobSkill;
    }

    @Override
    public List<JobSkillResponse> mapListEntityToListResponse(List<JobSkill> m) {
        if (!m.isEmpty()){
            return m.stream()
                    .map(this::mapEntityToResponse)
                    .toList();
        }
        return List.of();
    }

    @Override
    public PageResponse<JobSkillResponse> mapPageEntityToPageResponse(Page<JobSkill> m) {
        return null;
    }
}

package com.johanwork.job.mapper;

import com.johanwork.job.dto.request.JobTagRequest;
import com.johanwork.job.dto.response.JobTagResponse;
import com.johanwork.job.dto.response.PageResponse;
import com.johanwork.job.model.JobTag;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobTagMapper implements GenericResponseMapper<JobTag, JobTagRequest, JobTagResponse> {

    @Override
    public JobTagResponse mapEntityToResponse(JobTag jobTag) {
        return JobTagResponse.builder()
                .id(jobTag.getId())
                .name(jobTag.getName())
                .slug(jobTag.getSlug())
                .active(jobTag.isActive())
                .build();
    }

    @Override
    public JobTag mapRequestToEntity(JobTag jobTag, JobTagRequest jobTagRequest) {
        jobTag.setName(jobTagRequest.name());
        return jobTag;
    }

    @Override
    public List<JobTagResponse> mapListEntityToListResponse(List<JobTag> m) {
        if (!m.isEmpty()){
            return m.stream()
                    .map(this::mapEntityToResponse)
                    .toList();
        }
        return List.of();
    }

    @Override
    public PageResponse<JobTagResponse> mapPageEntityToPageResponse(Page<JobTag> m) {
        return null;
    }
}

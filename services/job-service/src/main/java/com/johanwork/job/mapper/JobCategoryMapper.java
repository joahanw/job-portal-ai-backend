package com.johanwork.job.mapper;

import com.johanwork.job.dto.JobCategoryRequest;
import com.johanwork.job.dto.response.JobCategoryResponse;
import com.johanwork.job.dto.response.PageResponse;
import com.johanwork.job.model.JobCategory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobCategoryMapper implements GenericResponseMapper<JobCategory, JobCategoryRequest, JobCategoryResponse>{


    @Override
    public JobCategoryResponse mapEntityToResponse(JobCategory jobCategory) {
        return JobCategoryResponse.builder()
                .id(jobCategory.getId())
                .name(jobCategory.getName())
                .slug(jobCategory.getSlug())
                .description(jobCategory.getDescription())
                .iconUrl(jobCategory.getIconUrl())
                .active(jobCategory.getActive())
                .parentId(jobCategory.getParent()!=null ? jobCategory.getParent().getId() : null)
                .parentName(jobCategory.getParent()!=null ? jobCategory.getParent().getName() : null)
                .subCategories(jobCategory.getChildren()!=null
                        ? mapToSubCategories(jobCategory.getChildren())
                        : null)
                .createdAt(jobCategory.getCreatedAt())
                .build();
    }

    @Override
    public JobCategory mapRequestToEntity(JobCategory jobCategory, JobCategoryRequest req) {
        jobCategory.setName(req.name());
        jobCategory.setDescription(req.description());
        jobCategory.setIconUrl(req.iconUrl());
        return jobCategory;
    }

    @Override
    public List<JobCategoryResponse> mapListEntityToListResponse(List<JobCategory> m) {
        if (!m.isEmpty()){
            return m.stream()
                    .map(this::mapEntityToResponse)
                    .toList();
        }
        return List.of();
    }

    @Override
    public PageResponse<JobCategoryResponse> mapPageEntityToPageResponse(Page<JobCategory> m) {
        return null;
    }

    private List<JobCategoryResponse> mapToSubCategories(List<JobCategory> children) {
        if (children.isEmpty()){
            return List.of();
        }
        return children.stream()
                .map(this::mapEntityToResponse)
                .toList();
    }
}

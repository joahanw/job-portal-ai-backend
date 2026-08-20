package com.johanwork.job.service.impl;

import com.johanwork.job.constant.AppConstant;
import com.johanwork.job.dto.request.JobTagRequest;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.JobTagResponse;
import com.johanwork.job.mapper.JobTagMapper;
import com.johanwork.job.model.JobTag;
import com.johanwork.job.repository.JobTagRepository;
import com.johanwork.job.service.IJobTagService;
import com.johanwork.job.service.IJobTagDomainService;
import com.johanwork.job.util.SlugGenerator;
import com.johanwork.job.util.Violations;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobTagService implements IJobTagService {

    private final JobTagRepository repository;
    private final IJobTagDomainService domain;
    private final JobTagMapper mapper;

    @Transactional
    @Override
    public GenericResponse<JobTagResponse> createTag(JobTagRequest req) {
        new Violations()
                .check(repository.existsByName(req.name()),
                        "name", "Name is already exists")
                .throwIfAny();
        String slug = SlugGenerator.generate(req.name(), repository::existsBySlug);
        JobTag tag = JobTag.builder()
                .name(req.name())
                .slug(slug)
                .build();
        return mapper.mapToGenericResponse(
                repository.save(tag),
                String.format(AppConstant.Success.CREATED, "Job Tag")
        );
    }

    @Override
    public GenericResponse<List<JobTagResponse>> getAllTags() {
        return mapper.mapToListGenericResponse(
                repository.findByActiveTrue(),
                String.format(AppConstant.Success.FETCHED, "Job Tags")
        );
    }

    @Override
    public GenericResponse<JobTagResponse> getTagById(Long id) {
        return mapper.mapToGenericResponse(
                domain.getById(id),
                String.format(AppConstant.Success.FETCHED, "Job Tag")
        );
    }

    @Transactional
    @Override
    public GenericResponse<JobTagResponse> updateTag(Long id, JobTagRequest req) {
        JobTag tag = domain.getById(id);
        new Violations()
                .check(!tag.getName().equals(req.name()) &&
                        repository.existsByName(req.name()),
                        "name", "Name is already exists")
                .throwIfAny();
        tag = mapper.mapRequestToEntity(tag, req);
        return mapper.mapToGenericResponse(
                tag, String.format(AppConstant.Success.UPDATED, "Job Tag")
        ) ;
    }

    @Transactional
    @Override
    public GenericResponse<Void> deleteTag(Long id) {
        JobTag tag = domain.getById(id);
        tag.setActive(false);
        return mapper.mapToGenericResponse(
                String.format(AppConstant.Success.DELETED, "Job Tag")
        );
    }

}

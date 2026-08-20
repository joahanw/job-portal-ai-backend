package com.johanwork.job.service.impl;

import com.johanwork.job.constant.AppConstant;
import com.johanwork.job.dto.JobCategoryRequest;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.JobCategoryResponse;
import com.johanwork.job.exception.CustomException;
import com.johanwork.job.mapper.JobCategoryMapper;
import com.johanwork.job.model.Job;
import com.johanwork.job.model.JobCategory;
import com.johanwork.job.repository.JobCategoryRepository;
import com.johanwork.job.service.IJobCategoryDomainService;
import com.johanwork.job.service.IJobCategoryService;
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
public class JobCategoryService implements IJobCategoryService {

    private final JobCategoryRepository jobCategoryRepository;
    private final IJobCategoryDomainService domain;
    private final JobCategoryMapper mapper;

    @Transactional
    @Override
    public GenericResponse<JobCategoryResponse> createCategory(JobCategoryRequest req) {

        new Violations()
                .check(jobCategoryRepository.existsByName(req.name()),
                        "name", "Category name already exists, choose different name")
                .throwIfAny();

        JobCategory parent = null;
        if (null != req.parentId()){
            parent = domain.getById(req.parentId());
        }
        String slug = SlugGenerator.generate(req.name(), jobCategoryRepository::existsBySlug);

        JobCategory jobCategory = mapper.mapRequestToEntity(new JobCategory(), req);
        jobCategory.setSlug(slug);
        jobCategory.setParent(parent);
        return mapper.mapToGenericResponse(
                jobCategoryRepository.save(jobCategory),
                String.format(AppConstant.Success.CREATED, "Job Category")
        );
    }

    @Override
    public GenericResponse<List<JobCategoryResponse>> getAllCategories() {
        return mapper.mapToListGenericResponse(
                jobCategoryRepository.findByActiveTrue(),
                String.format(AppConstant.Success.FETCHED, "Job Categories")
        );
    }

    @Override
    public GenericResponse<JobCategoryResponse> getCategoryById(Long id) {
        return mapper.mapToGenericResponse(
                domain.getById(id),
                String.format(AppConstant.Success.FETCHED, "Job Category")
        );
    }

    @Transactional
    @Override
    public GenericResponse<JobCategoryResponse> updateCategory(Long id, JobCategoryRequest req) {
        JobCategory jobCategory = domain.getById(id);
        Violations violations = new Violations()
                .check(!jobCategory.getName().equals(req.name()) &&
                        jobCategoryRepository.existsByName(req.name()),
                        "name", "Category name already exists, choose different name");

        JobCategory parent = null;
        if (null != req.parentId()){
           violations.check(req.parentId().equals(id),
                  "parentId", "A Category cannot be its own parent");
            if(!req.parentId().equals(id)){
                parent=domain.getById(req.parentId());
            }
        }

        violations.throwIfAny();

        jobCategory = mapper.mapRequestToEntity(jobCategory, req);
        jobCategory.setParent(parent);
        return mapper.mapToGenericResponse(
                jobCategory,
                String.format(AppConstant.Success.UPDATED, "Job Category")
        );
    }

    @Transactional
    @Override
    public GenericResponse<Void> deleteCategory(Long id) {
        JobCategory category = domain.getById(id);
        jobCategoryRepository.delete(category);
        return mapper.mapToGenericResponse(
                String.format(AppConstant.Success.DELETED, "Job Category")
        );
    }

}

package com.johanwork.job.service;

import com.johanwork.job.dto.JobCategoryRequest;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.JobCategoryResponse;
import com.johanwork.job.model.JobCategory;

import java.util.List;

public interface IJobCategoryService {

    GenericResponse<JobCategoryResponse> createCategory(JobCategoryRequest req);

    GenericResponse<List<JobCategoryResponse>> getAllCategories();

    GenericResponse<JobCategoryResponse> getCategoryById(Long id);

    GenericResponse<JobCategoryResponse> updateCategory(Long id, JobCategoryRequest req);

    GenericResponse<Void> deleteCategory(Long id);

}

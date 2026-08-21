package com.johanwork.job.service.impl;

import com.johanwork.job.client.CompanyClient;
import com.johanwork.job.constant.AppConstant;
import com.johanwork.job.domain.JobStatus;
import com.johanwork.job.dto.JobSearchRequest;
import com.johanwork.job.dto.request.JobRequest;
import com.johanwork.job.dto.response.CompanyResponse;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.JobResponse;
import com.johanwork.job.dto.response.PageResponse;
import com.johanwork.job.exception.CustomException;
import com.johanwork.job.mapper.JobMapper;
import com.johanwork.job.model.Job;
import com.johanwork.job.model.JobCategory;
import com.johanwork.job.model.JobSkill;
import com.johanwork.job.model.JobTag;
import com.johanwork.job.repository.JobRepository;
import com.johanwork.job.service.*;
import com.johanwork.job.specification.JobSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobService implements IJobService {

    private final JobRepository jobRepository;
    private final JobMapper jobMapper;
    private final IJobDomainService domain;
    private final IJobCategoryDomainService jobCategoryDomain;
    private final IJobSkillDomainService jobSkillDomain;
    private final IJobTagDomainService jobTagDomain;
    private final CompanyClient companyClient;

    @Override
    public GenericResponse<PageResponse<JobResponse>> getAllJobs(int pageNumber, int pageSize,
                                                                 String sortBy, String sortDirection,
                                                                 JobSearchRequest searchRequest) {
        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(Sort.Direction.DESC, sortBy)
                : Sort.by(Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Job> jobs = jobRepository.findAll(JobSpecification.filter(searchRequest), pageable);
        PageResponse<JobResponse> response = new PageResponse<>(
                jobs.map(data -> jobMapper.mapEntityToResponse(data, getCompany(data.getCompanyId()))).getContent(),
                jobs.getNumber(),
                jobs.getSize(),
                jobs.getTotalElements(),
                jobs.getTotalPages(),
                jobs.hasNext(),
                jobs.hasPrevious()
        );
        return new GenericResponse<>(response,
                String.format(AppConstant.Success.FETCHED, "Jobs"));
    }

    @Override
    public GenericResponse<List<JobResponse>> getJobByCompany(Long companyId) {
        return jobMapper.mapToListGenericResponse(
                domain.getByCompany(companyId),
                getCompany(companyId),
                String.format(AppConstant.Success.FETCHED, "Job")
        );
    }

    @Override
    public GenericResponse<JobResponse> getJobById(Long id) {
        Job job = domain.getById(id);
        return jobMapper.mapToGenericResponse(
                job, getCompany(job.getCompanyId()),
                String.format(AppConstant.Success.FETCHED, "Job")
        );
    }

    @Transactional
    @Override
    public GenericResponse<JobResponse> createJob(Long employerId, JobRequest req) {
        JobCategory jobCategory = jobCategoryDomain.getById(req.categoryId());
        Set<JobSkill> skills = req.skillIds() != null
                ? jobSkillDomain.getSkills(req.skillIds())
                : Collections.emptySet();
        Set<JobTag> tags = req.tagIds() != null
                ? jobTagDomain.getJobIds(req.tagIds())
                : Collections.emptySet();

        // TODO: Fetch Data company
        CompanyResponse company = companyClient.getCompaniesByOwner(employerId).getData();

        Job job = jobMapper.mapRequestToEntity(new Job(), req);
        job.setEmployerId(employerId);
        job.setCategory(jobCategory);
        job.setSkills(skills);
        job.setTags(tags);
        job.setCompanyId(company.getId());
        job = jobRepository.save(job);

        return jobMapper.mapToGenericResponse(
                job, company,
                String.format(AppConstant.Success.FETCHED, "Jobs")
        );
    }

    @Transactional
    @Override
    public GenericResponse<JobResponse> updateJob(Long id, Long employerId, JobRequest req) {
        Job job = domain.getById(id);
        assertEmployer(job, employerId);

        JobCategory jobCategory = job.getCategory().getId().equals(req.categoryId())
                ? job.getCategory()
                : jobCategoryDomain.getById(req.categoryId());
        Set<JobSkill> skills = req.skillIds() != null
                ? jobSkillDomain.getSkills(req.skillIds())
                : Collections.emptySet();
        Set<JobTag> tags = req.tagIds() != null
                ? jobTagDomain.getJobIds(req.tagIds())
                : Collections.emptySet();

        job = jobMapper.mapRequestToEntity(job, req);
        job.setCategory(jobCategory);
        job.setSkills(skills);
        job.setTags(tags);
        return jobMapper.mapToGenericResponse(
                job, getCompany(job.getCompanyId()),
                String.format(AppConstant.Success.FETCHED, "Jobs")
        );
    }

    @Transactional
    @Override
    public GenericResponse<JobResponse> publishJob(Long id, Long employerId) {
        Job job = domain.getById(id);
        assertEmployer(job, employerId);
        if (job.getStatus() == JobStatus.CLOSED || job.getStatus() == JobStatus.EXPIRED){
            throw new CustomException(HttpStatus.BAD_REQUEST,
                    AppConstant.Error.TITLE_JOB_EXPIRED,
                    AppConstant.Error.MESSAGE_JOB_EXPIRED);
        }

        job.setStatus(JobStatus.OPEN);
        job.setPublishedAt(Instant.now());
        job.setActive(true);
        return jobMapper.mapToGenericResponse(
                job, getCompany(job.getCompanyId()),
                String.format(AppConstant.Success.FETCHED, "Jobs")
        );
    }

    @Transactional
    @Override
    public GenericResponse<JobResponse> closeJob(Long id, Long employerId) {
        Job job = domain.getById(id);
        assertEmployer(job, employerId);
        job.setStatus(JobStatus.CLOSED);
        job.setClosedAt(Instant.now());
        job.setActive(false);
        return jobMapper.mapToGenericResponse(
                job, getCompany(job.getCompanyId()),
                String.format(AppConstant.Success.FETCHED, "Jobs")
        );
    }

    @Transactional
    @Override
    public GenericResponse<Void> deleteJob(Long id, Long employerId) {
        Job job = domain.getById(id);
        assertEmployer(job, employerId);
        jobRepository.delete(job);
        return jobMapper.mapToGenericResponse(
                String.format(AppConstant.Success.DELETED, "Job")
        );
    }

    @Override
    public GenericResponse<PageResponse<JobResponse>> getAllJobAdmin(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(Sort.Direction.DESC, sortBy)
                : Sort.by(Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Job> jobs = jobRepository.findAll(pageable);
        PageResponse<JobResponse> response = new PageResponse<>(
                jobs.map(data -> jobMapper.mapEntityToResponse(data, getCompany(data.getCompanyId()))).getContent(),
                jobs.getNumber(),
                jobs.getSize(),
                jobs.getTotalElements(),
                jobs.getTotalPages(),
                jobs.hasNext(),
                jobs.hasPrevious()
        );
        return new GenericResponse<>(response,
                String.format(AppConstant.Success.FETCHED, "Jobs"));
    }

    private CompanyResponse getCompany(Long companyId){
        return companyClient.getCompanyById(companyId).getData();
    }

    private void assertEmployer(Job job, Long employerId) {
        if (!job.getEmployerId().equals(employerId)){
            throw new CustomException(HttpStatus.FORBIDDEN,
                    AppConstant.Error.TITLE_FORBIDDEN,
                    AppConstant.Error.MESSAGE_FORBIDDEN);
        }
    }
}


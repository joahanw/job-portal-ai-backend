package com.johanwork.job.mapper;

import com.johanwork.job.dto.request.JobRequest;
import com.johanwork.job.dto.response.CompanyResponse;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.JobResponse;
import com.johanwork.job.dto.response.PageResponse;
import com.johanwork.job.model.Job;
import com.johanwork.job.model.embeddable.JobLocation;
import com.johanwork.job.model.embeddable.SalaryRange;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JobMapper {

    private final JobCategoryMapper jobCategoryMapper;
    private final JobSkillMapper jobSkillMapper;
    private final JobTagMapper jobTagMapper;

    public JobResponse mapEntityToResponse(Job job, CompanyResponse company) {
        JobLocation loc = job.getLocation();
        SalaryRange sal = job.getSalaryRange();
        return JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .requirements(job.getRequirements())
                .responsibilities(job.getResponsibilities())
                .benefits(job.getBenefits())
                .employerId(job.getCompanyId())
                .company(company)
                // JobCategory
                .category(jobCategoryMapper.mapEntityToResponse(job.getCategory()))
                // JobSkills
                .skills(job.getSkills().stream()
                        .map(jobSkillMapper::mapEntityToResponse)
                        .collect(Collectors.toSet()))
                // JobTags
                .tags(job.getTags().stream()
                        .map(jobTagMapper::mapEntityToResponse)
                        .collect(Collectors.toSet()))
                // Location
                .address(loc != null ? loc.getAddress() : null)
                .city(loc != null ? loc.getCity() : null)
                .country(loc != null ? loc.getCountry() : null)
                .state(loc != null ? loc.getState() : null)
                .zipCode(loc != null ? loc.getZipCode() : null)
                // Salary
                .minSalary(sal != null ? sal.getMinSalary() : null)
                .maxSalary(sal != null ? sal.getMaxSalary() : null)
                // Classification
                .jobType(job.getType())
                .workMode(job.getWorkMode())
                .experienceLevel(job.getExperienceLevel())
                .status(job.getStatus())
                // Posting details
                .openings(job.getOpenings())
                .applicationDeadline(job.getApplicationDeadline())
                .expiresAt(job.getExpiresAt())
                .active(job.getActive())
                // timestamp
                .createdAt(job.getCreatedAt())
                .updatedAt(job.getUpdatedAt())
                .publishedAt(job.getPublishedAt())
                .closedAt(job.getClosedAt())
                .build();
    }

    public Job mapRequestToEntity(Job job, JobRequest req) {
        job.setTitle(req.title());
        job.setDescription(req.description());
        job.setRequirements(req.requirements());
        job.setResponsibilities(req.responsibilities());
        job.setBenefits(req.benefits());
//        job.setCategory(req.categoryId());
//        category, skills and Tags SKIP!!
        job.setLocation(mapToLocation(req));
        job.setSalaryRange(mapToSalaryRange(req));
        job.setWorkMode(req.workMode());
        job.setExperienceLevel(req.experienceLevel());
        job.setType(req.jobType());
        job.setOpenings(req.openings() != null ? req.openings() : 1);
        job.setApplicationDeadline(req.applicationDeadline());
        job.setExpiresAt(req.expiresAt());
        return job;
    }

    public List<JobResponse> mapListEntityToListResponse(List<Job> m, CompanyResponse company) {
        if (!m.isEmpty()){
            return m.stream()
                    .map(data -> mapEntityToResponse(data, company))
                    .toList();
        }
        return List.of();
    }

    public GenericResponse<JobResponse> mapToGenericResponse(Job job, CompanyResponse company, String message){
        var res = mapEntityToResponse(job, company);
        return new GenericResponse<>(res, message);
    }

    public GenericResponse<Void> mapToGenericResponse(String message){
        return new GenericResponse<>(null, message);
    }

    public GenericResponse<List<JobResponse>> mapToListGenericResponse(List<Job> m, CompanyResponse company, String message){
        var res = mapListEntityToListResponse(m, company);
        return new GenericResponse<>(res, message);
    }

    private JobLocation mapToLocation(JobRequest req) {
        return new JobLocation(
               req.address(),
               req.city(),
                req.country(),
                req.state(),
                req.zipCode()
        );
    }

    private SalaryRange mapToSalaryRange(JobRequest req){
        return new SalaryRange(
                req.minSalary(),
                req.maxSalary()
        );
    }


}

package com.johanwork.job.service.impl;

import com.johanwork.job.client.CompanyClient;
import com.johanwork.job.client.JobClient;
import com.johanwork.job.client.ResumeClient;
import com.johanwork.job.client.UserClient;
import com.johanwork.job.constant.AppConstant;
import com.johanwork.job.domain.ApplicationStatus;
import com.johanwork.job.dto.ApplicationRequest;
import com.johanwork.job.dto.CompanyApplicationFilterRequest;
import com.johanwork.job.dto.response.*;
import com.johanwork.job.exception.CustomException;
import com.johanwork.job.mapper.ApplicationMapper;
import com.johanwork.job.model.Application;
import com.johanwork.job.model.ApplicationNote;
import com.johanwork.job.repository.ApplicationNoteRepository;
import com.johanwork.job.repository.ApplicationRepository;
import com.johanwork.job.service.IApplicationDomainService;
import com.johanwork.job.service.IApplicationService;
import com.johanwork.job.specification.ApplicationSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationService implements IApplicationService {

    private final IApplicationDomainService domain;
    private final ApplicationRepository repository;
    private final ApplicationMapper mapper;
    private final ApplicationNoteRepository applicationNoteRepository;
    private final JobClient jobClient;
    private final ResumeClient resumeClient;
    private final CompanyClient companyClient;
    private final UserClient userClient;

    @Transactional
    @Override
    public GenericResponse<ApplicationResponse> createApplication(Long candidateId, ApplicationRequest req) {
        if (repository.existsByCandidateIdAndJobId(candidateId, req.jobId())){
            throw new CustomException(HttpStatus.BAD_REQUEST,
                    AppConstant.Error.TITLE_APPLICATION_ALREADY_EXISTS,
                    AppConstant.Error.MESSAGE_APPLICATION_ALREADY_EXISTS);
        }

        // Fetch JOB
        JobResponse job = jobClient.getJobById(req.jobId()).getData();
        Long companyId = job.getCompany().getId();
        Long employerId = job.getEmployerId();

        // Fetch Resume
        ResumeResponse resume = resumeClient.getResumeById(req.resumeId(), candidateId).getData();

        Application application = mapper.mapRequestToEntity(new Application(), candidateId,
                companyId, employerId, req);
        application = repository.save(application);

        // TODO: AI Screening runs in a background thread, no callback needed

        return mapper.mapToGenericResponse(
                buildFullResponse(application),
                String.format(AppConstant.Success.CREATED, "Application"));
    }

    @Override
    public GenericResponse<ApplicationResponse> getApplicationById(Long id) {
        return mapper.mapToGenericResponse(
                buildFullResponse(domain.getById(id)),
                String.format(AppConstant.Success.FETCHED, "Application"));
    }

    @Override
    public GenericResponse<List<ApplicationResponse>> getMyApplication(Long candidateId) {
        return mapper.mapToListGenericResponse(
                repository.findByCandidateId(candidateId)
                        .stream().map(this::buildFullResponse)
                        .toList(),
                String.format(AppConstant.Success.FETCHED, "Applications"));
    }

    @Override
    public GenericResponse<List<ApplicationResponse>> getApplicationForJob(Long jobId) {
        return mapper.mapToListGenericResponse(
                repository.findByJobId(jobId)
                        .stream().map(this::buildFullResponse)
                        .toList(),
                String.format(AppConstant.Success.FETCHED, "Applications"));
    }

    @Override
    public GenericResponse<List<ApplicationResponse>> getApplicationForCompany(Long userId,
                                                                               CompanyApplicationFilterRequest filter) {
        // Fetch Company base on UserId
        CompanyResponse company = companyClient.getCompanyById(userId).getData();
        Sort sort = buildSort(filter.sortBy());
        return mapper.mapToListGenericResponse(
                repository.findAll(ApplicationSpecification.filter(
                        company.getId(),
                        filter.jobId(),
                        filter.status(),
                        filter.isStarred(),
                        filter.aiShortListStatus(),
                        filter.aiScore()
                        ), sort)
                        .stream().map(this::buildFullResponse)
                        .toList(),
                String.format(AppConstant.Success.FETCHED, "Applications"));
    }

    @Transactional
    @Override
    public GenericResponse<ApplicationResponse> updateStatus(Long id, Long employerId, ApplicationStatus status) {
        Application application = domain.getById(id);
        assertEmployer(application, employerId);
        if (application.getStatus()==ApplicationStatus.WITHDRAWN){
            throw new CustomException(HttpStatus.BAD_REQUEST,
                    AppConstant.Error.TITLE_ALREADY_WITHDRAWN,
                    AppConstant.Error.MESSAGE_ALREADY_WITHDRAWN);
        }
        application.setStatus(status);
        return mapper.mapToGenericResponse(
                buildFullResponse(application),
                String.format(AppConstant.Success.UPDATED, "Application"));
    }

    @Transactional
    @Override
    public GenericResponse<ApplicationResponse> withdrawnApplication(Long id, Long candidateId, String reason) {
        Application application = domain.getById(id);
        assertCandidate(application, candidateId);
        application.setStatus(ApplicationStatus.WITHDRAWN);
        application.setWithdrawnReason(reason);
        application.setWithdrawnAt(Instant.now());
        return mapper.mapToGenericResponse(
                buildFullResponse(application),
                String.format(AppConstant.Success.UPDATED, "Application"));
    }

    @Transactional
    @Override
    public GenericResponse<ApplicationResponse> toggleStart(Long id, Long employerId) {
        Application application = domain.getById(id);
        assertEmployer(application, employerId);
        application.setIsStarred(!application.getIsStarred());
        return mapper.mapToGenericResponse(
                buildFullResponse(application),
                String.format(AppConstant.Success.UPDATED, "Application"));
    }

    @Transactional
    @Override
    public GenericResponse<Void> deleteApplication(Long id, Long candidateId) {
        Application application = domain.getById(id);
        assertCandidate(application, candidateId);
        repository.delete(application);
        return mapper.mapToGenericResponse(String.format(AppConstant.Success.DELETED, "Application"));
    }

    private ApplicationResponse buildFullResponse(Application application){
        // Fetch Job
        JobResponse job  = jobClient.getJobById(application.getJobId()).getData();

        // Fetch Company
        CompanyResponse company = companyClient.getCompanyById(application.getCompanyId()).getData();

        // Fetch Candidate
        UserResponse candidate = userClient.getUserById(application.getCandidateId()).getData();

        List<ApplicationNote> notes = applicationNoteRepository.findByApplication_Id(application.getId());
        return mapper.mapEntityToResponse(application, notes, job, company, candidate);
    }

    private Sort buildSort(String sortBy){
        if ("AI_SCORE_DESC".equals(sortBy)){
            return Sort.by(Sort.Order.desc("aiScore")
                    .with(Sort.NullHandling.NULLS_LAST));
        }else if ("AI_SCORE_ASC".equals(sortBy)){
            return Sort.by(Sort.Order.desc("aiScore")
                    .with(Sort.NullHandling.NULLS_LAST));
        }else {
            return Sort.by(Sort.Order.desc("appliedAt"));
        }
    }

    private void assertEmployer(Application application, Long employerId){
        if (!application.getEmployerId().equals(employerId)){
            throw new CustomException(HttpStatus.FORBIDDEN,
                    AppConstant.Error.TITLE_FORBIDDEN,
                    AppConstant.Error.MESSAGE_FORBIDDEN);
        }
    }

    private void assertCandidate(Application application, Long candidateId){
        if (!application.getCandidateId().equals(candidateId)){
            throw new CustomException(HttpStatus.FORBIDDEN,
                    AppConstant.Error.TITLE_FORBIDDEN,
                    AppConstant.Error.MESSAGE_FORBIDDEN);
        }
    }


}

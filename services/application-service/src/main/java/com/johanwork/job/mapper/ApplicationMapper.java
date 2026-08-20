package com.johanwork.job.mapper;

import com.johanwork.job.domain.ApplicationStatus;
import com.johanwork.job.dto.ApplicationRequest;
import com.johanwork.job.dto.response.*;
import com.johanwork.job.model.Application;
import com.johanwork.job.model.ApplicationNote;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationMapper{

    public ApplicationResponse mapEntityToResponse(Application application,
                                                   List<ApplicationNote> notes,
                                                   JobResponse job,
                                                   CompanyResponse company,
                                                   UserResponse candidate
//                                                   ApplicationScreening screening
    ) {
        return ApplicationResponse.builder()
                .id(application.getId())
                .candidate(candidate)
                .employerId(application.getEmployerId())
                .job(job)
                .company(company)
                .status(application.getStatus())

                .resumeId(application.getResumeId())
                .coverLetter(application.getCoverLetter())

                .expectedSalary(application.getExpectedSalary())

                .availableFrom(application.getAvailableFrom())
                .isStarred(application.getIsStarred())
                .notes(notes.stream().map(this::mapEntityToResponse).toList())
                .withdrawnAt(application.getWithdrawnAt())
                .withdrawnReason(application.getWithdrawnReason())
                .appliedAt(application.getAppliedAt())
                .updatedAt(application.getUpdatedAt())
//                .screening(screening)
                .build();
    }

    public Application mapRequestToEntity(Application application,
                                          Long candidateId,
                                          Long companyId,
                                          Long employerId,
                                          ApplicationRequest req) {
        application.setJobId(req.jobId());
        application.setResumeId(req.resumeId());
        application.setCandidateId(candidateId);
        application.setCompanyId(companyId);
        application.setEmployerId(employerId);
        application.setCoverLetter(req.coverLetter());
        application.setExpectedSalary(req.expectedSalary());
        application.setAvailableFrom(req.availableFrom());
        return application;
    }

    public ApplicationNoteResponse mapEntityToResponse(ApplicationNote note){
        return ApplicationNoteResponse.builder()
                .id(note.getId())
                .addByUserId(note.getAddByUserId())
                .content(note.getContent())
                .createdAt(note.getCreatedAt())
                .build();
    }

    public GenericResponse<ApplicationResponse> mapToGenericResponse(ApplicationResponse res, String message) {
        return new GenericResponse<>(res, message);
    }

    public GenericResponse<ApplicationNoteResponse> mapToGenericResponse(ApplicationNote note, String message) {
        var res = mapEntityToResponse(note);
        return new GenericResponse<>(res, message);
    }

    public GenericResponse<Void> mapToGenericResponse(String message) {
        return new GenericResponse<>(null, message);
    }

    public GenericResponse<List<ApplicationResponse>> mapToListGenericResponse(List<ApplicationResponse> res, String message) {
        return new GenericResponse<>(res, message);
    }

    public GenericResponse<List<ApplicationNoteResponse>> mapToListNoteGenericResponse(List<ApplicationNoteResponse> res, String message) {
        return new GenericResponse<>(res, message);
    }
}

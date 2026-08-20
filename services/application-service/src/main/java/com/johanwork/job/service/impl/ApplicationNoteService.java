package com.johanwork.job.service.impl;

import com.johanwork.job.constant.AppConstant;
import com.johanwork.job.dto.AddApplicationNoteRequest;
import com.johanwork.job.dto.response.ApplicationNoteResponse;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.exception.CustomException;
import com.johanwork.job.mapper.ApplicationMapper;
import com.johanwork.job.model.Application;
import com.johanwork.job.model.ApplicationNote;
import com.johanwork.job.repository.ApplicationNoteRepository;
import com.johanwork.job.service.IApplicationDomainService;
import com.johanwork.job.service.IApplicationNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationNoteService implements IApplicationNoteService {

    private final ApplicationNoteRepository repository;
    private final IApplicationDomainService applicationDomainService;
    private final ApplicationMapper mapper;

    @Override
    public GenericResponse<ApplicationNoteResponse> addNote(Long applicationId, Long employerId, AddApplicationNoteRequest request) {
        Application application = applicationDomainService.getById(applicationId);
        assertEmployer(application, employerId);
        ApplicationNote applicationNote = ApplicationNote.builder()
                .application(application)
                .addByUserId(employerId)
                .content(request.content())
                .build();
        return mapper.mapToGenericResponse(
                repository.save(applicationNote),
                String.format(AppConstant.Success.CREATED, "Application Notes")
        );
    }

    @Override
    public GenericResponse<List<ApplicationNoteResponse>> getNotesByApplication(Long applicationId) {
        return mapper.mapToListNoteGenericResponse(
                repository.findByApplication_Id(applicationId)
                        .stream().map(mapper::mapEntityToResponse)
                        .toList(),
                String.format(AppConstant.Success.FETCHED, "Application Notes")
        );
    }

    @Override
    public GenericResponse<Void> deleteNote(Long applicationId, Long notedId, Long employerId) {
        Application application = applicationDomainService.getById(applicationId);
        assertEmployer(application, employerId);
        ApplicationNote note = repository.findById(notedId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND,
                        AppConstant.Error.TITLE_NOT_FOUND,
                        AppConstant.Error.MESSAGE_NOT_FOUND));
        repository.delete(note);
        return mapper.mapToGenericResponse(
                String.format(AppConstant.Success.DELETED, "Application Notes"));
    }

    private void assertEmployer(Application application, Long employerId){
        if (!application.getEmployerId().equals(employerId)){
            throw new CustomException(HttpStatus.FORBIDDEN,
                    AppConstant.Error.TITLE_FORBIDDEN,
                    AppConstant.Error.MESSAGE_FORBIDDEN);
        }
    }
}

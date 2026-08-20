package com.johanwork.job.service;

import com.johanwork.job.dto.AddApplicationNoteRequest;
import com.johanwork.job.dto.response.ApplicationNoteResponse;
import com.johanwork.job.dto.response.GenericResponse;

import java.util.List;

public interface IApplicationNoteService {

    GenericResponse<ApplicationNoteResponse> addNote(Long applicationId, Long employerId,
                                                    AddApplicationNoteRequest request);

    GenericResponse<List<ApplicationNoteResponse>> getNotesByApplication(Long applicationId);

    GenericResponse<Void> deleteNote(Long applicationId, Long notedId, Long employerId);

}

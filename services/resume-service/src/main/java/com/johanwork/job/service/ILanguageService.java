package com.johanwork.job.service;

import com.johanwork.job.dto.AddLanguageRequest;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.LanguageResponse;

import java.util.List;

public interface ILanguageService {

    GenericResponse<LanguageResponse> addLanguage(Long resumeId, Long candidateId, AddLanguageRequest req);
    GenericResponse<List<LanguageResponse>> getLanguages(Long resumeId);
    GenericResponse<LanguageResponse> updateLanguage(Long id, Long resumeId, Long candidateId, AddLanguageRequest req);
    GenericResponse<Void> deleteLanguage(Long id, Long resumeId, Long candidateId);

}

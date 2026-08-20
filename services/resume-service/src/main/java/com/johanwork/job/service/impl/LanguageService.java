package com.johanwork.job.service.impl;

import com.johanwork.job.constant.AppConstant;
import com.johanwork.job.dto.AddLanguageRequest;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.LanguageResponse;
import com.johanwork.job.mapper.LanguageMapper;
import com.johanwork.job.model.Language;
import com.johanwork.job.model.Resume;
import com.johanwork.job.repository.LanguageRepository;
import com.johanwork.job.service.ILanguageDomainService;
import com.johanwork.job.service.ILanguageService;
import com.johanwork.job.service.IResumeDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.johanwork.job.util.ResumeUtil.assertOwner;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LanguageService implements ILanguageService {

    private final IResumeDomainService resumeDomainService;
    private final ILanguageDomainService domain;
    private final LanguageRepository repository;
    private final LanguageMapper mapper;

    @Transactional
    @Override
    public GenericResponse<LanguageResponse> addLanguage(Long resumeId, Long candidateId, AddLanguageRequest req) {
        Resume resume = resumeDomainService.getById(resumeId);
        assertOwner(resume, candidateId);
        Language language = mapper.mapRequestToEntity(new Language(), req);
        language.setResume(resume);
        return mapper.mapToGenericResponse(
                repository.save(language),
                String.format(AppConstant.Success.CREATED, "Language")
        );
    }

    @Override
    public GenericResponse<List<LanguageResponse>> getLanguages(Long resumeId) {
        return mapper.mapToListGenericResponse(
                repository.findByResume_IdOrderByDisplayOrderAsc(resumeId),
                String.format(AppConstant.Success.FETCHED, "Languages")
        );
    }

    @Transactional
    @Override
    public GenericResponse<LanguageResponse> updateLanguage(Long id, Long resumeId, Long candidateId, AddLanguageRequest req) {
        Language language = domain.getById(id);
        assertOwner(language.getResume(), candidateId, resumeId);
        language = mapper.mapRequestToEntity(language, req);
        return mapper.mapToGenericResponse(
                language,
                String.format(AppConstant.Success.UPDATED, "Language")
        );
    }

    @Transactional
    @Override
    public GenericResponse<Void> deleteLanguage(Long id, Long resumeId, Long candidateId) {
        Language language = domain.getById(id);
        assertOwner(language.getResume(), candidateId, resumeId);
        repository.delete(language);
        return mapper.mapToGenericResponse(
                String.format(AppConstant.Success.DELETED, "Language")
        );
    }
}

package com.johanwork.job.mapper;

import com.johanwork.job.dto.AddLanguageRequest;
import com.johanwork.job.dto.response.LanguageResponse;
import com.johanwork.job.dto.response.PageResponse;
import com.johanwork.job.model.Language;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LanguageMapper implements GenericResponseMapper<Language, AddLanguageRequest, LanguageResponse> {

    @Override
    public LanguageResponse mapEntityToResponse(Language language) {
        return LanguageResponse.builder()
                .id(language.getId())
                .languageName(language.getLanguageName())
                .proficiency(language.getProficiency())
                .displayOrder(language.getDisplayOrder())
                .build();
    }

    @Override
    public Language mapRequestToEntity(Language language, AddLanguageRequest req) {
        language.setLanguageName(req.languageName());
        language.setProficiency(req.proficiency());
        language.setDisplayOrder(req.displayOrder());
        return language;
    }

    @Override
    public List<LanguageResponse> mapListEntityToListResponse(List<Language> m) {
        if (m.isEmpty()) return List.of();
        return m.stream().map(this::mapEntityToResponse).toList();
    }

    @Override
    public PageResponse<LanguageResponse> mapPageEntityToPageResponse(Page<Language> m) {
        return null;
    }
}

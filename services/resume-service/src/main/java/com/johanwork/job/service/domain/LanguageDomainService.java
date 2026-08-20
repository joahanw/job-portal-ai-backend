package com.johanwork.job.service.domain;

import com.johanwork.job.constant.AppConstant;
import com.johanwork.job.exception.CustomException;
import com.johanwork.job.model.Language;
import com.johanwork.job.repository.LanguageRepository;
import com.johanwork.job.service.ILanguageDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LanguageDomainService implements ILanguageDomainService {

    private final LanguageRepository repository;

    @Override
    public Language getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CustomException(
                        HttpStatus.NOT_FOUND,
                        String.format(AppConstant.Error.TITLE_NOT_FOUND, "Language"),
                        String.format(AppConstant.Error.MESSAGE_NOT_FOUND, "Language", id)
                ));
    }

}

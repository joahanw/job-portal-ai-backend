package com.johanwork.job.service.domain;

import com.johanwork.job.constant.AppConstant;
import com.johanwork.job.exception.CustomException;
import com.johanwork.job.model.Education;
import com.johanwork.job.repository.EducationRepository;
import com.johanwork.job.service.IEducationDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EducationDomainService implements IEducationDomainService {

    private final EducationRepository repository;

    @Override
    public Education getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND,
                        String.format(AppConstant.Error.TITLE_NOT_FOUND, "Education"),
                        String.format(AppConstant.Error.MESSAGE_NOT_FOUND, "Education", id)));
    }
}

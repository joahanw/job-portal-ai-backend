package com.johanwork.job.service.domain;

import com.johanwork.job.constant.AppConstant;
import com.johanwork.job.exception.CustomException;
import com.johanwork.job.model.WorkExperience;
import com.johanwork.job.repository.WorkExperienceRepository;
import com.johanwork.job.service.IWorkExperienceDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkExperienceDomainService implements IWorkExperienceDomainService {

    private final WorkExperienceRepository repository;

    @Override
    public WorkExperience getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND,
                        String.format(AppConstant.Error.TITLE_NOT_FOUND, "Work Experience"),
                        String.format(AppConstant.Error.MESSAGE_NOT_FOUND, "Work Experience", id)));
    }
}

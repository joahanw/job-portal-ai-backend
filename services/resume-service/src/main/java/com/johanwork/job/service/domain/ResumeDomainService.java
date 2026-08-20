package com.johanwork.job.service.domain;

import com.johanwork.job.constant.AppConstant;
import com.johanwork.job.exception.CustomException;
import com.johanwork.job.model.Resume;
import com.johanwork.job.repository.ResumeRepository;
import com.johanwork.job.service.IResumeDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResumeDomainService implements IResumeDomainService {

    private final ResumeRepository repository;

    @Override
    public Resume getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CustomException(
                        HttpStatus.NOT_FOUND,
                        String.format(AppConstant.Error.TITLE_NOT_FOUND, "Resume"),
                        String.format(AppConstant.Error.MESSAGE_NOT_FOUND, "Resume", id)
                ));
    }
}

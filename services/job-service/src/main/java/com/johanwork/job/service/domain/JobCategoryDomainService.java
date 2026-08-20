package com.johanwork.job.service.domain;

import com.johanwork.job.constant.AppConstant;
import com.johanwork.job.exception.CustomException;
import com.johanwork.job.model.JobCategory;
import com.johanwork.job.repository.JobCategoryRepository;
import com.johanwork.job.service.IJobCategoryDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobCategoryDomainService implements IJobCategoryDomainService {

    private final JobCategoryRepository jobCategoryRepository;

    @Override
    public JobCategory getById(Long id) {
        return jobCategoryRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND,
                   String.format( AppConstant.Error.TITLE_NOT_FOUND, "Job Category"),
                    String.format( AppConstant.Error.MESSAGE_NOT_FOUND,"Job Category", id)));
    }
}

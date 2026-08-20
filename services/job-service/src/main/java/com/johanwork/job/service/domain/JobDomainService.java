package com.johanwork.job.service.domain;

import com.johanwork.job.constant.AppConstant;
import com.johanwork.job.exception.CustomException;
import com.johanwork.job.model.Job;
import com.johanwork.job.repository.JobRepository;
import com.johanwork.job.service.IJobDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobDomainService implements IJobDomainService {

    private final JobRepository jobRepository;

    @Override
    public Job getById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() ->new CustomException(HttpStatus.NOT_FOUND,
                        String.format(AppConstant.Error.TITLE_NOT_FOUND, "Job"),
                        String.format(AppConstant.Error.MESSAGE_NOT_FOUND,"Job", id)));
    }

    @Override
    public List<Job> getByCompany(Long companyId) {
        return jobRepository.findByCompanyId(companyId);
    }
}

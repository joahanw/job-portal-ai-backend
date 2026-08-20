package com.johanwork.job.service.domain;

import com.johanwork.job.constant.AppConstant;
import com.johanwork.job.exception.CustomException;
import com.johanwork.job.model.JobTag;
import com.johanwork.job.repository.JobTagRepository;
import com.johanwork.job.service.IJobTagDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobTagDomainService implements IJobTagDomainService {

    private final JobTagRepository repository;

    @Override
    public JobTag getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CustomException(
                        HttpStatus.NOT_FOUND,
                        String.format(AppConstant.Error.TITLE_NOT_FOUND, "Job Tag"),
                        String.format(AppConstant.Error.MESSAGE_NOT_FOUND, "Job Tag", id)
                ));
    }

    @Override
    public Set<JobTag> getJobIds(Set<Long> jobIds) {
        Set<JobTag> tag = new HashSet<>(repository.findAllById(jobIds));
        return tag;
    }


}

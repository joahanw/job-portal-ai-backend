package com.johanwork.job.service.domain;

import com.johanwork.job.constant.AppConstant;
import com.johanwork.job.exception.CustomException;
import com.johanwork.job.model.ResumeSkill;
import com.johanwork.job.repository.ResumeSkillRepository;
import com.johanwork.job.service.IResumeSkillDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResumeSkillDomainService implements IResumeSkillDomainService {

    private final ResumeSkillRepository repository;

    @Override
    public ResumeSkill getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CustomException(
                        HttpStatus.NOT_FOUND,
                        String.format(AppConstant.Error.TITLE_NOT_FOUND, "Resume Skill"),
                        String.format(AppConstant.Error.MESSAGE_NOT_FOUND, "Resume Skill", id)
                ));
    }
}

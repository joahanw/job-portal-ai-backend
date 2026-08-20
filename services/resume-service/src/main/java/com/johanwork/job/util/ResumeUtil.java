package com.johanwork.job.util;

import com.johanwork.job.constant.AppConstant;
import com.johanwork.job.exception.CustomException;
import com.johanwork.job.model.Resume;
import org.springframework.http.HttpStatus;

public class ResumeUtil {

    public static void assertOwner(Resume resume, Long candidateId) {
        if (!resume.getCandidateId().equals(candidateId)){
            throw new CustomException(HttpStatus.FORBIDDEN,
                    AppConstant.Error.TITLE_FORBIDDEN,
                    AppConstant.Error.MESSAGE_FORBIDDEN);
        }
    }

    public static void assertOwner(Resume resume, Long candidateId, Long resumeId) {
        if (!resume.getCandidateId().equals(candidateId) && !resume.getId().equals(resumeId)){
            throw new CustomException(HttpStatus.FORBIDDEN,
                    AppConstant.Error.TITLE_FORBIDDEN,
                    AppConstant.Error.MESSAGE_FORBIDDEN);
        }
    }
}

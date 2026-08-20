package com.johanwork.job.mapper;

import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.PersonalInfoResponse;
import com.johanwork.job.dto.response.ResumeResponse;
import com.johanwork.job.dto.response.WorkExperienceResponse;
import com.johanwork.job.dto.CreateResumeRequest;
import com.johanwork.job.model.*;
import com.johanwork.job.model.embeddable.PersonalInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ResumeMapper{

    private final EducationMapper educationMapper;
    private final WorkExperienceMapper workExperienceMapper;
    private final ResumeSkillMapper skillMapper;
    private final ProjectMapper projectMapper;
    private final LanguageMapper languageMapper;

    public Resume mapCreateResumeRequestToEntity(Resume resume, Long candidateId, CreateResumeRequest req){
        resume.setCandidateId(candidateId);
        resume.setTitle(req.title());
        resume.setTemplate(req.template());
        resume.setVisibility(req.visibility());
        resume.setIsDefault(Boolean.TRUE.equals(req.isDefault()));
        return resume;
    }

    public ResumeResponse mapEntityToResumeResponse(Resume resume){
        return ResumeResponse.builder()
                .id(resume.getId())
                .candidateId(resume.getCandidateId())
                .title(resume.getTitle())
                .template(resume.getTemplate())
                .visibility(resume.getVisibility())
                .isDefault(resume.getIsDefault())
                .personalInfo(resume.getPersonalInfo() != null ? mapToPersonalInfoResponse(resume.getPersonalInfo()): null)
                .summary(resume.getSummary())
                .completionScore(resume.getCompletionScore())
                .createdAt(resume.getCreatedAt())
                .updatedAt(resume.getUpdatedAt())
                .workExperiences(workExperienceMapper.mapListEntityToListResponse(resume.getWorkExperiences()))
                .educations(educationMapper.mapListEntityToListResponse(resume.getEducations()))
                .projects(projectMapper.mapListEntityToListResponse(resume.getProjects()))
                .languages(languageMapper.mapListEntityToListResponse(resume.getLanguages()))
                .skills(skillMapper.mapListEntityToListResponse(resume.getSkills()))
                .build();
    }

    public List<ResumeResponse> mapEntityListToResumeResponseList(List<Resume> resume){
        if (resume.isEmpty()) return List.of();
        return resume.stream().map(this::mapEntityToResumeResponse).toList();
    }

    public PersonalInfo mapToPersonalInfo(PersonalInfo info, PersonalInfoResponse req){
        if (info == null) info = new PersonalInfo();
        if (null != req.getFirstName()) info.setFirstName(req.getFirstName());
        if (null != req.getLastName()) info.setLastName(req.getLastName());
        if (null != req.getHeadline()) info.setHeadline(req.getHeadline());
        if (null != req.getEmail()) info.setEmail(req.getEmail());
        if (null != req.getPhone()) info.setPhone(req.getPhone());
        if (null != req.getCity()) info.setCity(req.getCity());
        if (null != req.getCountry()) info.setCountry(req.getCountry());
        if (null != req.getLinkedinUrl()) info.setLinkedinUrl(req.getLinkedinUrl());
        if (null != req.getGithubUrl()) info.setGithubUrl(req.getGithubUrl());
        if (null != req.getPortfolioUrl()) info.setPortfolioUrl(req.getPortfolioUrl());
        if (null != req.getWebsiteUrl()) info.setWebsiteUrl(req.getWebsiteUrl());
        return info;
    }

    public GenericResponse<ResumeResponse> mapToGenericResponse(Resume resume,
                                                                String message){
        var res = mapEntityToResumeResponse(resume);
        return new GenericResponse<>(res, message);
    }

    public GenericResponse<Void> mapToGenericResponse(String message){
        return new GenericResponse<>(null, message);
    }

    public GenericResponse<List<ResumeResponse>> mapToListGenericResponse(List<Resume> resume, String message){
        var res = mapEntityListToResumeResponseList(resume);
        return new GenericResponse<>(res, message);
    }

    private PersonalInfoResponse mapToPersonalInfoResponse(PersonalInfo personalInfo){
        PersonalInfoResponse res = new PersonalInfoResponse();
        BeanUtils.copyProperties(personalInfo, res);
        return res;
    }

}

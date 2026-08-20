package com.johanwork.job.repository;

import com.johanwork.job.model.Job;
import com.johanwork.job.model.JobSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface JobSkillRepository extends JpaRepository<JobSkill, Long> {
    List<JobSkill> findByActiveTrue();

    boolean existsByName(String name);
    boolean existsBySlug(String slug);
}

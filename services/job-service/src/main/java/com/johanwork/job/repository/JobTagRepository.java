package com.johanwork.job.repository;

import com.johanwork.job.model.JobTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobTagRepository extends JpaRepository<JobTag, Long> {

    List<JobTag> findByActiveTrue();
    boolean existsByName(String name);
    boolean existsBySlug(String slug);

}

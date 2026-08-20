package com.johanwork.job.repository;

import com.johanwork.job.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LanguageRepository extends JpaRepository<Language, Long> {
    List<Language> findByResume_IdOrderByDisplayOrderAsc(Long resumeId);
}

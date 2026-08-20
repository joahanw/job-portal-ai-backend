package com.johanwork.job.repository;

import com.johanwork.job.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findAll(Specification<User> specification, Pageable pageable);
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailOrPhone(String email, String phone);
}

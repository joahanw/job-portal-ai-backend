package com.johanwork.job.specification;

import com.johanwork.job.domain.JobStatus;
import com.johanwork.job.dto.JobSearchRequest;
import com.johanwork.job.model.Job;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class JobSpecification {
    private JobSpecification(){}

    public static Specification<Job> filter(JobSearchRequest req) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.isTrue(root.get("active")));

            JobStatus status = req.status() != null ? req.status() : JobStatus.OPEN;
            predicates.add(cb.equal(root.get("status"), status));

            if (null != req.jobType()){
                predicates.add(cb.equal(root.get("type"), req.jobType()));
            }

            if (null != req.workMode()){
                predicates.add(cb.equal(root.get("workMode"), req.workMode()));
            }

            if (null != req.experienceLevel()){
                predicates.add(cb.equal(root.get("experienceLevel"), req.experienceLevel()));
            }

            if (null != req.categoryId()){
                predicates.add(cb.equal(root.get("category").get("id"), req.categoryId()));
            }

            if (null != req.location() && !req.location().isBlank()){
                String keyword = "%" + req.location().toLowerCase() + "%";
                predicates.add(
                        cb.or(
                                cb.like(cb.lower(root.get("location").get("city")), keyword),
                                cb.like(cb.lower(root.get("location").get("state")), keyword),
                                cb.like(cb.lower(root.get("location").get("country")), keyword)
                        )
                );
            }

            if (null != req.minSalary()) {
                predicates.add(cb.greaterThanOrEqualTo(
                        root.get("salaryRange").get("maxSalary"), req.minSalary()));
            }

            if (null != req.maxSalary()) {
                predicates.add(cb.lessThanOrEqualTo(
                        root.get("salaryRange").get("minSalary"), req.maxSalary()));
            }

            if (null != req.minOpenings()){
                predicates.add(cb.greaterThanOrEqualTo(
                        root.get("openings"), req.minOpenings()));
            }

            if (null != req.maxOpenings()){
                predicates.add(cb.lessThanOrEqualTo(
                        root.get("openings"), req.maxOpenings()));
            }

            // TODO: filter for tag, skills,

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

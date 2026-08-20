package com.johanwork.job.specification;

import com.johanwork.job.domain.AiShortListStatus;
import com.johanwork.job.domain.ApplicationStatus;
import com.johanwork.job.model.Application;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ApplicationSpecification {
    private ApplicationSpecification(){}

    public static Specification<Application> filter(
           Long companyId,
           Long jobId,
           ApplicationStatus status,
           boolean isStarred,
           AiShortListStatus aiShortListStatus,
           Integer aiScore
    ){
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("companyId"), companyId));

            if (null != jobId) predicates.add(cb.equal(root.get("jobId"),jobId));
            if (null != status) predicates.add(cb.equal(root.get("status"), status));
            if (isStarred) predicates.add(cb.equal(root.get("isStarred"), isStarred));
            if (null != aiShortListStatus) predicates.add(cb.equal(root.get("aiShortListStatus"), aiShortListStatus));
            if (null != aiScore) predicates.add(cb.greaterThanOrEqualTo(root.get("aiScore"), aiScore));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

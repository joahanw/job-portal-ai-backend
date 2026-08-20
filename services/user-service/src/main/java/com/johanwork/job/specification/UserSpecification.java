package com.johanwork.job.specification;

import com.johanwork.job.model.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {
    private UserSpecification(){}

    public static Specification<User> filter(
            String search
    ){
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(null != search && !search.isBlank()){
                String keyword = "%" + search.toLowerCase() + "%";
                predicates.add(
                        cb.or(
                                cb.like(cb.lower(root.get("fullName")), keyword),
                                cb.like(cb.lower(root.get("phone")), keyword),
                                cb.like(cb.lower(root.get("email")), keyword)
                        )
                );
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

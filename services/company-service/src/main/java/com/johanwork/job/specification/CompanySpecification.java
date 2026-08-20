package com.johanwork.job.specification;

import com.johanwork.job.domain.CompanyStatus;
import com.johanwork.job.domain.CompanyType;
import com.johanwork.job.domain.IndustryType;
import com.johanwork.job.model.Company;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CompanySpecification {
    private CompanySpecification() {}

    public static Specification<Company> filter(
            String search,
            CompanyType companyType,
            IndustryType industryType,
            CompanyStatus companyStatus
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(null != search && !search.isBlank()){
                String keyword = "%" + search.toLowerCase() + "%";
                predicates.add(
                        cb.or(
                                cb.like(cb.lower(root.get("name")), keyword),
                                cb.like(cb.lower(root.get("slug")), keyword),
                                cb.like(cb.lower(root.get("email")), keyword),
                                cb.like(cb.lower(root.get("phone")), keyword)
                        )
                );
            }

            if (null != companyType){
                predicates.add(cb.equal(root.get("companyType"), companyType));
            }

            if (null != industryType){
                predicates.add(cb.equal(root.get("industryType"), industryType));
            }

            if (null != companyStatus){
                predicates.add(cb.equal(root.get("status"), companyStatus));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

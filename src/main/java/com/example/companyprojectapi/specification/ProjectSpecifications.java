package com.example.companyprojectapi.specification;

import com.example.companyprojectapi.entity.Project;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public final class ProjectSpecifications {

    private ProjectSpecifications() {
    }

    public static Specification<Project> hasEmployee(Long employeeId) {
        return (root, query, cb) -> {
            if (Long.class != query.getResultType()) {
                root.fetch("company", JoinType.LEFT);
                root.fetch("employees", JoinType.LEFT);
                query.distinct(true);
            }
            return cb.equal(root.join("employees", JoinType.INNER).get("id"), employeeId);
        };
    }
}

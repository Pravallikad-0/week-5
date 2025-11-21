package com.example.companyprojectapi.specification;

import com.example.companyprojectapi.entity.Employee;
import com.example.companyprojectapi.entity.Project;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public final class EmployeeSpecifications {

    private EmployeeSpecifications() {
    }

    public static Specification<Employee> hasProject(Long projectId) {
        return (root, query, cb) -> {
            if (Long.class != query.getResultType()) {
                root.fetch("address", JoinType.LEFT);
                root.fetch("company", JoinType.LEFT);
                query.distinct(true);
            }
            Join<Employee, Project> projectJoin = root.join("projects", JoinType.INNER);
            return cb.equal(projectJoin.get("id"), projectId);
        };
    }

    public static Specification<Employee> belongsToCompany(Long companyId) {
        return (root, query, cb) -> cb.equal(root.get("company").get("id"), companyId);
    }
}

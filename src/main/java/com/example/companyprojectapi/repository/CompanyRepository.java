package com.example.companyprojectapi.repository;

import com.example.companyprojectapi.entity.Company;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long>, JpaSpecificationExecutor<Company> {

    @EntityGraph(attributePaths = {
            "projects",
            "projects.employees",
            "projects.employees.address"
    })
    Optional<Company> findByNameIgnoreCase(String name);
}

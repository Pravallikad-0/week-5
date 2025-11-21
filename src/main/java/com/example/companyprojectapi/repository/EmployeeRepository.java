package com.example.companyprojectapi.repository;

import com.example.companyprojectapi.entity.Employee;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    @EntityGraph(attributePaths = {"address", "projects", "company"})
    Optional<Employee> findDetailedById(Long id);
}

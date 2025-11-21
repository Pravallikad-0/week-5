package com.example.companyprojectapi.repository;

import com.example.companyprojectapi.entity.Project;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {

    @EntityGraph(attributePaths = {"employees", "employees.address", "company"})
    Optional<Project> findWithEmployeesById(Long id);

    @Query("select distinct p from Project p join p.employees e where e.id = :employeeId")
    List<Project> findDistinctByEmployees_Id(@Param("employeeId") Long employeeId);
}

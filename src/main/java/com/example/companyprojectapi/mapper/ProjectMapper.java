package com.example.companyprojectapi.mapper;

import com.example.companyprojectapi.dto.EmployeeSummary;
import com.example.companyprojectapi.dto.ProjectSummary;
import com.example.companyprojectapi.dto.ProjectWithEmployeesResponse;
import com.example.companyprojectapi.entity.Project;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class ProjectMapper {

    private ProjectMapper() {
    }

    public static ProjectSummary toSummary(Project project) {
        return new ProjectSummary(project.getId(), project.getName(), project.getDescription());
    }

    public static ProjectWithEmployeesResponse toProjectWithEmployees(Project project) {
        List<EmployeeSummary> employees = project.getEmployees()
                .stream()
                .sorted(Comparator.comparing(e -> (e.getLastName() + e.getFirstName())))
                .map(EmployeeMapper::toSummary)
                .collect(Collectors.toList());
        return new ProjectWithEmployeesResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                employees
        );
    }
}

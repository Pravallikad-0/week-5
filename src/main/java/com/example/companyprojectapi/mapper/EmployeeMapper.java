package com.example.companyprojectapi.mapper;

import com.example.companyprojectapi.dto.AddressResponse;
import com.example.companyprojectapi.dto.CompanySummary;
import com.example.companyprojectapi.dto.EmployeeResponse;
import com.example.companyprojectapi.dto.EmployeeSummary;
import com.example.companyprojectapi.dto.ProjectSummary;
import com.example.companyprojectapi.entity.Address;
import com.example.companyprojectapi.entity.Employee;
import com.example.companyprojectapi.entity.Project;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class EmployeeMapper {

    private EmployeeMapper() {
    }

    public static EmployeeResponse toResponse(Employee employee) {
        return new EmployeeResponse(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getJobTitle(),
                new CompanySummary(employee.getCompany().getId(), employee.getCompany().getName()),
                toAddressResponse(employee.getAddress()),
                employee.getProjects()
                        .stream()
                        .sorted(Comparator.comparing(Project::getName))
                        .map(EmployeeMapper::toProjectSummary)
                        .collect(Collectors.toList())
        );
    }

    public static EmployeeSummary toSummary(Employee employee) {
        return new EmployeeSummary(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getJobTitle(),
                employee.getEmail()
        );
    }

    private static ProjectSummary toProjectSummary(Project project) {
        return new ProjectSummary(project.getId(), project.getName(), project.getDescription());
    }

    public static AddressResponse toAddressResponse(Address address) {
        if (Objects.isNull(address)) {
            return null;
        }
        return new AddressResponse(
                address.getId(),
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getPostalCode(),
                address.getCountry()
        );
    }
}

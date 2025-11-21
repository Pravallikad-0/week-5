package com.example.companyprojectapi.dto;

import java.util.List;

public record EmployeeResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String jobTitle,
        CompanySummary company,
        AddressResponse address,
        List<ProjectSummary> projects
) {
}

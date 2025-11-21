package com.example.companyprojectapi.dto;

import java.util.List;

public record ProjectWithEmployeesResponse(
        Long id,
        String name,
        String description,
        List<EmployeeSummary> employees
) {
}

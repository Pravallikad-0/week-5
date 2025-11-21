package com.example.companyprojectapi.dto;

import java.util.List;

public record CompanyAggregateResponse(
        Long id,
        String name,
        String description,
        List<ProjectWithEmployeesResponse> projects
) {
}

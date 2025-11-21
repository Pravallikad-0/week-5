package com.example.companyprojectapi.dto;

public record EmployeeSummary(Long id,
                               String firstName,
                               String lastName,
                               String jobTitle,
                               String email) {
}

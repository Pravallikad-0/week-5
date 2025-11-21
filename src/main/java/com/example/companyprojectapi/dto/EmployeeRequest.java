package com.example.companyprojectapi.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record EmployeeRequest(
        @NotBlank(message = "First name is required") String firstName,
        @NotBlank(message = "Last name is required") String lastName,
        @Email(message = "Email must be valid") @NotBlank(message = "Email is required") String email,
        String jobTitle,
        @NotNull(message = "Company id is required") Long companyId,
        Set<Long> projectIds,
        @NotNull(message = "Address is required") @Valid AddressRequest address
) {
}

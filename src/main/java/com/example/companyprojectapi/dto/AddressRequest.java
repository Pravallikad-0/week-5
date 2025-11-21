package com.example.companyprojectapi.dto;

import jakarta.validation.constraints.NotBlank;

public record AddressRequest(
        @NotBlank(message = "Street is required") String street,
        @NotBlank(message = "City is required") String city,
        @NotBlank(message = "State is required") String state,
        @NotBlank(message = "Postal code is required") String postalCode,
        @NotBlank(message = "Country is required") String country
) {
}

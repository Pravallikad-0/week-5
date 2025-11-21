package com.example.companyprojectapi.dto;

public record AddressResponse(
        Long id,
        String street,
        String city,
        String state,
        String postalCode,
        String country
) {
}

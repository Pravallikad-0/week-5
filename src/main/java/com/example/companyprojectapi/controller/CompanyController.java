package com.example.companyprojectapi.controller;

import com.example.companyprojectapi.dto.CompanyAggregateResponse;
import com.example.companyprojectapi.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/search")
    public CompanyAggregateResponse getCompanyByName(@RequestParam String name) {
        return companyService.getByName(name);
    }

    @DeleteMapping("/{companyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompany(@PathVariable Long companyId) {
        companyService.deleteCompany(companyId);
    }
}

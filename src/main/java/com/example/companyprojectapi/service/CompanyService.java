package com.example.companyprojectapi.service;

import com.example.companyprojectapi.dto.CompanyAggregateResponse;
import com.example.companyprojectapi.dto.ProjectWithEmployeesResponse;
import com.example.companyprojectapi.entity.Company;
import com.example.companyprojectapi.entity.Project;
import com.example.companyprojectapi.exception.ResourceNotFoundException;
import com.example.companyprojectapi.mapper.ProjectMapper;
import com.example.companyprojectapi.repository.CompanyRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public CompanyAggregateResponse getByName(String name) {
        Company company = companyRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found: " + name));
        List<ProjectWithEmployeesResponse> projectViews = company.getProjects().stream()
                .sorted(Comparator.comparing(Project::getName))
                .map(ProjectMapper::toProjectWithEmployees)
                .toList();
        return new CompanyAggregateResponse(company.getId(), company.getName(), company.getDescription(), projectViews);
    }

    @Transactional
    public void deleteCompany(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found: " + id));
        companyRepository.delete(company);
    }
}

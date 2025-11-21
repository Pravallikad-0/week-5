package com.example.companyprojectapi.controller;

import com.example.companyprojectapi.dto.EmployeeRequest;
import com.example.companyprojectapi.dto.EmployeeResponse;
import com.example.companyprojectapi.dto.PageResponse;
import com.example.companyprojectapi.dto.ProjectSummary;
import com.example.companyprojectapi.service.EmployeeService;
import com.example.companyprojectapi.service.ProjectService;
import com.example.companyprojectapi.util.PaginationUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final ProjectService projectService;

    public EmployeeController(EmployeeService employeeService, ProjectService projectService) {
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody EmployeeRequest request) {
        EmployeeResponse response = employeeService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{employeeId}")
    public EmployeeResponse updateEmployee(@PathVariable Long employeeId,
                                           @Valid @RequestBody EmployeeRequest request) {
        return employeeService.update(employeeId, request);
    }

    @GetMapping("/{employeeId}")
    public EmployeeResponse getEmployee(@PathVariable Long employeeId) {
        return employeeService.getById(employeeId);
    }

    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Long employeeId) {
        employeeService.delete(employeeId);
    }

    @GetMapping("/{employeeId}/projects")
    public PageResponse<ProjectSummary> getProjectsForEmployee(@PathVariable Long employeeId,
                                                               @RequestParam(defaultValue = "0") Integer page,
                                                               @RequestParam(defaultValue = "20") Integer size,
                                                               @RequestParam(required = false) String sort) {
        Pageable pageable = PaginationUtils.buildPageable(page, size, sort, Sort.by("name").ascending());
        return projectService.getProjectsByEmployee(employeeId, pageable);
    }
}

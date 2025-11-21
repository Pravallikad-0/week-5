package com.example.companyprojectapi.controller;

import com.example.companyprojectapi.dto.EmployeeResponse;
import com.example.companyprojectapi.dto.PageResponse;
import com.example.companyprojectapi.service.EmployeeService;
import com.example.companyprojectapi.service.ProjectService;
import com.example.companyprojectapi.util.PaginationUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final EmployeeService employeeService;
    private final ProjectService projectService;

    public ProjectController(EmployeeService employeeService, ProjectService projectService) {
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    @GetMapping("/{projectId}/employees")
    public PageResponse<EmployeeResponse> getEmployeesByProject(@PathVariable Long projectId,
                                                                @RequestParam(defaultValue = "0") Integer page,
                                                                @RequestParam(defaultValue = "20") Integer size,
                                                                @RequestParam(required = false) String sort) {
        Pageable pageable = PaginationUtils.buildPageable(page, size, sort, Sort.by("lastName").ascending());
        return employeeService.getEmployeesByProject(projectId, pageable);
    }

    @DeleteMapping("/{projectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
    }
}

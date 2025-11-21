package com.example.companyprojectapi.service;

import com.example.companyprojectapi.dto.PageResponse;
import com.example.companyprojectapi.dto.ProjectSummary;
import com.example.companyprojectapi.entity.Employee;
import com.example.companyprojectapi.entity.Project;
import com.example.companyprojectapi.exception.ResourceNotFoundException;
import com.example.companyprojectapi.mapper.ProjectMapper;
import com.example.companyprojectapi.repository.ProjectRepository;
import com.example.companyprojectapi.specification.ProjectSpecifications;
import com.example.companyprojectapi.util.PaginationUtils;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public PageResponse<ProjectSummary> getProjectsByEmployee(Long employeeId, Pageable pageable) {
        Page<Project> projectPage = projectRepository.findAll(ProjectSpecifications.hasEmployee(employeeId), pageable);
        return PaginationUtils.toPageResponse(projectPage, ProjectMapper::toSummary);
    }

    @Transactional
    public void deleteProject(Long projectId) {
        Project project = projectRepository.findWithEmployeesById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found: " + projectId));
        for (Employee employee : project.getEmployees()) {
            employee.getProjects().remove(project);
        }
        projectRepository.delete(project);
    }
}

package com.example.companyprojectapi.service;

import com.example.companyprojectapi.dto.EmployeeRequest;
import com.example.companyprojectapi.dto.EmployeeResponse;
import com.example.companyprojectapi.dto.PageResponse;
import com.example.companyprojectapi.entity.Address;
import com.example.companyprojectapi.entity.Company;
import com.example.companyprojectapi.entity.Employee;
import com.example.companyprojectapi.entity.Project;
import com.example.companyprojectapi.exception.ResourceNotFoundException;
import com.example.companyprojectapi.mapper.EmployeeMapper;
import com.example.companyprojectapi.repository.CompanyRepository;
import com.example.companyprojectapi.repository.EmployeeRepository;
import com.example.companyprojectapi.repository.ProjectRepository;
import com.example.companyprojectapi.specification.EmployeeSpecifications;
import com.example.companyprojectapi.util.PaginationUtils;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;
    private final ProjectRepository projectRepository;

    public EmployeeService(EmployeeRepository employeeRepository,
                           CompanyRepository companyRepository,
                           ProjectRepository projectRepository) {
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
        this.projectRepository = projectRepository;
    }

    @Transactional
    public EmployeeResponse create(EmployeeRequest request) {
        Company company = companyRepository.findById(request.companyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found: " + request.companyId()));

        Employee employee = new Employee();
        mapEmployeeFields(employee, request);
        employee.setCompany(company);
        employee.setAddress(toAddress(request));

        Set<Project> projects = resolveProjects(request.projectIds());
        projects.forEach(project -> project.addEmployee(employee));

        Employee saved = employeeRepository.save(employee);
        return EmployeeMapper.toResponse(saved);
    }

    @Transactional
    public EmployeeResponse update(Long employeeId, EmployeeRequest request) {
        Employee employee = employeeRepository.findDetailedById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + employeeId));

        if (!employee.getCompany().getId().equals(request.companyId())) {
            Company newCompany = companyRepository.findById(request.companyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Company not found: " + request.companyId()));
            employee.setCompany(newCompany);
        }

        mapEmployeeFields(employee, request);
        syncAddress(employee, request);
        syncProjects(employee, request.projectIds());

        return EmployeeMapper.toResponse(employee);
    }

    @Transactional
    public void delete(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + employeeId));
        employee.clearProjects();
        employeeRepository.delete(employee);
    }

    public EmployeeResponse getById(Long id) {
        Employee employee = employeeRepository.findDetailedById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + id));
        return EmployeeMapper.toResponse(employee);
    }

    public PageResponse<EmployeeResponse> getEmployeesByProject(Long projectId, Pageable pageable) {
        Page<Employee> employeePage = employeeRepository.findAll(EmployeeSpecifications.hasProject(projectId), pageable);
        return PaginationUtils.toPageResponse(employeePage, EmployeeMapper::toResponse);
    }

    private void mapEmployeeFields(Employee employee, EmployeeRequest request) {
        employee.setFirstName(request.firstName());
        employee.setLastName(request.lastName());
        employee.setEmail(request.email());
        employee.setJobTitle(request.jobTitle());
    }

    private Address toAddress(EmployeeRequest request) {
        var addressRequest = request.address();
        return Address.builder()
                .street(addressRequest.street())
                .city(addressRequest.city())
                .state(addressRequest.state())
                .postalCode(addressRequest.postalCode())
                .country(addressRequest.country())
                .build();
    }

    private void syncAddress(Employee employee, EmployeeRequest request) {
        Address address = employee.getAddress();
        if (address == null) {
            address = toAddress(request);
            employee.setAddress(address);
        } else {
            address.setStreet(request.address().street());
            address.setCity(request.address().city());
            address.setState(request.address().state());
            address.setPostalCode(request.address().postalCode());
            address.setCountry(request.address().country());
        }
    }

    private Set<Project> resolveProjects(Set<Long> projectIds) {
        if (CollectionUtils.isEmpty(projectIds)) {
            return new HashSet<>();
        }
        List<Project> projects = projectRepository.findAllById(projectIds);
        if (projects.size() != projectIds.size()) {
            throw new ResourceNotFoundException("One or more projects were not found");
        }
        return new HashSet<>(projects);
    }

    private void syncProjects(Employee employee, Set<Long> projectIds) {
        employee.clearProjects();
        Set<Project> projects = resolveProjects(projectIds);
        projects.forEach(project -> project.addEmployee(employee));
    }
}

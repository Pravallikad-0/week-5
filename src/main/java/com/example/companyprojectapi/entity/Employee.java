package com.example.companyprojectapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(onlyExplicitlyIncluded = true)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    private Long id;

    @Column(nullable = false, length = 120)
    @ToString.Include
    private String firstName;

    @Column(nullable = false, length = 120)
    @ToString.Include
    private String lastName;

    @Column(nullable = false, length = 150, unique = true)
    @ToString.Include
    private String email;

    @Column(length = 150)
    private String jobTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Address address;

    @Builder.Default
    @ManyToMany(mappedBy = "employees", fetch = FetchType.LAZY)
    private Set<Project> projects = new LinkedHashSet<>();

    public void setAddress(Address address) {
        if (address != null) {
            address.setEmployee(this);
        }
        this.address = address;
    }

    public void clearProjects() {
        projects.forEach(project -> project.getEmployees().remove(this));
        projects.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee employee)) return false;
        return id != null && Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

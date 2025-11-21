package com.example.companyprojectapi.repository;

import com.example.companyprojectapi.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}

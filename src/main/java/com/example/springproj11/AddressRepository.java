package com.example.springproj11;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, String> {
    Address findById(UUID id);
    Address findByName(String name);
}
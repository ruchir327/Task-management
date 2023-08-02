package com.example.Task.management.repository;

import com.example.Task.management.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

    // Custom query using JPQL with named params

}
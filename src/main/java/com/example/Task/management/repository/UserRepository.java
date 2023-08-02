package com.example.Task.management.repository;


import com.example.Task.management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name IN ('ROLE_ADMIN', 'ROLE_USER')")
    List<User> findAllUsersWithUserRole();

    Optional<User> findByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Boolean existsByUsername(String username);

    // Custom query using JPQL with named params

}
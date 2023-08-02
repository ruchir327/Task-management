package com.example.Task.management.config;


import com.example.Task.management.model.Role;
import com.example.Task.management.model.User;
import com.example.Task.management.repository.RoleRepository;
import com.example.Task.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class DefaultAdminAndUsersConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            // Create and save default admin user
            User adminUser = new User();
            adminUser.setName("Default Admin");
            adminUser.setUsername("admin");
            adminUser.setEmail("admin@example.com");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("Admin role not found!"));
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);
            adminUser.setRoles(adminRoles);
            userRepository.save(adminUser);

            // Create and save Ruchir user
            User ruchirUser = new User();
            ruchirUser.setName("Ruchir");
            ruchirUser.setUsername("ruchir");
            ruchirUser.setEmail("ruchir@example.com");
            ruchirUser.setPassword(passwordEncoder.encode("ruchir123"));
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("User role not found!"));
            Set<Role> userRoles = new HashSet<>();
            userRoles.add(userRole);
            ruchirUser.setRoles(userRoles);
            userRepository.save(ruchirUser);
        }
    }
}

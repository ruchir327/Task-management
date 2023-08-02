//package com.example.Todo.management.service.impl;
//
//import com.example.Todo.management.dto.JwtAuthResponse;
//import com.example.Todo.management.dto.LoginDto;
//import com.example.Todo.management.dto.RegisterDto;
//import com.example.Todo.management.exception.TodoAPIException;
//import com.example.Todo.management.model.Role;
//import com.example.Todo.management.model.User;
//import com.example.Todo.management.repository.RoleRepository;
//import com.example.Todo.management.repository.UserRepository;
////import com.example.Todo.management.security.JwtTokenProvider;
////import com.example.Todo.management.security.JwtTokenProvider;
//import com.example.Todo.management.security.JwtTokenProvider;
//import com.example.Todo.management.service.AuthService;
//import lombok.AllArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//
//@Service
//@AllArgsConstructor
//public class AuthServiceImpl implements AuthService {
//
//    private UserRepository userRepository;
//    private RoleRepository roleRepository;
//    private PasswordEncoder passwordEncoder;
//    private AuthenticationManager authenticationManager;
//    private JwtTokenProvider jwtTokenProvider;
//
//
//
//// ...
//    @Autowired
//    private final ModelMapper modelMapper;
//
//    @Override
//    public String register(RegisterDto registerDto) {
//        if (userRepository.existsByUsername(registerDto.getUsername())) {
//            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Username already exists!");
//        }
//
//        if (userRepository.existsByEmail(registerDto.getEmail())) {
//            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Email already exists!");
//        }
//
//        // Validate the role field (make sure it is either "admin" or "user")
//        if (!"ADMIN".equalsIgnoreCase(registerDto.getRole()) && !"USER".equalsIgnoreCase(registerDto.getRole())) {
//            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Invalid role specified!");
//        }
//
//        User user = modelMapper.map(registerDto, User.class);
//        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
//
//        // Determine the role based on the value provided in RegisterDto
//        String roleName = registerDto.getRole();
//        Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow(() ->
//                new TodoAPIException(HttpStatus.INTERNAL_SERVER_ERROR, "Default role not found!"));
//
//        // If the provided role is "admin", assign the "ROLE_ADMIN" role
//        if ("ADMIN".equalsIgnoreCase(roleName)) {
//            userRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow(() ->
//                    new TodoAPIException(HttpStatus.INTERNAL_SERVER_ERROR, "Admin role not found!"));
//        }
//
//        Set<Role> roles = new HashSet<>();
//        roles.add(userRole);
//        user.setRoles(roles);
//
//        userRepository.save(user);
//
//        return "User registered successfully!";
//    }
//
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }
//    @Override
//    public JwtAuthResponse login(LoginDto loginDto) {
//
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                loginDto.getUsernameOrEmail(), loginDto.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        String token = jwtTokenProvider.generateToken(authentication);
//        Optional<User> user = userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail());
//        String role="ROLE_USER";
//        if (user.isPresent()) {
//            User loggedinuser = user.get();
//            Optional<Role> optionalRole=loggedinuser.getRoles().stream().findFirst();
//            if (optionalRole.isPresent()) {
//                Role userRole=optionalRole.get();
//                role=userRole.getName();
//            }
//        }
//        JwtAuthResponse jwtAuthResponse=new JwtAuthResponse();
//        jwtAuthResponse.setAccessToken(token);
//        jwtAuthResponse.setRole(role);
//        return jwtAuthResponse;
//    }
//}

package com.example.Task.management.service.impl;

import com.example.Task.management.dto.JwtAuthResponse;
import com.example.Task.management.dto.LoginDto;
import com.example.Task.management.dto.RegisterDto;
import com.example.Task.management.exception.TaskAPIException;
import com.example.Task.management.model.Role;
import com.example.Task.management.repository.RoleRepository;
import com.example.Task.management.repository.UserRepository;
import com.example.Task.management.security.JwtTokenProvider;
import com.example.Task.management.service.AuthService;
import com.example.Task.management.model.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private final ModelMapper modelMapper;

    @Override
    public String register(RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new TaskAPIException(HttpStatus.BAD_REQUEST, "Username already exists!");
        }

        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new TaskAPIException(HttpStatus.BAD_REQUEST, "Email already exists!");
        }

        // Validate the role field (make sure it is either "ADMIN" or "USER")
        if (!"ADMIN".equalsIgnoreCase(registerDto.getRole()) && !"USER".equalsIgnoreCase(registerDto.getRole())) {
            throw new TaskAPIException(HttpStatus.BAD_REQUEST, "Invalid role specified!");
        }

        User user = modelMapper.map(registerDto, User.class);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        // Determine the role based on the value provided in RegisterDto
        String roleName = registerDto.getRole();
        Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow(() ->
                new TaskAPIException(HttpStatus.INTERNAL_SERVER_ERROR, "Default role not found!"));

        // If the provided role is "ADMIN", assign the "ROLE_ADMIN" role
        if ("ADMIN".equalsIgnoreCase(roleName)) {
            userRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow(() ->
                    new TaskAPIException(HttpStatus.INTERNAL_SERVER_ERROR, "Admin role not found!"));
        }

        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully!";
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public JwtAuthResponse login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        Optional<User> user = userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail());
        String role = "ROLE_USER";
        if (user.isPresent()) {
            User loggedinuser = user.get();
            Optional<Role> optionalRole = loggedinuser.getRoles().stream().findFirst();
            if (optionalRole.isPresent()) {
                Role userRole = optionalRole.get();
                role = userRole.getName();
            }
        }
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        jwtAuthResponse.setRole(role);
        return jwtAuthResponse;
    }
}

package com.example.Task.management.service;


import com.example.Task.management.dto.JwtAuthResponse;
import com.example.Task.management.dto.LoginDto;
import com.example.Task.management.dto.RegisterDto;
import com.example.Task.management.model.User;

import java.util.List;

public interface AuthService {
    String register(RegisterDto registerDto);

        JwtAuthResponse login(LoginDto loginDto);
            List<User> getAllUsers();
}
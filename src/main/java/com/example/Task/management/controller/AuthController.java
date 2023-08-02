package com.example.Task.management.controller;
import com.example.Task.management.dto.JwtAuthResponse;
import com.example.Task.management.dto.LoginDto;
import com.example.Task.management.dto.RegisterDto;
import com.example.Task.management.model.User;
import com.example.Task.management.service.AuthService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    // Build Register REST API
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


@PostMapping(value = {"/login", "/signin"})
public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
    JwtAuthResponse jwtAuthResponse = authService.login(loginDto);


//
    return ResponseEntity.ok(jwtAuthResponse);
}
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return authService.getAllUsers();
    }

}
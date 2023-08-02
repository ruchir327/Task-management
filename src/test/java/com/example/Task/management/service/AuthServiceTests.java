package com.example.Task.management.service;

import com.example.Task.management.dto.JwtAuthResponse;
import com.example.Task.management.dto.LoginDto;
import com.example.Task.management.dto.RegisterDto;
import com.example.Task.management.exception.TaskAPIException;
import com.example.Task.management.model.Role;
import com.example.Task.management.model.User;
import com.example.Task.management.repository.RoleRepository;
import com.example.Task.management.repository.UserRepository;
import com.example.Task.management.security.JwtTokenProvider;
import com.example.Task.management.service.impl.AuthServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class AuthServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        authService = new AuthServiceImpl(userRepository, roleRepository, passwordEncoder, authenticationManager, jwtTokenProvider, new ModelMapper());
    }


    @Test
    @DisplayName("Register a new user successfully")
    public void testRegisterNewUser() {
        // Given
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername("john");
        registerDto.setEmail("john@example.com");
        registerDto.setPassword("password");
        registerDto.setRole("user");

        given(userRepository.existsByUsername(anyString())).willReturn(false);
        given(userRepository.existsByEmail(anyString())).willReturn(false);
        given(passwordEncoder.encode(anyString())).willReturn("encodedPassword");

        Role userRole = new Role("ROLE_USER");
        given(roleRepository.findByName(anyString())).willReturn(Optional.of(userRole));


        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);

        given(userRepository.save(any(User.class))).willReturn(user);

        // When
        String result = authService.register(registerDto);

        // Then
        Assertions.assertThat(result).isEqualTo("User registered successfully!");
        verify(userRepository, Mockito.times(1)).save(any(User.class));
        System.out.println("User registered successfully!");
    }

    @Test
    @DisplayName("Attempt to register a user with an existing username")
    public void testRegisterUserWithExistingUsername() {
        // Given
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername("abc");
        registerDto.setEmail("abcc");
        registerDto.setPassword("password");
        registerDto.setRole("user");

        given(userRepository.existsByUsername(anyString())).willReturn(true);

        try {
            // When
            authService.register(registerDto);

            // Then
            fail("Expected TaskAPIException, but no exception was thrown.");
        } catch (TaskAPIException e) {
            // Verify that the expected exception was thrown
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
            assertEquals("Username already exists!", e.getMessage());
            verify(userRepository, never()).save(any(User.class));
            System.out.println("Username already exists!");

        }
    }

    @Test
    @DisplayName("Attempt to register a user with an existing email")
    public void testRegisterUserWithExistingEmail() {
        // Given
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername("ajit");
        registerDto.setEmail("ajit@gmail.com");
        registerDto.setPassword("ajit");
        registerDto.setRole("user");

        given(userRepository.existsByUsername(anyString())).willReturn(false);
        given(userRepository.existsByEmail(anyString())).willReturn(true);

        try {
            // When
            authService.register(registerDto);

            // Then
            fail("Expected TaskAPIException, but no exception was thrown.");
        } catch (TaskAPIException e) {
            // Verify that the expected exception was thrown
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
            assertEquals("Email already exists!", e.getMessage());
            verify(userRepository, never()).save(any(User.class));
        }
        System.out.println("Email already exists");
    }


    @Test
    @DisplayName("Attempt to register a user with an invalid role")
    public void testRegisterUserWithInvalidRole() {
        // Given
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername("john");
        registerDto.setEmail("john@example.com");
        registerDto.setPassword("password");
        registerDto.setRole("invalidRole");

        try {
            // When
            authService.register(registerDto);

            // Then
            fail("Expected TaskAPIException, but no exception was thrown.");
        } catch (TaskAPIException e) {
            // Verify that the expected exception was thrown
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
            assertEquals("Invalid role specified!", e.getMessage());
            verify(userRepository, never()).save(any(User.class));
        }
        System.out.println("Invalid role specified!");
    }


    @Test
    @DisplayName("Test login with valid credentials")
    public void testLoginWithValidCredentials() {
        // Arrange
        LoginDto loginDto = new LoginDto();
        loginDto.setUsernameOrEmail("john@example.com");
        loginDto.setPassword("password");

        User user = new User();
        user.setUsername("john");
        user.setEmail("john@example.com");
        user.setPassword("password");

        // Set up authentication manager to return an Authentication object
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        // Set up user repository to return the user
        when(userRepository.findByUsernameOrEmail("john@example.com", "john@example.com")).thenReturn(Optional.of(user));

        // Set up jwtTokenProvider to return a token
        when(jwtTokenProvider.generateToken(authentication)).thenReturn("jwt_token");

        // Act
        JwtAuthResponse response = authService.login(loginDto);

        // Assert
        assertEquals("jwt_token", response.getAccessToken());
        assertNotNull(response.getRole(), "Expected a non-null role");
        assertTrue(response.getRole().equals("ROLE_USER") || response.getRole().equals("ROLE_ADMIN"), "Expected either ROLE_USER or ROLE_ADMIN");
        System.out.println("Logged in successfully!");
    }





    @Test
    @DisplayName("Login with invalid credentials")
    public void testLoginWithInvalidCredentials() {
        // Given
        LoginDto loginDto = new LoginDto();
        loginDto.setUsernameOrEmail("invalidUsername");
        loginDto.setPassword("invalidPassword");

        given(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .willThrow(new TaskAPIException(HttpStatus.UNAUTHORIZED, "Invalid credentials!"));

        // When and Then
        TaskAPIException exception = assertThrows(TaskAPIException.class, () -> {
            authService.login(loginDto);
        });

        // Verify that the expected exception was thrown
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
        assertEquals("Invalid credentials!", exception.getMessage());
        System.out.println("Invalid Credentials");
    }


}

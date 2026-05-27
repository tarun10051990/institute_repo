package com.career.assessment.service;

import com.career.assessment.dto.AuthRequest;
import com.career.assessment.dto.AuthResponse;
import com.career.assessment.dto.RegisterRequest;
import com.career.assessment.entity.User;
import com.career.assessment.repository.UserRepository;
import com.career.assessment.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User.EducationLevel level = User.EducationLevel.CLASS_10_12;
        if (request.getEducationLevel() != null) {
            try {
                level = User.EducationLevel.valueOf(request.getEducationLevel());
            } catch (IllegalArgumentException ignored) {
            }
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .educationLevel(level)
                .schoolName(request.getSchoolName())
                .city(request.getCity())
                .build();

        user = userRepository.save(user);

        String token = tokenProvider.generateToken(user.getEmail(), user.getId());
        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userId(user.getId())
                .build();
    }

    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = tokenProvider.generateToken(user.getEmail(), user.getId());
        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userId(user.getId())
                .build();
    }
}

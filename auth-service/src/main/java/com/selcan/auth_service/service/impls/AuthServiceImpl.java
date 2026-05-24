package com.selcan.auth_service.service.impls;

import com.selcan.auth_service.dtos.AuthResponse;
import com.selcan.auth_service.dtos.LoginDto;
import com.selcan.auth_service.dtos.RegisterDto;
import com.selcan.auth_service.dtos.UserResponse;
import com.selcan.auth_service.enums.Role;
import com.selcan.auth_service.exceptions.InvalidCredentialsException;
import com.selcan.auth_service.exceptions.UserAlreadyExistsException;
import com.selcan.auth_service.exceptions.UserNotFoundException;
import com.selcan.auth_service.model.User;
import com.selcan.auth_service.repositories.UserRepository;
import com.selcan.auth_service.security.JwtUtil;
import com.selcan.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final JwtBlacklistService blacklistService;
    @Override
    public AuthResponse register(RegisterDto request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email already exists");
        }
        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        String token = jwtUtil.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .user(mapToResponse(user))
                .build();
    }

    @Override
    public AuthResponse login(LoginDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .user(mapToResponse(user))
                .build();
    }

    @Override
    public void logout(String token) {
        blacklistService.blacklist(token, 86400000);
    }
    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}

package com.ito.bloger.service;

import com.ito.bloger.config.Role;
import com.ito.bloger.dto.request.AuthenticationRequest;
import com.ito.bloger.dto.response.AuthenticationResponse;
import com.ito.bloger.enitty.Token;
import com.ito.bloger.enitty.User;
import com.ito.bloger.repository.TokenRepository;
import com.ito.bloger.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;

    public AuthenticationResponse register(AuthenticationRequest request, HttpServletResponse response) {
        userRepository.findByUsername(request.getUsername())
                .ifPresent(user -> {
                    throw new RuntimeException("Username is already taken");
                });

        System.out.println(request);
        if (!request.getRole().equals(Role.USER.name()) && !request.getRole().equals(Role.ADMIN.name())) {
            throw new RuntimeException("Role is not valid");
        }
        User user = null;
        if (request.getRole().equals(Role.ADMIN.name())) {
            var role = Role.ADMIN;
            System.out.println(role);
            user = User.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(role)
                    .build();
        } else if (request.getRole().equals(Role.USER.name())) {
            var role = Role.USER;
            System.out.println(role);
            user = User.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(role)
                    .build();
        }

        final String token = jwtService.generateToken(user);
        saveUserToken(user, token);

        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge((int) jwtService.getJwtExpiration());
        response.addCookie(cookie);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        revokeAllToken(user);
        final String token = jwtService.generateToken(user);
        saveUserToken(user, token);

        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge((int) jwtService.getJwtExpiration());
        response.addCookie(cookie);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    private void revokeAllToken(User user) {
        tokenRepository.findAllValidTokenByUserId(user.getId())
                .forEach(token -> {
                    token.setExpired(true);
                    token.setRevoked(true);
                });
        tokenRepository.saveAll(user.getTokens());
    }
    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .isExpired(false)
                .isRevoked(false)
                .build();
        tokenRepository.save(token);
    }
}

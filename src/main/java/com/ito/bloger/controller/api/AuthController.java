package com.ito.bloger.controller.api;

import com.ito.bloger.dto.request.AuthenticationRequest;
import com.ito.bloger.service.AuthService;
import com.ito.bloger.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthenticationRequest request, HttpServletResponse response) {
        return ResponseEntity.ok().body(authService.register(request, response)) ;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request, HttpServletResponse response) {
        return ResponseEntity.ok().body(authService.authenticate(request, response));
    }
}

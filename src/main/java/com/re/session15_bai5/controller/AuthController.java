package com.re.session15_bai5.controller;

import com.re.session15_bai5.reponse.AuthResponse;
import com.re.session15_bai5.request.LoginRequest;
import com.re.session15_bai5.request.RegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final Map<String, String> userStore = new HashMap<>();

    public AuthController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        if (userStore.containsKey(request.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new AuthResponse("Username already exists"));
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        userStore.put(request.getUsername(), encodedPassword);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse("User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        String encodedPassword = userStore.get(request.getUsername());
        if (encodedPassword != null && passwordEncoder.matches(request.getPassword(), encodedPassword)) {
            return ResponseEntity.ok(new AuthResponse("Login successful"));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new AuthResponse("Invalid username or password"));
    }
}

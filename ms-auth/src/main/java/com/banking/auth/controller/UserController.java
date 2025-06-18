package com.banking.auth.controller;

import com.banking.auth.dto.request.RegisterRequest;
import com.banking.auth.dto.response.RegisterResponse;
import com.banking.auth.enums.Role;
import com.banking.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody RegisterRequest request, Authentication authentication) {
        String creatorEmail = authentication.getName();
        return ResponseEntity.ok(userService.registerUser(request, creatorEmail));
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId, Authentication authentication) {
        String requestEmail = authentication.getName();
        String responseMessage = userService.deleteUser(userId, requestEmail);
        return ResponseEntity.ok(responseMessage);
    }

    @GetMapping("/role/{role}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<List<RegisterResponse>> getUsersByRole(@PathVariable Role role) {
        return ResponseEntity.ok(userService.getUsersByRole(role));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'AUDITOR')")
    public ResponseEntity<List<RegisterResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
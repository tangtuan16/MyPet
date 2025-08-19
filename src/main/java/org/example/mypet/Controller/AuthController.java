package org.example.mypet.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mypet.DTO.Auth.JwtResponse;
import org.example.mypet.DTO.Auth.RefreshTokenRequest;
import org.example.mypet.DTO.Auth.ResetPasswordRequest;
import org.example.mypet.DTO.Auth.UserRegister;
import org.example.mypet.Service.AuthService;
import org.example.mypet.Utils.ApiResponse;
import org.example.mypet.Utils.Jwts.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Boolean>> createUser(@Valid @RequestBody UserRegister ur) {
        log.info("Creating user {}", ur.getUsername());
        log.info("AuthServiceT {}", authService);
        log.info("JwtService {}", jwtService);
        return ApiResponse.success(authService.createUser(ur), HttpStatus.CREATED.value(), "Register Successful!", null);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        JwtResponse response = jwtService.refreshToken(request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<String>> resetPassword(@Valid @RequestBody ResetPasswordRequest dto) {
        authService.resetPasswordAsAdmin(dto.getUserId(), dto.getNewPassword());
        return ApiResponse.success("Password reset successfully");
    }
}

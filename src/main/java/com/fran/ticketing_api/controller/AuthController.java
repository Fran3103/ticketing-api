package com.fran.ticketing_api.controller;


import com.fran.ticketing_api.dto.auth.*;
import com.fran.ticketing_api.service.IAuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @Operation(summary = "Registrar un nuevo usuario", description = "Crea un usuario y devuelve access y refresh tokens")
    @ApiResponse(responseCode = "201", description = "Usuario creado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class)))
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest registerRequest,
            HttpServletResponse response) {

        AuthResponse authResponse = authService.register(registerRequest, response);
        URI location = URI.create("/api/auth/me");
        return ResponseEntity.created(location).body(authResponse);
    }

    @Operation(summary = "Login", description = "Autentica con email y password y devuelve tokens")
    @ApiResponse(responseCode = "200", description = "Login exitoso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class)))
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest loginRequest,
            HttpServletResponse response) {

        AuthResponse authResponse = authService.login(loginRequest, response);
        return ResponseEntity.ok(authResponse);
    }

    @Operation(summary = "Refresh token", description = "Renueva el access token usando refresh token")
    @ApiResponse(responseCode = "200", description = "Tokens renovados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class)))
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(
            @Valid @RequestBody RefreshRequest refreshRequest,
            HttpServletResponse response) {

        AuthResponse authResponse = authService.refresh(refreshRequest, response);
        return ResponseEntity.ok(authResponse);
    }

    @Operation(summary = "Logout", description = "Revoca el refresh token")
    @ApiResponse(responseCode = "204", description = "Logout exitoso")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody LogoutRequest logoutRequest) {
        authService.logout(logoutRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Perfil del usuario autenticado", description = "Devuelve la información del usuario actual")
    @ApiResponse(responseCode = "200", description = "Perfil obtenido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MeResponse.class)))
    @GetMapping("/me")
    public ResponseEntity<MeResponse> profile(Authentication authentication) {
        String email = authentication.getName();
        MeResponse meResponse = authService.me(email);
        return ResponseEntity.ok(meResponse);
    }

}

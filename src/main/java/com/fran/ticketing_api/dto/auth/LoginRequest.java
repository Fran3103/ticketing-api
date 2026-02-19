package com.fran.ticketing_api.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request para iniciar sesión")
public record LoginRequest(
        @Schema(description = "Email del usuario", example = "juan@example.com") @Email @NotBlank String email,
        @Schema(description = "Contraseña del usuario", example = "password123") @NotBlank  String password
) {
}

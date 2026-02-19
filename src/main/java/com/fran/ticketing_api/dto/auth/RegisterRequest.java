package com.fran.ticketing_api.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request para registrar un nuevo usuario")
public record RegisterRequest(
        @Schema(description = "Nombre completo del usuario", example = "Juan Pérez") @NotBlank @Size(max = 100) String name,
        @Schema(description = "Email único del usuario", example = "juan@example.com") @Email @NotBlank @Size(max = 150) String email,
        @Schema(description = "Contraseña en texto plano (se almacenará hasheada)", minLength = 8, maxLength = 72, example = "password123") @NotBlank @Size(min=8, max=72) String password
) {
}

package com.fran.ticketing_api.dto;

import com.fran.ticketing_api.entitie.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Request para crear un usuario (admin) ")
public record CreateUserRequest(
        @Schema(description = "Nombre completo", example = "Pedro") @NotBlank @Size(max = 100) String name,
        @Schema(description = "Email del usuario", example = "pepe@example.com") @NotBlank @Email @Size(max = 150) String email,
        @Schema(description = "Rol del usuario", example = "ADMIN") @NotNull Role role,
        @Schema(description = "Contraseña (se almacenará hasheada)", minLength = 8, maxLength = 72, example = "passwordSeguro123") @NotBlank @Size(min = 8, max = 72) String password
        ) {
}

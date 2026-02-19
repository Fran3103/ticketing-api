package com.fran.ticketing_api.dto;

import com.fran.ticketing_api.entitie.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Schema(description = "Campos opcionales para actualizar un usuario")
public record UpdateUserRequest(
        @Schema(description = "Nombre completo", example = "Pedro") @Size(max = 100) String name,
        @Schema(description = "Email del usuario", example = "pepe@example.com") @Email @Size(max = 150) String email,
        @Schema(description = "Rol del usuario", example = "AGENT") Role role
) {
}

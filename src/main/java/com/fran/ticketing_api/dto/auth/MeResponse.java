package com.fran.ticketing_api.dto.auth;

import com.fran.ticketing_api.entitie.Role;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Información pública del usuario autenticado")
public record MeResponse(
        @Schema(description = "ID del usuario", example = "1") Long id,
        @Schema(description = "Nombre completo", example = "Juan Pérez") String name,
        @Schema(description = "Email del usuario", example = "juan@example.com") String email,
        @Schema(description = "Rol del usuario", example = "CUSTOMER") Role role,
        @Schema(description = "Fecha de creación", example = "2026-02-18T20:30:00Z") Instant createdAt
) {
}

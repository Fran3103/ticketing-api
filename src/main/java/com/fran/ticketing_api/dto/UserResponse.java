package com.fran.ticketing_api.dto;


import com.fran.ticketing_api.entitie.Role;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Representación pública de un usuario")
public record UserResponse (
        @Schema(description = "ID del usuario", example = "1") Long id,
    @Schema(description = "Nombre completo", example = "Pedro") String name,
     @Schema(description = "Email del usuario", example = "pepe@example.com") String email,
     @Schema(description = "Rol del usuario", example = "ADMIN") Role role,
     @Schema(description = "Fecha de creación", example = "2026-02-18T22:44:40.753Z") Instant createdAt
){}

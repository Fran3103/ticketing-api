package com.fran.ticketing_api.dto;

import com.fran.ticketing_api.entitie.Priority;
import com.fran.ticketing_api.entitie.Status;
import com.fran.ticketing_api.entitie.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request para crear un ticket")
public record CreateTicketRequest(
        @Schema(description = "Título del ticket", example = "Error en inicio de sesión") @NotBlank @Size(max=100) String title,
        @Schema(description = "Descripción detallada del problema", example = "No puedo acceder a mi cuenta") String description,
        @Schema(description = "ID del usuario asignado (opcional)", example = "2") Long assigneeId,
        @Schema(description = "Prioridad del ticket", example = "HIGH") Priority priority) {}

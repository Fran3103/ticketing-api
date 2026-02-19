package com.fran.ticketing_api.dto;

import com.fran.ticketing_api.entitie.Priority;
import com.fran.ticketing_api.entitie.Status;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Representación de un ticket")
public record TicketResponse(
    @Schema(description = "ID del ticket", example = "1") Long id,
    @Schema(description = "Título del ticket", example = "Error en inicio de sesión") String title,
    @Schema(description = "Descripción del ticket", example = "No puedo acceder a mi cuenta") String description,
    @Schema(description = "Estado del ticket", example = "OPEN") Status status,
    @Schema(description = "Prioridad del ticket", example = "HIGH") Priority priority,
    @Schema(description = "ID del agente asignado (nullable)", example = "2") Long assignedId,
    @Schema(description = "Fecha de creación", example = "2026-02-18T10:30:00Z") Instant createdAt

) {
}

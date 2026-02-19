package com.fran.ticketing_api.dto;

import com.fran.ticketing_api.entitie.Priority;
import com.fran.ticketing_api.entitie.Status;
import com.fran.ticketing_api.entitie.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Campos para actualizar un ticket")
public record UpdateTicketRequest(
    @Schema(description = "Título del ticket", example = "Nuevo título") String title,
    @Schema(description = "Descripción del ticket", example = "Descripción actualizada") String description,
    @Schema(description = "ID del agente asignado", example = "2") Long assigneeId,
    @Schema(description = "Estado del ticket", example = "IN_PROGRESS") @NotNull Status status,
    @Schema(description = "Prioridad del ticket", example = "MEDIUM") Priority priority
) {
}

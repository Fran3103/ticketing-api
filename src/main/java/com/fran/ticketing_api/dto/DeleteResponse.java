package com.fran.ticketing_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta genérica para operaciones de eliminación")
public record DeleteResponse(@Schema(description = "Mensaje de resultado", example = "Ticket deleted successfully") String message, @Schema(description = "ID del recurso eliminado", example = "1") Long id) {
}

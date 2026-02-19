package com.fran.ticketing_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.Instant;

@Schema(description = "Representación de un comentario de ticket")
public record TicketCommentResponse(
        @Schema(description = "ID del comentario", example = "1") Long id,
        @Schema(description = "ID del ticket asociado", example = "5") Long ticketId,
        @Schema(description = "ID del autor del comentario", example = "2") Long authorId,
         @Schema(description = "Texto del comentario", example = "Mensaje del comentario", maxLength = 1000) @Size(max=1000) String comment,
        @Schema(description = "Fecha de creación", example = "2026-02-18T11:20:00Z") Instant createdAt

) {
}

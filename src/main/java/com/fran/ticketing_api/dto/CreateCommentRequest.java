package com.fran.ticketing_api.dto;

import com.fran.ticketing_api.entitie.Ticket;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Request para crear un comentario en un ticket")
public record CreateCommentRequest(
     @Schema(description = "Texto del comentario", example = "Estamos investigando el problema", maxLength = 1000) @NotBlank @Size(max = 1000) String comment,
     @Schema(description = "ID del autor del comentario", example = "2") @NotNull Long author


) {
}

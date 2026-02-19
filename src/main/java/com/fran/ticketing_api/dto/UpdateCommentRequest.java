package com.fran.ticketing_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Campos para actualizar un comentario")
public record UpdateCommentRequest(
       @Schema(description = "Texto del comentario", example = "Actualización del comentario", maxLength = 1000) @NotBlank @Size(max=1000) String comment
) {
}

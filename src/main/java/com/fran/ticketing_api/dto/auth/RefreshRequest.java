package com.fran.ticketing_api.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request para renovar tokens (refresh)")
public record RefreshRequest(
        @Schema(description = "Refresh token", example = "a1b2c3d4e5f6...") @NotBlank String refreshToken
        ) {
}

package com.fran.ticketing_api.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta de autenticación con tokens")
public record AuthResponse(

        @Schema(description = "JWT access token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...") String accessToken,
        @Schema(description = "Refresh token (no es JWT)" , example = "a1b2c3d4e5f6...") String refreshToken,
        @Schema(description = "Tiempo en segundos que dura el access token", example = "900") Long expiresInSeconds

) {
}

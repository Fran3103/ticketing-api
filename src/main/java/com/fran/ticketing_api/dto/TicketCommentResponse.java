package com.fran.ticketing_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public record TicketCommentResponse(
        Long id,
        Long ticketId,
        Long authorId,
         @Size(max=1000) String comment,
        Instant createdAt

) {
}

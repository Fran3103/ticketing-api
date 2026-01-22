package com.fran.ticketing_api.dto;

import com.fran.ticketing_api.entitie.Priority;
import com.fran.ticketing_api.entitie.Status;

import java.time.Instant;
import java.util.List;

public record TicketDetailResponse(
        Long id,
        String title,
        String description,
        Status status,
        Priority priority,
        Long assignedId,
        Instant createdAt,
        Instant updatedAt,
        List<TicketCommentResponse> comments
) {
}

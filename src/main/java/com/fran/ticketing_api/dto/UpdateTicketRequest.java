package com.fran.ticketing_api.dto;

import com.fran.ticketing_api.entitie.Priority;
import com.fran.ticketing_api.entitie.Status;
import com.fran.ticketing_api.entitie.User;
import jakarta.validation.constraints.NotNull;

public record UpdateTicketRequest(
    String title,
    String description,
    Long assigneeId,
    @NotNull Status status,
    Priority priority
) {
}

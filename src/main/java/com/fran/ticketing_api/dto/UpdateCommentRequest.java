package com.fran.ticketing_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateCommentRequest(
       @NotBlank @Size(max=1000) String comment
) {
}

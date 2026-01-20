package com.fran.ticketing_api.dto;

import com.fran.ticketing_api.entitie.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        @Size(max = 100) String name,
        @Email @Size(max = 150) String email,
        Role role
) {
}

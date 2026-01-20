package com.fran.ticketing_api.dto;


import com.fran.ticketing_api.entitie.Role;

import java.time.Instant;

public record UserResponse (
        Long id,
    String name,
     String email,
     Role role,
     Instant createdAr
){}

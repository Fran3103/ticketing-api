package com.fran.ticketing_api.service;

import com.fran.ticketing_api.dto.auth.*;
import jakarta.servlet.http.HttpServletResponse;

public interface IAuthService {

    AuthResponse register(RegisterRequest req, HttpServletResponse http) ;

    AuthResponse login(LoginRequest req, HttpServletResponse http);

    AuthResponse refresh(RefreshRequest req, HttpServletResponse http);

    void logout(LogoutRequest req);

    MeResponse me(String email);
}

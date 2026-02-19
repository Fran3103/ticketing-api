package com.fran.ticketing_api.service.impl;

import com.fran.ticketing_api.dto.auth.*;
import com.fran.ticketing_api.entitie.RefreshToken;
import com.fran.ticketing_api.entitie.Role;
import com.fran.ticketing_api.entitie.User;
import com.fran.ticketing_api.exception.ResourceNotFoundException;
import com.fran.ticketing_api.repository.IRefreshTokenRepository;
import com.fran.ticketing_api.repository.IUserRepository;
import com.fran.ticketing_api.security.JwtService;
import com.fran.ticketing_api.service.IAuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private IUserRepository userRepo;

    @Autowired
    private IRefreshTokenRepository refreshRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;

    @Value("${jwt.refresh.expDays}")
    private long refreshExpDays;

    private final SecureRandom random = new SecureRandom();

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest req, HttpServletResponse http) {
        // Verificar si el email ya existe
        if (userRepo.existsByEmailIgnoreCase(req.email())) {
            throw new IllegalArgumentException("Email already registered");
        }

        // Crear nuevo usuario
        User user = new User();
        user.setName(req.name());
        user.setEmail(req.email().toLowerCase());
        user.setPasswordHash(passwordEncoder.encode(req.password()));
        user.setRole(Role.CUSTOMER); // Por defecto es CUSTOMER
        user.setEnabled(true);

        user = userRepo.save(user);

        // Generar tokens
        String accessToken = generateAccessToken(user);
        String refreshToken = generateAndSaveRefreshToken(user);

        return new AuthResponse(
                accessToken,
                refreshToken,
                jwtService.accessExpiresInSeconds()
        );
    }

    @Override
    @Transactional
    public AuthResponse login(LoginRequest req, HttpServletResponse http) {
        // Autenticar con Spring Security
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.email().toLowerCase(),
                        req.password()
                )
        );

        // Obtener usuario
        User user = userRepo.findByEmailIgnoreCase(req.email())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Generar tokens
        String accessToken = generateAccessToken(user);
        String refreshToken = generateAndSaveRefreshToken(user);

        return new AuthResponse(
                accessToken,
                refreshToken,
                jwtService.accessExpiresInSeconds()
        );
    }

    @Override
    @Transactional
    public AuthResponse refresh(RefreshRequest req, HttpServletResponse http) {
        String tokenHash = hashToken(req.refreshToken());

        // Buscar refresh token válido
        RefreshToken refreshToken = refreshRepo.findByTokenHashAndRevokedFalse(tokenHash)
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired refresh token"));

        // Verificar si ha expirado
        if (refreshToken.getExpiresAt().isBefore(Instant.now())) {
            refreshToken.setRevoked(true);
            refreshRepo.save(refreshToken);
            throw new IllegalArgumentException("Refresh token expired");
        }

        User user = refreshToken.getUser();

        // Generar nuevo access token
        String newAccessToken = generateAccessToken(user);

        // Opcionalmente, generar nuevo refresh token (rotación de tokens)
        String newRefreshToken = generateAndSaveRefreshToken(user);

        // Revocar el refresh token anterior
        refreshToken.setRevoked(true);
        refreshRepo.save(refreshToken);

        return new AuthResponse(
                newAccessToken,
                newRefreshToken,
                jwtService.accessExpiresInSeconds()
        );
    }

    @Override
    @Transactional
    public void logout(LogoutRequest req) {
        String tokenHash = hashToken(req.refreshToken());

        // Buscar y revocar el refresh token
        refreshRepo.findByTokenHashAndRevokedFalse(tokenHash)
                .ifPresent(token -> {
                    token.setRevoked(true);
                    refreshRepo.save(token);
                });
    }

    @Override
    public MeResponse me(String email) {
        User user = userRepo.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return new MeResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt()
        );
    }

    // ========== Métodos Auxiliares ==========

    private String generateAccessToken(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", user.getRole().name());
        extraClaims.put("userId", user.getId());

        return jwtService.generateAccessToken(user.getEmail(), extraClaims);
    }

    private String generateAndSaveRefreshToken(User user) {
        // Generar token aleatorio
        byte[] randomBytes = new byte[32];
        random.nextBytes(randomBytes);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);

        // Crear entidad RefreshToken
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setTokenHash(hashToken(token));
        refreshToken.setCreatedAt(Instant.now());
        refreshToken.setExpiresAt(Instant.now().plusSeconds(refreshExpDays * 24 * 60 * 60)); // Expira en X días
        refreshToken.setRevoked(false);

        refreshRepo.save(refreshToken);

        return token;
    }

    private String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }
}

package com.fran.ticketing_api.entitie;


import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;


@Setter @Getter
@AllArgsConstructor @NoArgsConstructor
@Table(name = "refresh_tokens")
@Entity
public class RefreshToken {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, name = "token_hash", unique = true, length = 255)
    private String tokenHash;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "replaced_by")
    private RefreshToken replacedBy;

     @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

     @Column(name = "revoked", nullable = false)
    private boolean revoked = false;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Column(name = "user_agent", length = 255)
    private String userAgent;

    @Column(length = 50, name = "ip")
    private String ip;


    @PrePersist
    public void onCreate() {
        if (this.createdAt == null) createdAt = Instant.now();
    }

    public boolean isExpired() {
        return expiresAt != null && Instant.now().isAfter(expiresAt);
    }
}

package com.fran.ticketing_api.repository;

import com.fran.ticketing_api.entitie.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IRefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByTokenHashAndRevokedFalse(String tokenHash);

    long deleteByUser_Id(Long userId);

    List<RefreshToken> findAllByUser_IdAndRevokedFalse(Long userId);


    @Modifying
    @Query("update RefreshToken rt set rt.revoked = true where rt.user.id = :userId and rt.revoked = false")
    int revokeAllActiveByUserId(@Param("userId") Long userId);

}

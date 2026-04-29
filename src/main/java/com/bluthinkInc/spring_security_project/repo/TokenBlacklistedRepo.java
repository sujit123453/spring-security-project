package com.bluthinkInc.spring_security_project.repo;

import com.bluthinkInc.spring_security_project.model.TokenBlacklisted;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TokenBlacklistedRepo extends JpaRepository<TokenBlacklisted, Long> {

    boolean existsByToken(String token);

    @Transactional
    @Modifying
    @Query("DELETE FROM TokenBlacklisted b WHERE b.expiration < :now")
    void deleteExpiredBlacklistedToken(LocalDateTime now);
}

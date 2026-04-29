package com.bluthinkInc.spring_security_project.repo;

import com.bluthinkInc.spring_security_project.model.RefreshToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM RefreshToken r WHERE r.expiresIn< :now")
    void deleteExpiredRefreshToken(LocalDateTime now);


    Optional<RefreshToken> findByUserName(String username);

    @Transactional
    @Modifying
    void deleteByUserName(String username);


}

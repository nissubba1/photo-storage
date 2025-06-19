package org.example.server.token;

import jakarta.transaction.Transactional;
import org.example.server.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);

    @Modifying
    @Transactional
    @Query("UPDATE VerificationToken vt Set vt.confirmedAt = :confirmedAt WHERE vt.token = :token")
    int updateConfirmedAt(@Param("token") String token, @Param("confirmedAt") LocalDateTime now);

    void deleteAllByUser(User user);
}

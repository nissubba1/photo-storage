package org.example.server.token;

import jakarta.transaction.Transactional;
import org.example.server.user.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class VerificationTokenService {
    private final VerificationTokenRepository verificationTokenRepository;

    public VerificationTokenService(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Transactional(rollbackOn =  Exception.class)
    public void saveVerificationToken(VerificationToken verificationToken) {
        verificationTokenRepository.save(verificationToken);
    }

    public VerificationToken getToken(String token) {
        return verificationTokenRepository.findByToken(token).orElseThrow(() -> new IllegalArgumentException("token not found"));
    }

    public int setConfirmedAt(String token) {
        return verificationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }

    @Transactional(rollbackOn =  Exception.class)
    public void deleteUserToken(User user) {
        verificationTokenRepository.deleteAllByUser(user);
    }
}

package org.example.server.registration;

import org.example.server.email.EmailSender;
import org.example.server.email.EmailTemplate;
import org.example.server.token.VerificationToken;
import org.example.server.token.VerificationTokenService;
import org.example.server.user.User;
import org.example.server.user.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegistrationService {
    private final UserService userService;
    private final EmailValidation emailValidation;
    private final VerificationTokenService verificationTokenService;
    private final EmailSender emailSender;
    private final EmailTemplate emailTemplate;

    public RegistrationService(UserService userService, EmailValidation emailValidation, VerificationTokenService verificationTokenService, EmailSender emailSender, EmailTemplate emailTemplate) {
        this.userService = userService;
        this.emailValidation = emailValidation;
        this.verificationTokenService = verificationTokenService;
        this.emailSender = emailSender;
        this.emailTemplate = emailTemplate;
    }

    public String sendVerificationEmail(User user) {
        boolean isValidEmail = emailValidation.test(user.getEmail());

        if (!isValidEmail) {
            throw new IllegalArgumentException("Invalid email");
        }

        String token = userService.registerUser(user);

        String verificationLink = "http://localhost:8080/api/v1.1/user/register/confirm?token=" + token;
        try {
            emailSender.sendEmail(user.getEmail(), emailTemplate.buildEmail(user.getFirstName(), verificationLink));
            System.out.println("Confirmation email send");
        } catch (Exception e) {
            System.out.println("Error sending verification email" + e.getMessage());
        }
        return token;
    }

    public String verifyToken(String token) {
        VerificationToken verificationToken = verificationTokenService.getToken(token);

        if (verificationToken.getConfirmedAt() != null) {
            throw new IllegalArgumentException("Token has already been confirmed");
        }

        LocalDateTime expiredAt = verificationToken.getExpiredAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Token has expired");
        }

        int checkTokenVerified = verificationTokenService.setConfirmedAt(token);

        if (checkTokenVerified == 0) {
            throw new IllegalArgumentException("Token could not be verified");
        }

        int enableAccount = userService.enableUserAccount(verificationToken.getUser().getUsername());

        if (enableAccount == 0) {
            throw new IllegalArgumentException("User account could not be enabled");
        }

        return "Token has been confirmed";
    }
}

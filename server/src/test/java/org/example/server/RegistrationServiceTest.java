package org.example.server;

import org.example.server.email.EmailSender;
import org.example.server.email.EmailTemplate;
import org.example.server.registration.EmailValidation;
import org.example.server.registration.RegistrationService;
import org.example.server.token.VerificationTokenService;
import org.example.server.user.User;
import org.example.server.user.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class RegistrationServiceTest {

    private RegistrationService registrationService;
    private UserService userService;
    private EmailValidation emailValidation;
    private VerificationTokenService verificationTokenService;
    private EmailSender emailSender;
    private EmailTemplate emailTemplate;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        emailValidation = mock(EmailValidation.class);
        verificationTokenService = mock(VerificationTokenService.class);
        emailSender = mock(EmailSender.class);
        emailTemplate = mock(EmailTemplate.class);

        registrationService = new RegistrationService(
                userService,
                emailValidation,
                verificationTokenService,
                emailSender,
                emailTemplate
        );
    }

    @Test
    void testSendVerificationEmail_WithValidEmail_ShouldReturnToken() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setFirstName("Nishan");

        when(emailValidation.test(user.getEmail())).thenReturn(true);
        when(userService.registerUser(user)).thenReturn("mockToken");
        when(emailTemplate.buildEmail(any(), any())).thenReturn("mockEmailContent");

        String token = registrationService.sendVerificationEmail(user);

        assertEquals("mockToken", token);
        verify(emailSender).sendEmail(eq("test@example.com"), eq("mockEmailContent"));
    }

    @Test
    void testSendVerificationEmail_WithInvalidEmail_ShouldThrowException() {
        User user = new User();
        user.setEmail("bademail.com");

        when(emailValidation.test(user.getEmail())).thenReturn(false);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> registrationService.sendVerificationEmail(user)
        );

        assertEquals("Invalid email", exception.getMessage());
    }
}

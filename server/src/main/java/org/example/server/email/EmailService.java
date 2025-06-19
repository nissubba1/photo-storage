package org.example.server.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements EmailSender {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    @Async
    public void sendEmail(String to, String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setText(email, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("Verify your email");
            mimeMessageHelper.setFrom("oauthverification@gmail.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("Failed to send email.", e);
        }
    }

}

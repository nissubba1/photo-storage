package org.example.server.user;

import jakarta.transaction.Transactional;
import org.example.server.dto.LoginRequest;
import org.example.server.token.VerificationToken;
import org.example.server.token.VerificationTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final VerificationTokenService verificationTokenService;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder
            , VerificationTokenService verificationTokenService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.verificationTokenService = verificationTokenService;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(rollbackOn = Exception.class)
    public String registerUser(User user) {
        Optional<User> userExists = userRepository.findByUsername(user.getUsername());

        if (userExists.isPresent()) {
            User currentUser = userExists.get();
            if (!currentUser.isEnabled()) {
                // Delete the existing token of the current user
                verificationTokenService.deleteUserToken(currentUser);

                String token = UUID.randomUUID().toString();
                VerificationToken verificationToken = new VerificationToken(
                        token,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusMinutes(15),
                        currentUser
                );
                verificationTokenService.saveVerificationToken(verificationToken);
                System.out.println("Reconfirmation email send out");
                return token;
            } else {
                throw new IllegalArgumentException("User already verified");
            }
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );
        System.out.println("User ID: " + user.getUserId() + " - User registered successfully");
        System.out.println("confirmation email send out");
        verificationTokenService.saveVerificationToken(verificationToken);
        return token;
    }

    public User findUserUsingUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public int enableUserAccount(String username) {
        return userRepository.enableAccount(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}

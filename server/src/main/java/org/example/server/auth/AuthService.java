// Updated 
package org.example.server.auth;

import org.example.server.dto.LoginRequest;
import org.example.server.user.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;

    public AuthService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public User authenticateUser(LoginRequest loginRequest) {
        Authentication authenticationRequest = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationRequest);
        User user = (User) authentication.getPrincipal();
        System.out.println("Username : " + user.getUsername() + " authenticated successfully");
        return user;
    }
}

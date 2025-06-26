package org.example.server.dto;

public class LoginResponse {
    String username;

    public LoginResponse(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

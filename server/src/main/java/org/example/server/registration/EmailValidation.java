package org.example.server.registration;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class EmailValidation implements Predicate<String> {
    @Override
    public boolean test(String email) {
        return email.contains("@");
    }
}

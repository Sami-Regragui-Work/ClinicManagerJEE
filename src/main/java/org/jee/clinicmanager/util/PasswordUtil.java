package org.jee.clinicmanager.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

public class PasswordUtil {
    private static final Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder("", 16, 185000, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);

    public static String hash(String password) {
        return encoder.encode(password);
    }

    public static boolean verify(String password, String hashedPassword) {
        return encoder.matches(password, hashedPassword);
    }
}

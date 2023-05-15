package com.ismailcet.SocialMedia.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public static String hashPassword(String password){
        return passwordEncoder.encode(password);
    }

    public static Boolean verifyPassword(String rawPassword, String encodePassword){
        return passwordEncoder.matches(rawPassword,encodePassword);
    }

}

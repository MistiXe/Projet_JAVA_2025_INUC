package com.example.demo;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordCrypter {

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(10));
    }

    public static boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}

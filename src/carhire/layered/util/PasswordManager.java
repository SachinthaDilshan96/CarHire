package carhire.layered.util;

import org.mindrot.BCrypt;

public class PasswordManager {

    public static String encryptPassword(String plainText){
        return BCrypt.hashpw(plainText,BCrypt.gensalt(5));
    }

    public static boolean validatePassword(String plainTextPassword, String hashedPassword){
        return BCrypt.checkpw(plainTextPassword,hashedPassword);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.backend.util;


import java.util.Base64;
//import org.mindrot.jbcrypt.BCrypt;
/**
 *
 * @author sofia
 */
public class PasswordUtil {

    public static String encodeBase64(String plain) {
        return Base64.getEncoder().encodeToString(plain.getBytes());
    }
    public static String decodeBase64(String encoded) {
        return new String(Base64.getDecoder().decode(encoded));
    }

    /**public static String hashBCrypt(String plain) {
        return BCrypt.hashpw(plain, BCrypt.gensalt(12));
    }
    public static boolean checkBCrypt(String plain, String hashed) {
        return BCrypt.checkpw(plain, hashed);
    }**/
}


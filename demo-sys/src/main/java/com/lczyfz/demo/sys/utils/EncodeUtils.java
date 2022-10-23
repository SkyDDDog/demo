package com.lczyfz.demo.sys.utils;

import com.lczyfz.edp.springboot.core.utils.Digests;
import com.lczyfz.edp.springboot.core.utils.Encodes;
import org.springframework.stereotype.Component;

@Component
public class EncodeUtils {

    public static String encodePassword(String plainPassword) {
        byte[] salt = Digests.generateSalt(8);
        byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, 1024);
        return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
    }

    public static boolean validatePassword(String plainPassword, String password) {
        String plain = Encodes.unescapeHtml(plainPassword);
        byte[] salt = Encodes.decodeHex(password.substring(0, 16));
        byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, 1024);

        return password.equals(Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword));
    }

}

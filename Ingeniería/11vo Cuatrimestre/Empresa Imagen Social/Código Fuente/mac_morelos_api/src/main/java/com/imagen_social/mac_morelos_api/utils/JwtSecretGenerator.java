package com.imagen_social.mac_morelos_api.utils;

import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;

public class JwtSecretGenerator {
    public static void main(String[] args) {
        Key key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256); 
        String secretKey = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("Clave Secreta JWT: " + secretKey);
    }
}

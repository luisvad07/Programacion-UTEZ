package utez.edu.mx.foodster.utils;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Base64;
import java.util.logging.Logger;

public class KeyGenerator {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(KeyGenerator.class.getName());
        var key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        var base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
        logger.info(base64Key);

    }
}
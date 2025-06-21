package utez.edu.mx.foodster.utils;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;
import org.springframework.stereotype.Component;

@Component
public class Encrypt {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 16 * 8; // in bits

    public static String encrypt(String value) throws GeneralSecurityException {
        Key generatedKey = generateKey();
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        byte[] initializationVector = new byte[12]; // GCM recommends 12 bytes
        new SecureRandom().nextBytes(initializationVector);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, initializationVector);
        cipher.init(Cipher.ENCRYPT_MODE, generatedKey, gcmParameterSpec);
        byte[] encryptedByteValue = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
        byte[] result = new byte[initializationVector.length + encryptedByteValue.length];
        System.arraycopy(initializationVector, 0, result, 0, initializationVector.length);
        System.arraycopy(encryptedByteValue, 0, result, initializationVector.length, encryptedByteValue.length);
        return Base64.getEncoder().encodeToString(result);
    }

    public static String decrypt(String value) throws GeneralSecurityException {
        Key generatedKey = generateKey();
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        byte[] decodedValue = Base64.getDecoder().decode(URLDecoder.decode(value, StandardCharsets.UTF_8));
        byte[] iv = Arrays.copyOfRange(decodedValue, 0, 12); // GCM recommends 12 bytes
        byte[] cipherText = Arrays.copyOfRange(decodedValue, 12, decodedValue.length);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, generatedKey, gcmParameterSpec);
        byte[] decryptedByteValue = cipher.doFinal(cipherText);
        return new String(decryptedByteValue, StandardCharsets.UTF_8);
    }

    private static Key generateKey() {
        String key = "nCnVo1kkhMfnfXkK";
        return new SecretKeySpec(key.getBytes(), ALGORITHM);
    }
    private Encrypt() {
    }
}
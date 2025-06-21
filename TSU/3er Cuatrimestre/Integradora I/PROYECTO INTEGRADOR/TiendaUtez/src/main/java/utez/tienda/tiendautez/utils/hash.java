package utez.tienda.tiendautez.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

public class hash {


    public static void main(String[] args) {
       hash hash = new hash();
       hash.getMD5("123");
    }


    public String getMD5(String pass) {
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] encBytes = md.digest(pass.getBytes());
            BigInteger numero = new BigInteger(1, encBytes);
            String encString = numero.toString(16);

            while (encString.length() < 32) {

                encString += "0";
            }
          //  System.out.println(encString);

            return encString;
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }
    /*public static void main(String[] args) {
        String secretKey = "123";
        hash mMain = new hash();
        String cadenaAEncriptar = JOptionPane.showInputDialog("Ingresa la cadena a encriptar");
        String cadenaEncriptada = mMain.ecnode(secretKey, cadenaAEncriptar);
        JOptionPane.showMessageDialog(null, "Cadena encriptada: " + cadenaEncriptada);
        String cadenaDesencriptada = mMain.deecnode(secretKey, cadenaEncriptada);
        JOptionPane.showMessageDialog(null, "Cadena desencriptada: " + cadenaDesencriptada);

    }*/

    public String ecnode(String secretKey, String cadena){
        String encriptacion = "";
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            byte[] llavePassword = md5.digest(secretKey.getBytes(StandardCharsets.UTF_8));
            byte[] BytesKey = Arrays.copyOf(llavePassword, 24);
            SecretKey key = new SecretKeySpec(BytesKey, "DESede");
            Cipher cifrado = Cipher.getInstance("DESede");
            cifrado.init(Cipher.ENCRYPT_MODE, key);
            byte[] plainTextBytes = cadena.getBytes(StandardCharsets.UTF_8);
            byte[] buf = cifrado.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            encriptacion = new String(base64Bytes);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Algo salió mal");
        }
        System.out.println(secretKey);
        return encriptacion;
    }

    public String deecnode(String secretKey, String cadenaEncriptada) {
        String desencriptacion = "";
        try {
            byte[] message = Base64.decodeBase64(cadenaEncriptada.getBytes(StandardCharsets.UTF_8));
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md5.digest(secretKey.getBytes(StandardCharsets.UTF_8));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);
            byte[] plainText = decipher.doFinal(message);
            desencriptacion = new String(plainText, StandardCharsets.UTF_8);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Algo salió mal");
        }
        return desencriptacion;
    }
}

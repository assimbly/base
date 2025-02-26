package org.assimbly.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public final class EncryptionUtil {

    private final StandardPBEStringEncryptor textEncryptor = new StandardPBEStringEncryptor();
    private static final int SALT_LENGTH = 16; // Length of the salt in bytes
    private static final int IV_LENGTH = 16; // Length of the IV in bytes
    private final String password;

    public EncryptionUtil(String password, String algorithm) {
        this.password = password;
        this.textEncryptor.setPassword(password);
        this.textEncryptor.setAlgorithm(algorithm);
        this.textEncryptor.setIvGenerator(new RandomIvGenerator());
    }

    public StandardPBEStringEncryptor getTextEncryptor() {
        return textEncryptor;
    }

    public String encrypt(String plainText) {
        // If the value is already encrypted, do not encrypt again and return
        if (plainText.startsWith("ENC(") && plainText.endsWith(")")) {
            return plainText;
        }

        // Generate random salt
        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);

        // Generate random IV
        byte[] iv = new byte[IV_LENGTH];
        new SecureRandom().nextBytes(iv);

        // Generate key from password and salt
        SecretKeySpec secretKey = new SecretKeySpec(generateKey(password, salt), "AES");

        // Encrypt the plain text
        byte[] encryptedBytes = encryptWithIv(secretKey, iv, plainText);

        // Encode the salt, IV, and encrypted text
        Base64.Encoder encoder = Base64.getEncoder();
        String encodedSalt = encoder.encodeToString(salt);
        String encodedIv = encoder.encodeToString(iv);
        String encodedEncryptedText = encoder.encodeToString(encryptedBytes);

        // Concatenate and return
        return String.format("ENC(%s|%s|%s)", encodedSalt, encodedIv, encodedEncryptedText);
    }

    public String decrypt(String encryptedText) {
        // Validate and extract components
        if (!encryptedText.startsWith("ENC(") || !encryptedText.endsWith(")")) {
            throw new IllegalArgumentException("Invalid encrypted text format.");
        }

        String contents = encryptedText.substring(4, encryptedText.length() - 1);
        String[] parts = contents.split("\\|");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid encrypted text format.");
        }

        Base64.Decoder decoder = Base64.getDecoder();
        byte[] salt = decoder.decode(parts[0]);
        byte[] iv = decoder.decode(parts[1]);
        byte[] encryptedBytes = decoder.decode(parts[2]);

        // Generate key from password and salt
        SecretKeySpec secretKey = new SecretKeySpec(generateKey(password, salt), "AES");

        // Decrypt the encrypted text
        return decryptWithIv(secretKey, iv, encryptedBytes);
    }


    private byte[] generateKey(String password, byte[] salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 10000, 256); // 10000 iterations, 256 bits
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return factory.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            throw new RuntimeException("Key generation failed.", e);
        }
    }

    private byte[] encryptWithIv(SecretKeySpec secretKey, byte[] iv, String plainText) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParams = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParams);
            return cipher.doFinal(plainText.getBytes("UTF-8")); // Use UTF-8 encoding
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed.", e);
        }
    }

    private String decryptWithIv(SecretKeySpec secretKey, byte[] iv, byte[] encryptedBytes) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParams = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParams);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, "UTF-8"); // Use UTF-8 encoding
        } catch (Exception e) {
            throw new RuntimeException("Decryption failed.", e);
        }
    }

}


// 代码生成时间: 2025-10-08 19:34:51
// DataEncryptionComponent.java
package com.yourcompany.tools;

import org.springframework.stereotype.Component;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Data Encryption Utility Component
 * This component provides utility methods for encrypting and decrypting data.
 */
@Component
public class DataEncryptionComponent {

    private static final String ALGORITHM = "AES";
    private static final String KEY_GENERATION_ALGORITHM = "AES";
    private static final int KEY_SIZE = 128;

    // Generate AES key
    public SecretKey generateAESKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_GENERATION_ALGORITHM);
        keyGenerator.init(KEY_SIZE, new SecureRandom());
        return keyGenerator.generateKey();
    }

    // Encrypt data
    public byte[] encryptData(byte[] data, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }

    // Decrypt data
    public byte[] decryptData(byte[] encryptedData, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(encryptedData);
    }

    // Convert secret key to Base64 encoded string
    public String convertSecretKeyToBase64(SecretKey secretKey) {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    // Convert Base64 encoded string to SecretKey
    public SecretKey convertBase64ToSecretKey(String base64Key) throws Exception {
        return new SecretKeySpec(Base64.getDecoder().decode(base64Key), ALGORITHM);
    }

    // Utility method to handle encryption errors
    public void handleEncryptionError(Exception e) {
        // Log error or handle it according to your error handling policy
        e.printStackTrace();
    }

    // Utility method to handle decryption errors
    public void handleDecryptionError(Exception e) {
        // Log error or handle it according to your error handling policy
        e.printStackTrace();
    }

    // Main method for testing purposes
    public static void main(String[] args) {
        try {
            DataEncryptionComponent encryptionComponent = new DataEncryptionComponent();

            // Generate AES key
            SecretKey key = encryptionComponent.generateAESKey();

            // Original data
            String originalData = "Hello World!";

            // Encrypt data
            byte[] encryptedData = encryptionComponent.encryptData(originalData.getBytes(), key);
            System.out.println("Encrypted data in bytes: " + new String(encryptedData));

            // Decrypt data
            byte[] decryptedData = encryptionComponent.decryptData(encryptedData, key);
            System.out.println("Decrypted data: " + new String(decryptedData));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
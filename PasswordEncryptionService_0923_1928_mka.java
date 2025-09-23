// 代码生成时间: 2025-09-23 19:28:04
import org.springframework.stereotype.Component;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

@Component
public class PasswordEncryptionService {

    // Key used for encryption and decryption
    private static final String ALGORITHM = "AES";
    private static final byte[] key = new byte[16];
    static { // Static block for key generation
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            keyGenerator.init(128); // 128 bits is recommended
            key = keyGenerator.generateKey().getEncoded();
        } catch (Exception e) {
            throw new RuntimeException("Error while generating key", e);
        }
    }

    private Key getKey() {
        return new SecretKeySpec(key, ALGORITHM);
    }

    /**
     * Encrypts the provided password.
     *
     * @param password The password to encrypt.
     * @return The encrypted password as a Base64 encoded string.
     */
    public String encryptPassword(String password) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getKey());
            byte[] encryptedBytes = cipher.doFinal(password.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error while encrypting password", e);
        }
    }

    /**
     * Decrypts the provided encrypted password.
     *
     * @param encryptedPassword The password to decrypt, expected to be a Base64 encoded string.
     * @return The decrypted password.
     */
    public String decryptPassword(String encryptedPassword) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getKey());
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedPassword);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error while decrypting password", e);
        }
    }
}

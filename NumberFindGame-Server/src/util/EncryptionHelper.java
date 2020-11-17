package util;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class EncryptionHelper {
    public static Cipher DCIPHER;
    public static Cipher CIPHER;

    public void createKey(int mode) {
        // Create key
        try {
            final char[] password = "secret_password".toCharArray();
            final byte[] salt = "random_salt".getBytes();
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec spec = new PBEKeySpec(password, salt, 1024, 128);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

            if (mode == Cipher.ENCRYPT_MODE) {
                CIPHER = Cipher.getInstance("AES");
                CIPHER.init(Cipher.ENCRYPT_MODE, secret);
            } else if (mode == Cipher.DECRYPT_MODE) {
                DCIPHER = Cipher.getInstance("AES");
                DCIPHER.init(mode, secret);
            }
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }
}

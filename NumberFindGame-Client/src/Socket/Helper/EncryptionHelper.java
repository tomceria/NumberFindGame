package Socket.Helper;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class EncryptionHelper {
    public static Cipher CIPHER;
    public static Cipher DCIPHER;
    public static PublicKey ClientPublicKey;
    public static PrivateKey ClientPrivateKey;
    public static SecretKey ClientSecretKey;
    public static PublicKey ServerPublicKey;
    public static Cipher ServerPublicKeyCipher;

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

    public void generateKeysForClient() {
        try {
            SecureRandom sr = new SecureRandom();
            // Thuật toán phát sinh khóa - Rivest Shamir Adleman (RSA)
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048, sr);
            // Phát sinh cặp khóa
            KeyPair kp = kpg.genKeyPair();
            // PublicKey
            ClientPublicKey = kp.getPublic();
            // PrivateKey
            ClientPrivateKey = kp.getPrivate();
        } catch (NoSuchAlgorithmException ignored) {
            // ignored
        }
    }

    public void generateServerPublicKey() {
        try {
            SecureRandom sr = new SecureRandom();
            sr.setSeed(159874);
            // Thuật toán phát sinh khóa - Rivest Shamir Adleman (RSA)
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048, sr);
            // Phát sinh cặp khóa
            KeyPair kp = kpg.genKeyPair();
            // PublicKey
            ServerPublicKey = kp.getPublic();
            // Sử dụng public key để mã hoá dữ liệu
            ServerPublicKeyCipher = Cipher.getInstance("RSA");
            ServerPublicKeyCipher.init(Cipher.ENCRYPT_MODE, ServerPublicKey);
            // sử dụng client private key để giải mã
//            DCIPHER = Cipher.getInstance("RSA");
//            DCIPHER.init(Cipher.DECRYPT_MODE, ClientPrivateKey);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ignored) {
            // ignored
        }
    }

    public void generateSecretKeyAndInitCipher() {
        // Create key
        try {
            final char[] password = "secret_password".toCharArray();
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec spec = new PBEKeySpec(password, salt, 1024, 128);
            SecretKey tmp = factory.generateSecret(spec);
            ClientSecretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            CIPHER = Cipher.getInstance("AES");
            CIPHER.init(Cipher.ENCRYPT_MODE, ClientSecretKey);
            DCIPHER = Cipher.getInstance("AES");
            DCIPHER.init(Cipher.DECRYPT_MODE, ClientSecretKey);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | InvalidKeySpecException ignored) {
            // ignored
        }
    }
}

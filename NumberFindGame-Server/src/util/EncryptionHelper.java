package util;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class EncryptionHelper {
    public static Cipher DCIPHER;
    public static Cipher CIPHER;
    public static PublicKey ServerPublicKey;
    public static PrivateKey ServerPrivateKey;

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

    public void generateKeysForServer() {
        try {
            SecureRandom sr = new SecureRandom();
            // Thuật toán phát sinh khóa - Rivest Shamir Adleman (RSA)
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(4096, sr);
            // Phát sinh cặp khóa
            KeyPair kp = kpg.genKeyPair();
            // PublicKey
            ServerPublicKey = kp.getPublic();

            // PrivateKey
            ServerPrivateKey = kp.getPrivate();
            // Lưu Public Key
//            FileOutputStream fos = new FileOutputStream("publickey.txt");
//            fos.write(ServerPublicKey.getEncoded());
//            fos.close();

            // Lưu Private Key
//            fos = new FileOutputStream("privatekey.txt");
//            fos.write(ServerPrivateKey.getEncoded());
//            fos.close();
            // sử dụng server private key để giải mã
            DCIPHER = Cipher.getInstance("RSA");
            DCIPHER.init(Cipher.DECRYPT_MODE, ServerPrivateKey);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ignored) {

        }
    }

    public void getKeysFromFile() {
        try {
            // Đọc file chứa public key
            FileInputStream fis = new FileInputStream("publickey.txt");
            byte[] b = new byte[fis.available()];
            fis.read(b);
            fis.close();

            // Tạo public key
            X509EncodedKeySpec spec = new X509EncodedKeySpec(b);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            ServerPublicKey = factory.generatePublic(spec);

            // Đọc file chứa private key
            FileInputStream fis2 = new FileInputStream("privatekey.txt");
            byte[] b2 = new byte[fis2.available()];
            fis2.read(b2);
            fis2.close();

            // Tạo private key
            PKCS8EncodedKeySpec spec2 = new PKCS8EncodedKeySpec(b2);
            KeyFactory factory2 = KeyFactory.getInstance("RSA");
            ServerPrivateKey = factory2.generatePrivate(spec2);

            // sử dụng server private key để giải mã
            DCIPHER = Cipher.getInstance("RSA");
            DCIPHER.init(Cipher.DECRYPT_MODE, ServerPrivateKey);
        } catch (NoSuchAlgorithmException | IOException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }
}

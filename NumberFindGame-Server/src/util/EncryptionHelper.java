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
    /**
     * Chỉ dùng để giải mã Key của Request đầu
     * Main CIPHER và DCIPHER là các instance tạo trong các hàm sealObject, unsealObject
     */
    public static Cipher DCIPHER;
    public static PublicKey ServerPublicKey;
    public static PrivateKey ServerPrivateKey;

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

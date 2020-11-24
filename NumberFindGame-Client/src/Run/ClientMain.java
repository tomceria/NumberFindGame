package Run;

import Socket.Helper.EncryptionHelper;

import javax.crypto.Cipher;

public class ClientMain {
    public static final String APP_NAME = "Number Find Game";

    public static void main (String[] args) {
        EncryptionHelper encryptionHelper = new EncryptionHelper();
        // tạo server public key
//        encryptionHelper.generateServerPublicKey();
        encryptionHelper.getServerKeysFromFile();
        // tạo client public và private key
//        encryptionHelper.generateKeysForClient();
        // tạo client secret key
        encryptionHelper.generateSecretKeyAndInitCipher();

        GameMain.init();
    }
}

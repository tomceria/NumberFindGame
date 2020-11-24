package Run;

import Socket.Helper.EncryptionHelper;

import javax.crypto.Cipher;

public class ClientMain {
    public static final String APP_NAME = "Number Find Game";

    public static void main (String[] args) {
        EncryptionHelper encryptionHelper = new EncryptionHelper();
        encryptionHelper.generateKeysForClient();
        encryptionHelper.generateServerPublicKey();

        GameMain.init();
    }
}

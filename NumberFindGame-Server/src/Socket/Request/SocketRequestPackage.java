package Socket.Request;

import javax.crypto.SealedObject;
import java.io.Serializable;

public class SocketRequestPackage implements Serializable {
    public SealedObject data;
    public byte[] encryptedSecretKey;

    public SocketRequestPackage(SealedObject data, byte[] encryptedSecretKey) {
        this.data = data;
        this.encryptedSecretKey = encryptedSecretKey;
    }
}

package Socket;

import Socket.Encryption.ISecretObject;
import Socket.Encryption.SecretObjectImpl;
import Socket.Helper.EncryptionHelper;
import Socket.Request.SocketRequest;
import Socket.Request.SocketRequest_AccessLogin;
import Socket.Response.SocketResponse;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SealedObject;
import javax.security.sasl.AuthenticationException;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private static Socket socket;                                // Chỉ được tương tác với socket thông qua Client
    private static ObjectInputStream input;                   // input và output được đặc static để các hàm BUS truy xuất
    private static ObjectOutputStream output;
    private static ClientSocketProcess persistSocketProcess;

    public void performPersistSocketConnection(String hostname, int port, String username, String password) throws IOException {
        this.start(hostname, port);

        SocketRequest_AccessLogin authenticationRequest = new SocketRequest_AccessLogin(username, password);
        authenticationRequest.publicKey = EncryptionHelper.ClientPublicKey;
        sendRequest(authenticationRequest);                                          // Gửi thông tin đăng nhập để server duyệt
        SocketResponse authenticationResponse = receiveResponse();
        System.out.println("SERVER AUTH MESSAGE: " + authenticationResponse.getMessage());

        switch (authenticationResponse.getStatus()) {
            case SUCCESS: {                                                           // Đăng nhập thành công => Kết nối
                System.out.println("CLIENT: Logged in successfully.");
                persistSocketProcess = new ClientSocketProcess(this);
                persistSocketProcess.start();
                break;
            }
            case FAILED: {
                System.out.println("CLIENT: Disconnected.");
                this.close();
                throw new AuthenticationException(authenticationResponse.getMessage());
            }
        }
    }

    public Object performOneTimeSocketRequest(String hostname, int port, SocketRequest requestRaw) throws IOException {
        this.start(hostname, port);

        Object result = null;

        switch (requestRaw.getAction()) {
            // Sử dụng case cho các ACTION có xử lý đặc biệt. Hiện tại thì không có
            default: {
                sendRequest(requestRaw);

                SocketResponse resultRaw = this.receiveResponse();
                if (resultRaw == null) {
                    result = false;
                    break;
                }

                switch (resultRaw.getAction()) {
                    case MSG: {
                        switch (resultRaw.getStatus()) {
                            case SUCCESS: {
                                result = true;
                                break;
                            }
                            case FAILED: {
                                this.close();
                                throw new AuthenticationException(resultRaw.getMessage());
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }

        this.close();

        return result;
    }

    public void sendRequest(SocketRequest request) {
        if (this.isInitiated() == false) {
            throw new RuntimeException("Client has not connected to server.");
        }

        try {
//            Client.output.writeObject(sealObject(request));
            Client.output.writeObject(encryptObject(request));
            Client.output.flush();
        } catch (IOException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }

    protected SocketResponse receiveResponse() {
        if (this.isInitiated() == false) {
            throw new RuntimeException("Client has not connected to server.");
        }

        SocketResponse response = null;
        try {
//            response = (SocketResponse) Client.input.readObject();
//            response = unsealObject(Client.input.readObject());
            response = decryptObject(Client.input.readObject());
        } catch (NullPointerException | EOFException e) {
            // Disconnect
            close();
        } catch (IOException | ClassNotFoundException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return response;
    }

    // mã hóa request bằng server public key
    protected SealedObject encryptObject(Object o) throws IOException, IllegalBlockSizeException {
        ISecretObject secretObject = new SecretObjectImpl((SocketRequest) o);
        SealedObject so = new SealedObject(secretObject, EncryptionHelper.CIPHER);
        return so;
    }

    // giải mã response bằng client private key
    protected SocketResponse decryptObject(Object o) throws IOException, IllegalBlockSizeException, BadPaddingException, ClassNotFoundException {
        SealedObject s = (SealedObject) o;
        ISecretObject decryptedSecretObject = (ISecretObject) s.getObject(EncryptionHelper.DCIPHER);
        return decryptedSecretObject.getSecretResponse();
    }

    // mã hóa request trước khi gửi đi
    protected SealedObject sealObject(Object o) throws IOException, IllegalBlockSizeException {
        ISecretObject secretObject = new SecretObjectImpl((SocketRequest) o);
        SealedObject so = new SealedObject(secretObject, EncryptionHelper.CIPHER);
        return so;
    }

    // giải mã response nhận về
    protected SocketResponse unsealObject(Object o) throws ClassNotFoundException, BadPaddingException, IllegalBlockSizeException, IOException {
        SealedObject s = (SealedObject) o;
        ISecretObject decryptedSecretObject = (ISecretObject) s.getObject(EncryptionHelper.DCIPHER);
        return decryptedSecretObject.getSecretResponse();
    }

    public void close() {
        try {
//            if (persistSocketProcess != null && persistSocketProcess.isAlive()) {
//                persistSocketProcess.interrupt();
//            }
            if (input != null) {
                input.close();
            }
            if (output != null) {
                output.close();
            }
            if (socket != null) {
                socket.close();
            }

            socket = null;
            output = null;
            input = null;
            persistSocketProcess = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Privates

    private void start(String hostname, int port) throws IOException {
        if (this.isInitiated()) {
            throw new RuntimeException("Client has already been initiated");
        }

        socket = new Socket(hostname, port);                       // Thực hiện kết nối đến server với hostname xác định
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
    }

    private boolean isInitiated() {
        boolean result = true;

        if (socket == null && input == null && output == null) {
            result = false;
        } else if (socket == null || input == null || output == null) {
            throw new RuntimeException("One of the socket's properties is destructed. This should not happen.");
        }

        return result;
    }
}

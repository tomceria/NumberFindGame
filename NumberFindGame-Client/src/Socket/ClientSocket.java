package Socket;

import Socket.Request.SocketRequest;
import Socket.Request.SocketRequest_Login;
import Socket.Response.SocketResponse;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSocket {
    private static Socket socket;                                // Chỉ được tương tác với socket thông qua ClientSocket
    private static ObjectInputStream input;                   // input và output được đặc static để các hàm BUS truy xuất
    private static ObjectOutputStream output;

    public static void connect(String hostname, int port, String username, String password) throws IOException, ClassNotFoundException {
        socket = new Socket(hostname, port);                       // Thực hiện kết nối đến server với hostname xác định
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());

        SocketRequest_Login authenticationRequest = new SocketRequest_Login(username, password);
        sendRequest(authenticationRequest);                                          // Gửi thông tin đăng nhập để server duyệt
        SocketResponse authenticationResponse = receiveResponse();
        System.out.println("SERVER: " + authenticationResponse.getMessage());

        switch (authenticationResponse.getStatus()) {
            case SUCCESS: {                                                           // Đăng nhập thành công => Kết nối
                System.out.println("CLIENT: Connected");
                sendRequest(new SocketRequest(SocketRequest.Action.MESSAGE, "Test sending Request while waiting for Response"));
                ClientSocketProcess process = new ClientSocketProcess();
                process.start();
                break;
            }
            case FAILED: {
                System.out.println("CLIENT: Disconnected");
                close();
                return;
            }
        }
    }

    public static void sendRequest(SocketRequest request) {
        try {
            ClientSocket.output.writeObject(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SocketResponse receiveResponse() {
        SocketResponse response = null;
        try {
            response = (SocketResponse) ClientSocket.input.readObject();
        } catch (NullPointerException | EOFException e) {
            // Disconnect
            close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static void close() {
        try {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package Socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSocket {
    private static Socket socket;                                // Chỉ được tương tác với socket thông qua ClientSocket
    public static ObjectInputStream input;                   // input và output được đặc static để các hàm BUS truy xuất
    public static ObjectOutputStream output;

    public static void connect(String hostname, int port, String username, String password) throws IOException, ClassNotFoundException {
        socket = new Socket(hostname, port);                       // Thực hiện kết nối đến server với hostname xác định
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());

        SocketRequest_Login authenticationRequest = new SocketRequest_Login(username, password);
        output.writeObject(authenticationRequest);                            // Gửi thông tin đăng nhập để server duyệt
        SocketResponse authenticationResponse = (SocketResponse) input.readObject();

        switch (authenticationResponse.getStatus()) {
            case SUCCESS: {                                                           // Đăng nhập thành công => Kết nối
                System.out.println("CLIENT: Connected");
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

    public static void close() {
        try {
            input.close();
            output.close();
            socket.close();
            socket = null;
            output = null;
            input = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

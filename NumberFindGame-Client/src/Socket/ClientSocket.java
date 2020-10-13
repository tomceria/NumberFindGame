package Socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSocket {
    static Socket socket;
    static ObjectInputStream input;
    static ObjectOutputStream output;

    public static void connect(String hostname, String username, String password) throws IOException, ClassNotFoundException {
        socket = new Socket(hostname, 54321);                 // Thực hiện kết nối đến server với hostname xác định
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());

        output.writeObject(new LogInRequest(username, password));             // Gửi thông tin đăng nhập để server duyệt
        SocketResponse result = (SocketResponse) input.readObject();
        System.out.println(result.getResult().toString());
    }

    public static void close() throws IOException {
        input.close();
        output.close();
        socket.close();
        socket = null;
        output = null;
        input = null;
    }
}

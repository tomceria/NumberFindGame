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

        switch (result.getStatus()) {
            case SUCCESS: {                                                           // Đăng nhập thành công => Kết nối
                System.out.println("Login successfully!");
                ClientSocketProcess process = new ClientSocketProcess();
                process.start();
                break;
            }
            case END: {
                System.out.println("Goodbye");
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

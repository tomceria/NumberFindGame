package Socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    // TODO: Placeholder - REMOOOOOVE
    public static void main(String[] args) {
        Socket socket = null;

        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        try {
            ServerSocket server = new ServerSocket(54321);
            socket = server.accept();

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            SocketRequest requestObj = (SocketRequest) in.readObject();
            System.out.println(requestObj.action);

            out.close();
            in.close();
            socket.close();
            server.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
package Socket;

import sun.rmi.runtime.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    // TODO: Placeholder - REMOOOOOVE
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(54321);
            while (true) {
                Socket client = server.accept();
                ClientHandler clientHandler = new ClientHandler(client);
                clientHandler.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
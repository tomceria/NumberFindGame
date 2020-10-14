package Socket;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ClientHandler extends Thread {
    Socket client;
    ObjectInputStream input;
    ObjectOutputStream output;

    public ClientHandler(Socket client) throws IOException {
        this.client = client;
        this.output = new ObjectOutputStream(client.getOutputStream());
        this.input = new ObjectInputStream(client.getInputStream());
    }

    @Override
    public void run() {
        try {
            while (true) {
                SocketRequest requestRaw = (SocketRequest) input.readObject();
                switch (requestRaw.getAction()) {
                    case LOGIN: {
                        LogInRequest request = (LogInRequest) requestRaw;
                        System.out.println(request.username + "; " + request.password);
                        output.writeObject(new SocketResponse(SocketResponse.Status.SUCCESS));
                        break;
                    }
                    case DISCONNECT: {
                        output.writeObject(new SocketResponse(SocketResponse.Status.END));
                        closeSocket();
                        break;
                    }
                }
            }
        } catch (EOFException | SocketException e) {
            System.out.println("Disconnected!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void closeSocket() throws IOException {
        output.close();
        input.close();
        client.close();
    }
}

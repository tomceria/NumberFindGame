package Socket;

import java.io.*;
import java.net.Socket;

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
        while (true) {
            try {
                SocketRequest requestRaw = (SocketRequest) input.readObject();
                switch (requestRaw.getAction()) {
                    case "REQ_LOGIN": {
                        LogInRequest request = (LogInRequest) requestRaw;
                        System.out.println(request.username + "; " + request.password);
                        output.writeObject(new SocketResponse(SocketResponse.Status.SUCCESS));
                        break;
                    }
                    case "REQ_DISCONNECT": {
                        closeSocket();
                        break;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void closeSocket() throws IOException {
        output.close();
        input.close();
        client.close();
    }
}

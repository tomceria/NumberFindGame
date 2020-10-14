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

    public Socket getClient() {
        return client;
    }

    @Override
    public void run() {
        try {
            while (true) {
                SocketRequest requestRaw = (SocketRequest) input.readObject();
                SocketResponse response;

                if (requestRaw.getMessage() != null) {
                    System.out.println(requestRaw.getMessage());
                }

                switch (requestRaw.getAction()) {
                    case LOGIN: {
                        SocketRequest_Login request = (SocketRequest_Login) requestRaw;
                        // TODO: Thực hiện kiểm tra thông tin đăng nhập
                        if (request.username.equals("luuminhhoang")) {
                            System.out.println(String.format("%s logged in.", request.username));
                            response = new SocketResponse(SocketResponse.Status.SUCCESS, "Successfully logged in!");
                        } else {
                            response = new SocketResponse(SocketResponse.Status.FAILED, "Login failed.");
                        }
                        output.writeObject(response);
                        break;
                    }
                    case DISCONNECT: {
                        response = new SocketResponse(SocketResponse.Status.END);
                        output.writeObject(response);
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

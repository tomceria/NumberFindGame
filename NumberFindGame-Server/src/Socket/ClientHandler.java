package Socket;

import util.NotifyingThread;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.UUID;

public class ClientHandler {
    UUID id;
    Socket client;
    ObjectInputStream input;
    ObjectOutputStream output;
    ClientThread clientHandleThread;

    public ClientHandler(Socket client, UUID id, ClientManager clientManager) throws IOException {
        this.id = id;
        this.client = client;
        this.output = new ObjectOutputStream(client.getOutputStream());
        this.input = new ObjectInputStream(client.getInputStream());

        this.clientHandleThread = new ClientThread() {
            @Override
            public void doRun() {
                try {
                    while (true) {
                        SocketRequest requestRaw = (SocketRequest) input.readObject();
                        new RequestHandler(requestRaw).init();
                    }
                } catch (EOFException | SocketException e) {
                    System.out.println("Disconnected!");
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        };
        this.clientHandleThread.setUuid(id);
        this.clientHandleThread.addListener(clientManager);  // Khi clientHandleThread kết thúc, sẽ báo cho listener (clientManager) để thực hiện xoá khỏi danh sách clientConnections
    }

    public Socket getClient() {
        return client;
    }

    protected void init() {
        clientHandleThread.start();
    }

    // Client Socket functions

    public void sendRequest(SocketRequest requestRaw) throws IOException {
        output.writeObject(requestRaw);
    }

    public void closeSocket() throws IOException {
        output.close();
        input.close();
        client.close();
    }

    //

    abstract class ClientThread extends NotifyingThread {
        private UUID uuid;

        public UUID getUuid() {
            return uuid;
        }
        public void setUuid(UUID uuid) {
            this.uuid = uuid;
        }
    }
}

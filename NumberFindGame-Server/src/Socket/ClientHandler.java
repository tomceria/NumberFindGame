package Socket;

import util.IThreadCompleteListener;
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
                        SocketResponse response;

                        if (requestRaw.getMessage() != null) {
                            System.out.println(requestRaw.getMessage());
                        }

                        switch (requestRaw.getAction()) {
                            case LOGIN: {
                                SocketRequest_Login request = (SocketRequest_Login) requestRaw;
                                // TODO: Thực hiện kiểm tra thông tin đăng nhập
                                if (request.username.equals("luuminhhoang")) { System.out.println(String.format("%s logged in.", request.username));
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
        };
        this.clientHandleThread.setUuid(id);
        this.clientHandleThread.addListener(clientManager);  // Khi clientHandleThread kết thúc, sẽ báo cho listener (clientManager) để thực hiện xoá khỏi danh sách clientConnections
    }

    public void init() {
        clientHandleThread.start();
    }

    public Socket getClient() {
        return client;
    }

    public NotifyingThread getClientHandleThread() {
        return clientHandleThread;
    }

    public void closeSocket() throws IOException {
        output.close();
        input.close();
        client.close();
    }

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

package Socket;

import bus.PlayerBUS;
import dto.PlayerDTO;
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

    boolean isLoggedIn = false;
    IClientIdentifier clientIdentifier;
    ClientManager clientManager;

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
                        if (isLoggedIn == false) {
                            if (requestRaw.getAction().equals(SocketRequest.Action.LOGIN)) {
                                if (performValidateClient(requestRaw)) {
                                    isLoggedIn = true;
                                    onSuccessConnection();
                                }
                            } else {
                                break;  // Yêu cầu ĐẦU TIÊN không hợp lệ => Thoát khỏi vòng lặp => Kết thúc Thread => Disconnect
                            }
                        } else if (isLoggedIn && clientIdentifier != null) {                  // Đã đăng nhập => Xử lý MỌI yêu cầu
                            new RequestHandler(requestRaw, clientIdentifier, ClientHandler.this).init();  // RequestHandler xử lý yêu cầu BẤT ĐỒNG BỘ, trong lúc đó tiếp tục nhận yêu cầu từ client
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
        this.clientManager = clientManager;
    }

    public Socket getClient() {
        return client;
    }
    public ClientManager getClientManager() {
        return clientManager;
    }

    public IClientIdentifier getClientIdentifier() {
        return clientIdentifier;
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

    protected void init() {
        clientHandleThread.start();
    }

    private boolean performValidateClient(SocketRequest requestRaw) {
        boolean isValidated = false;
        SocketRequest_Login request = (SocketRequest_Login) requestRaw;
        PlayerBUS playerBUS = new PlayerBUS();

        if (playerBUS.login(request.username, request.password)) {
            ClientHandler.this.clientIdentifier = playerBUS.getOneByUsername(request.username);
            isValidated = true;
        }

        return isValidated;
    }

    private void onSuccessConnection() {
        ((GameServer) clientManager.getServer()).gameRoom.joinRoom((PlayerDTO) clientIdentifier);
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

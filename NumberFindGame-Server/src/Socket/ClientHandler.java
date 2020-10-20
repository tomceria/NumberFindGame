package Socket;

import Socket.Request.SocketRequest;
import Socket.Request.SocketRequest_Login;
import Socket.Response.SocketResponse;
import bus.PlayerBUS;
import dto.MatchPlayer;
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
    IClientIdentifier clientIdentifier; // inherits MatchPlayer
    ClientManager clientManager;  // PARENT

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
                        SocketRequest requestRaw = receiveRequest();
                        if (isLoggedIn == false) {
                            if (requestRaw.getAction().equals(SocketRequest.Action.LOGIN)) {
                                if (performValidateClient(requestRaw)) {
                                    isLoggedIn = true;
                                    sendResponse(new SocketResponse(SocketResponse.Status.SUCCESS, "Logged in."));
                                    onSuccessConnection();
                                } else {
                                    sendResponse(new SocketResponse(SocketResponse.Status.FAILED, "Invalid login credentials."));
                                }
                            } else {
                                sendResponse(new SocketResponse(SocketResponse.Status.FAILED, "Invalid access request."));
                                break;  // Yêu cầu ĐẦU TIÊN không hợp lệ => Thoát khỏi vòng lặp => Kết thúc Thread => Disconnect
                            }
                        } else if (isLoggedIn && clientIdentifier != null) {        // Đã đăng nhập => Xử lý MỌI yêu cầu
                            new RequestHandler(requestRaw, clientIdentifier, ClientHandler.this).init();  // RequestHandler xử lý yêu cầu BẤT ĐỒNG BỘ, trong lúc đó tiếp tục nhận yêu cầu từ client
                        }
                    }
                } catch (EOFException | SocketException e) {
                    // Disconnect
                    System.out.println(String.format("Client '%s' disconnected.", ClientHandler.this.id));
                    closeSocket();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        };
        this.clientHandleThread.setUuid(id);
        this.clientHandleThread.addListener(clientManager);  // Khi clientHandleThread kết thúc, sẽ báo cho listener (clientManager) để thực hiện xoá khỏi danh sách clientConnections
        this.clientManager = clientManager;
    }

    public UUID getId() {
        return id;
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

    protected void sendResponse(SocketResponse response) {
        try {
            output.writeObject(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected SocketRequest receiveRequest() throws IOException, ClassNotFoundException {
        SocketRequest request = null;
        request = (SocketRequest) input.readObject();
        return request;
    }

    protected void closeSocket() {
        try {
            if (input != null) {
                input.close();
            }
            if (output != null) {
                output.close();
            }
            if (client != null) {
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //

    protected void init() {
        clientHandleThread.start();
    }

    private boolean performValidateClient(SocketRequest requestRaw) {
        boolean isValidated = false;
        SocketRequest_Login request = (SocketRequest_Login) requestRaw;

        // TODO: BUSINESS LOGIC
        PlayerBUS playerBUS = new PlayerBUS();
        if (playerBUS.login(request.username, request.password)) {
            PlayerDTO player = playerBUS.getOneByUsername(request.username);
            ClientHandler.this.clientIdentifier = new MatchPlayer(player);
            isValidated = true;
        }

        return isValidated;
    }

    private void onSuccessConnection() {
        ((GameServer) clientManager.getServer()).joinGame(this);
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

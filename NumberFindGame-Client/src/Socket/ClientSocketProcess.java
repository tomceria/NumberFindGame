package Socket;

import Socket.Response.SocketResponse;

public class ClientSocketProcess extends Thread {
    Client client;  // PARENT
    boolean isRunning = true;

    public ClientSocketProcess(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        while(isRunning) {
            SocketResponse result = client.receiveResponse();
            if (result == null) {
                continue;
            }

            switch (result.getAction()) {
                case MSG: {
                    System.out.println(String.format("[Server] : %s", result.getMessage()));
                    break;
                }
                case UPDATE_GAMEROOM: {
                    System.out.println("Updating game room props. --- " + result.getMessage()); // TODO: Placeholder
                    break;
                }
                case UPDATE_PLAYERJOINROOM: {
                    System.out.println("You have joined the game. --- " + result.getMessage()); // TODO: Placeholder
                    break;
                }
                case NET_CLOSE: {
                    System.out.println(result.getMessage());
                    client.close();
                    isRunning = false;
                    break;
                }
            }
        }
    }
}

package Socket;

import Socket.Response.SocketResponse;

import java.io.EOFException;
import java.io.IOException;

public class ClientSocketProcess extends Thread {
    boolean isRunning = true;

    @Override
    public void run() {
        while(isRunning) {
            SocketResponse result = ClientSocket.receiveResponse();
            if (result == null) {
                continue;
            }

            switch (result.getAction()) {
                case MSG: {
                    System.out.println(String.format("[Server] : %s", result.getMessage()));
                    break;
                }
                case UPDATE_GAMEROOM: {
                    System.out.println("Updating game room props. " + result.getMessage()); // TODO: Placeholder
                    break;
                }
                case UPDATE_PLAYERJOINROOM: {
                    System.out.println("You have joined the game. " + result.getMessage()); // TODO: Placeholder
                    break;
                }
                case NET_CLOSE: {
                    System.out.println(result.getMessage());
                    ClientSocket.close();
                    isRunning = false;
                    break;
                }
            }
        }
    }
}

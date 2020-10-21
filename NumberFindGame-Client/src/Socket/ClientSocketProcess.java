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

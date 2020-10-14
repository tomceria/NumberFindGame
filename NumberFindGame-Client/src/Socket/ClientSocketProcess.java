package Socket;

import java.io.EOFException;
import java.io.IOException;

public class ClientSocketProcess extends Thread {
    @Override
    public void run() {
        while(true) {
            try {
                SocketResponse result = (SocketResponse) ClientSocket.input.readObject();
                switch (result.getStatus()) {}                                                      // TODO: Xử lý input
            } catch (EOFException e) {                                                          // Ngắt kết nối bất chợt
                ClientSocket.close();
                break;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

package BUS;

import Models.Player;
import Socket.*;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class SocketBUS {
    // TODO: Placeholder - Dump Players data
    private ArrayList<Player> DUMPPLAYERS = new ArrayList<Player>() {
        {
            this.add(new Player(1, "luuminhhoang", "lala@gmail.com", "Hoàng", "Lưu"));
            this.add(new Player(2, "vohoanghuy", "lala2@gmail.com", "Huy", "Võ"));
            this.add(new Player(3, "huathianhngan", "lala3@gmail.com", "Ngân", "Hứa"));
            this.add(new Player(4, "tranthuythuyan", "lala4@gmail.com", "An", "Trần"));
        }
    };

    public void establishConnection() {
        Socket socket = null;

        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        try {
            socket = new Socket("127.0.0.1", 54321);

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            LogInRequest request = new LogInRequest(DUMPPLAYERS.get(0).getUsername(), "123456");
            out.writeObject(request);
            while (true) {

            }
//
//            out.close();
//            in.close();
//            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

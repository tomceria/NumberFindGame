package Run;

import Socket.Client;
import Socket.GameClient;
import bus.LoginBUS;
import bus.ViewBUS;

import javax.swing.*;

public class GameMain {
    public static Client client;

    // Functions

    protected static void init() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        GameMain.client = new GameClient();

        ViewBUS.configureWindow(new JFrame(ClientMain.APP_NAME));
        ViewBUS.gotoLoginView();
    }
}

package Run;

import Socket.GameClient;
import bus.ViewBUS;

import javax.swing.*;
import java.util.Scanner;

public class GameMain {
    private String netHostname;
    private int netPort;

    public GameMain() {
        this.netHostname = "127.0.0.1";
        this.netPort = 54321;
    }

    // Functions

    protected void init() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        ViewBUS.configureWindow(new JFrame(ClientMain.APP_NAME));
        ViewBUS.gotoLoginView();

        // TODO: Migrate to GUI

        Scanner scan = new Scanner(System.in);
        System.out.print("username: ");
        String username = scan.nextLine();
        System.out.print("password: ");
        String password = scan.nextLine();
        try {
            new GameClient().start(
                this.netHostname,
                54321,
                username,
                password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Properties

    public String getNetHostname() {
        return netHostname;
    }
    public void setNetHostname(String netHostname) {
        this.netHostname = netHostname;
    }
}

package Run;

import Socket.Client;
import GUI.GameView;
import Socket.GameClient;
import bus.GameBUS;
import bus.ViewBUS;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Scanner;

public class GameMain {
    public static String appName = "Number Find Game";

    protected void init() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        ViewBUS.configureWindow(new JFrame(this.appName));

        // TODO: Migrate to GUI

        Scanner scan = new Scanner(System.in);
        System.out.print("username: ");
        String username = scan.nextLine();
        System.out.print("password: ");
        String password = scan.nextLine();
        try {
            new GameClient().start(
                "192.168.1.101",
                54321,
                username,
                password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

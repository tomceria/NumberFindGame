package Run;

import Socket.Client;
import GUI.GameView;
import Socket.GameClient;
import bus.GameBUS;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Scanner;

public class ClientMain {
    public static String appName = "Number Find Game";
    public static JFrame mainFrame;

    private static Client client;

    public static void main (String[] args) throws IOException {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO: TEMPORARY - Migrate to GUI
        System.out.print("Test Menu:\n(1) Core Game\n(2) Connect to Server\nChoose: ");
        Scanner scan = new Scanner(System.in);
        int choose = scan.nextInt();
        scan.nextLine();

        switch (choose) {
            case 2: {
                System.out.print("username: "); String username = scan.nextLine();
                System.out.print("password: "); String password = scan.nextLine();
                try {
                    GameClient gameClient = new GameClient(
                            "127.0.0.1",
                            54321,
                            username,
                            password);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void configureWindow(JFrame mainFrame) {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1024, 768 + 22);
        mainFrame.setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation(
                dim.width/2-mainFrame.getSize().width/2,
                dim.height/2-mainFrame.getSize().height/2);
    }

    public static void gotoGameView(JFrame mainFrame, GameBUS gameBUS) {
        mainFrame.setContentPane(new GameView(gameBUS).contentPane);
        mainFrame.setVisible(true);
    }
}

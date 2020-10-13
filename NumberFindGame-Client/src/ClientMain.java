import Socket.ClientSocket;
import GUI.GameView;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {
    static String appName = "Number Find Game";

    public static void main (String[] args) throws IOException {
        System.out.print("Test Menu:\n(1) Core Game\n(2) Connect to Server\nChoose: ");
        Scanner scan = new Scanner(System.in);
        int choose = scan.nextInt();

        switch (choose) {
            case 1: {
                JFrame mainFrame = new JFrame(appName);
                configureWindow(mainFrame);
                break;
            }
            case 2: {
                try {
                    ClientSocket.connect("127.0.0.1", "luuminhhoang", "123456");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void configureWindow(JFrame mainFrame) {
        mainFrame.setContentPane(new GameView().contentPane);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1024, 768 + 22);
        mainFrame.setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation(
                dim.width/2-mainFrame.getSize().width/2,
                dim.height/2-mainFrame.getSize().height/2);
        mainFrame.setVisible(true);
    }
}

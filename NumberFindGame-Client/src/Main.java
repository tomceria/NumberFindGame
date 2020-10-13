import BUS.SocketBUS;
import GUI.GameView;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class Main {
    static String appName = "Number Find Game";

    public static void main (String[] args) {
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
                SocketBUS socketBUS = new SocketBUS();
                socketBUS.establishConnection();
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

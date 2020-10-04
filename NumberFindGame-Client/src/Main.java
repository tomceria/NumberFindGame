import GUI.GameView;

import javax.swing.*;

public class Main {
    static String appName = "Number Find Game";

    public static void main (String[] args) {
        System.out.print("Hello there");

        JFrame mainFrame = new JFrame(appName);
        mainFrame.setContentPane(new GameView().contentPane);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1024, 768);
        mainFrame.setVisible(true);
    }
}

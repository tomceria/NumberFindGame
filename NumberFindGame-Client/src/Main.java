import GUI.GameView;

import javax.swing.*;
import java.awt.*;

public class Main {
    static String appName = "Number Find Game";

    public static void main (String[] args) {
        JFrame mainFrame = new JFrame(appName);
        configureWindow(mainFrame);
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

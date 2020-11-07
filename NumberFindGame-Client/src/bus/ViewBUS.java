package bus;

import GUI.GameView;
import GUI.LoginView;

import javax.swing.*;
import java.awt.*;

public class ViewBUS {
    private static JFrame mainFrame;

    public static void configureWindow(JFrame mainFrame) {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1024, 768 + 22);
        mainFrame.setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation(
                dim.width / 2 - mainFrame.getSize().width / 2,
                dim.height / 2 - mainFrame.getSize().height / 2);

        ViewBUS.setMainFrame(mainFrame);
    }

    public static void gotoLoginView(LoginBUS loginBUS) {
        mainFrame.setContentPane(new LoginView(loginBUS).getContentPane());
        mainFrame.setVisible(true);
    }

    public static void gotoGameView(GameBUS gameBUS) {
        mainFrame.setContentPane(new GameView(gameBUS).contentPane);
    }

    private static void setMainFrame(JFrame mainFrame) {
        ViewBUS.mainFrame = mainFrame;
    }
}

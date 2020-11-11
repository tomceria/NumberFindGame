package bus;

import GUI.*;

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

    public static void gotoLoginView() {
        LoginView loginView = new LoginView(new LoginBUS());
        mainFrame.setContentPane(loginView.getContentPane());
        mainFrame.setVisible(true);
    }

    public static void gotoRegisterView() {
        RegisterView registerView = new RegisterView(new RegisterBUS());
        mainFrame.setContentPane(registerView.getContentPane());
        mainFrame.setVisible(true);
    }

    public static void gotoGameView(GameBUS gameBUS) {
        GameView gameView = new GameView(gameBUS);
        mainFrame.setContentPane(gameView.contentPane);

        gameView.init();
    }

    private static void setMainFrame(JFrame mainFrame) {
        ViewBUS.mainFrame = mainFrame;
    }
}

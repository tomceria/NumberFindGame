package bus;

import GUI.*;

import javax.swing.*;
import java.awt.*;

public class ViewBUS {
	private static JFrame mainFrame;
	public static LoginView loginView = null;
	public static RegisterView registerView = null;
	public static GameRoomView gameRoomView = null;
	public static UpdateInfoView updateInfoView = null;
	public static GameView gameView = null;
	public static GameResultView gameResultView = null;

	public static void configureWindow(JFrame mainFrame) {
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(1024, 768 + 22);
		mainFrame.setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mainFrame.setLocation(dim.width / 2 - mainFrame.getSize().width / 2,
				dim.height / 2 - mainFrame.getSize().height / 2);

		ViewBUS.setMainFrame(mainFrame);
	}

	public static void gotoLoginView() {
		if (loginView == null) {
			loginView = new LoginView(new LoginBUS());
		}
		mainFrame.setContentPane(loginView.getContentPane());
		mainFrame.setVisible(true);
	}

	public static void gotoRegisterView(String hostname, int netPort) {
		if (registerView == null) {
			registerView = new RegisterView(new RegisterBUS(hostname, netPort));
		}
		mainFrame.setContentPane(registerView.getContentPane());
		mainFrame.setVisible(true);
	}

    public static void gotoGameRoomView(GameRoomBUS gameRoomBUS) {
        gameRoomView = new GameRoomView(gameRoomBUS);
        gotoGameRoomView();
    }
    public static void gotoGameRoomView() {
        if (gameRoomView == null) {
            return;
        }
        mainFrame.setContentPane(gameRoomView.getContentPane());
        mainFrame.setVisible(true);
    }

//    public static void gotoUpdateInfoView(UpdateInfoBUS updateInfoBUS) {
//
//        gotoUpdateInfoView();
//    }

    public static void gotoUpdateInfoView() {
    	if (updateInfoView == null) {
        	updateInfoView = new UpdateInfoView(new UpdateInfoBUS());
        }
        mainFrame.setContentPane(updateInfoView.getContentPane());
        mainFrame.setVisible(true);
    }

    public static void gotoGameView(GameBUS gameBUS) {
        gameView = new GameView(gameBUS);
        gotoGameView();
    }
    public static void gotoGameView() {
        mainFrame.setContentPane(gameView.getContentPane());
        mainFrame.setVisible(true);

		gameView.init();
	}

	public static void gotoGameResultView() {
		if (gameResultView == null) {
			gameResultView = new GameResultView(new GameResultBUS());
		}
		mainFrame.setContentPane(gameResultView.getContentPane());
		mainFrame.setVisible(true);
	}

	private static void setMainFrame(JFrame mainFrame) {
		ViewBUS.mainFrame = mainFrame;
	}
}

package bus;

import Common.ViewBinder;
import Run.GameMain;
import Socket.GameClient;
import Socket.Request.SocketRequest_GameQuit;
import Socket.Request.SocketRequest_GameRoomQuit;
import Socket.Request.SocketRequest_GameRoomStartGame;
import Socket.Response.SocketResponse_GameInit;
import Socket.Response.SocketResponse_GameRoomProps;
import dto.GameRoom;
import dto.GameRoom_Client;
import dto.Game_Client;
import dto.MatchPlayer;

import javax.swing.*;

import bus.LoginBUS;

public class GameRoomBUS {
	GameRoom gameRoom; // PARENT
	public GameRoomBUS_ViewBinder viewBinder;

	public GameRoomBUS(GameRoom gameRoom) {
		this.gameRoom = gameRoom;
		this.viewBinder = new GameRoomBUS_ViewBinder();
	}

	// Functions

	public void action_RequestStartGame() {
		this.getGameRoom().getClient().sendRequest(new SocketRequest_GameRoomStartGame());
	}

    public void action_QuitGame() {
        ViewBUS.gotoLoginView();
        ViewBUS.terminateAllViews();
        this.getGameRoom().getClient().sendRequest(
                new SocketRequest_GameRoomQuit()
        );
    }
    public void listen_setGameRoomProps(SocketResponse_GameRoomProps response) {
        ((GameRoom_Client) this.gameRoom).setPlayers(response.players);
        this.gameRoom.setMatchConfig(response.matchConfig);
        this.gameRoom.setStatus(response.status);

		/**
		 * Force lệnh update của ViewBinder khi có cập nhật về thông tin phòng
		 */
		this.viewBinder.update();
	}

	public void listen_startGame(SocketResponse_GameInit response) {
		MatchPlayer clientPlayer = ((GameClient) GameMain.client).getClientPlayer();

		Game_Client game = new Game_Client(response.game, this.getGameRoom().getClient(), clientPlayer);
		this.gameRoom.setGame(game);
		game.getGameBUS().initGame();

		ViewBUS.gotoGameView(game.getGameBUS());
	}

	public void ui_loadPlayerCount(JLabel lblPlayerCount) {
		lblPlayerCount.setText(String.format("%s / %s", this.getGameRoom().getPlayers().size(),
				this.getGameRoom().getMatchConfig().getMaxPlayer()));
	}

	public void action_GotoUpdateInfoView() {
		ViewBUS.gotoUpdateInfoView();
	}

	public void action_GotoLeaderboardView() {
		ViewBUS.gotoLeaderboardView();
	}

	// Privates

	private Game_Client getGame() {
		return (Game_Client) this.gameRoom.getGame();
	}

	private GameRoom_Client getGameRoom() {
		return ((GameRoom_Client) this.gameRoom);
	}

	// Inner Classes

	public class GameRoomBUS_ViewBinder extends ViewBinder {
		public JLabel lblPlayerCount;
		public JButton btnStartGame;

		public GameRoomBUS_ViewBinder() {
			super();
			this.update();
			this.startUpdatePeriod();
		}

		@Override
		public void update() {
			if (gameRoom != null) {
				if (lblPlayerCount != null && GameRoomBUS.this.getGameRoom().getPlayers() != null
						&& GameRoomBUS.this.getGameRoom().getPlayers().size() > 0) {
					ui_loadPlayerCount(lblPlayerCount);
				}

				if (GameRoomBUS.this.getGameRoom().getPlayers() != null) {
					if (GameRoomBUS.this.getGameRoom().getPlayers().size() > 1) {
						btnStartGame.setVisible(true);
					} else {
						btnStartGame.setVisible(false);
					}
				}
			}
		}
	}
}

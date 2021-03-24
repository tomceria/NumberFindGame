package Socket;

import javax.swing.JOptionPane;

import Socket.Response.*;
import bus.*;
import dto.GameRoom_Client;
import dto.Game_Client;

public class ClientSocketProcess extends Thread {
	Client client; // PARENT
	boolean isRunning = true;

	public ClientSocketProcess(Client client) {
		this.client = client;
	}

	@Override
	public void run() {
		while (isRunning) {
			SocketResponse resultRaw = client.receiveResponse();
			if (resultRaw == null) {
				continue;
			}

			switch (resultRaw.getAction()) {
			case MSG: {
				System.out.println(String.format("[Server] : %s", resultRaw.getMessage()));
				break;
			}
			case MSG_UPDATEINFO: {
				int messageType = resultRaw.getStatus().equals(SocketResponse.Status.FAILED) ? JOptionPane.ERROR_MESSAGE
						: JOptionPane.INFORMATION_MESSAGE;
				JOptionPane.showMessageDialog(ViewBUS.updateInfoView.getContentPane(), resultRaw.getMessage(),
						"Message", messageType);
				break;
			}
			case GAMEROOM_PLAYERJOIN: {
				if (((GameClient) this.client).getGameRoom() != null) {
					System.out.println("CLIENT: You're already in a room.");
				} else {
					((GameClient) this.client).getGameClientBUS()
							.listen_clientPlayerJoinRoom((SocketResponse_GameRoomPlayerJoin) resultRaw);
				}

				break;
			}
			case GAMEROOM_PROPS: {
				((GameClient) client).getGameRoom().getGameRoomBUS()
						.listen_setGameRoomProps((SocketResponse_GameRoomProps) resultRaw);

				break;
			}
			case GAME_INIT: {
				GameRoom_Client gameRoom = ((GameClient) client).getGameRoom();

				gameRoom.getGameRoomBUS().listen_startGame((SocketResponse_GameInit) resultRaw);

				break;
			}
			case GAME_PROPS: {
				GameRoom_Client gameRoom = ((GameClient) client).getGameRoom();
				((Game_Client) gameRoom.getGame()).getGameBUS()
						.listen_GameUpdated((SocketResponse_GameProps) resultRaw);
				break;
			}
			case GAME_END: {
				GameRoom_Client gameRoom = ((GameClient) client).getGameRoom();
				((Game_Client) gameRoom.getGame()).getGameBUS().listen_GameEnd();
				break;
			}
			case GAME_RESULT: {
				ViewBUS.gameResultView.getGameResultBUS().listen_showResult((SocketResponse_GameResult) resultRaw);
				break;
			}
			case LEADERBOARD_RESULT: {
				ViewBUS.leaderboardView.getLeaderboardBUS()
						.listen_showResult((SocketResponse_LeaderboardResult) resultRaw);
				break;
			}
			case NET_CLOSE: {
				((GameClient) client).close();
				isRunning = false;
				ViewBUS.gotoLoginView();
				int messageType = resultRaw.getStatus().equals(SocketResponse.Status.FAILED) ? JOptionPane.ERROR_MESSAGE
						: JOptionPane.INFORMATION_MESSAGE;
				JOptionPane.showMessageDialog(ViewBUS.loginView.getContentPane(), resultRaw.getMessage(), "Message",
						messageType);
				break;
			}
			}
		}
	}
}

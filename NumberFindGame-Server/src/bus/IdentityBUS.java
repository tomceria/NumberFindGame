package bus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import Socket.ClientHandler;
import Socket.Request.SocketRequest_AccessChangePassword;
import Socket.Request.SocketRequest_AccessLogin;
import Socket.Request.SocketRequest_AccessRegister;
import Socket.Request.SocketRequest_AccessUpdateInfo;
import Socket.Response.SocketResponse;
import dto.MatchPlayer;
import dto.MatchPlayer_Server;
import dto.PlayerDTO;
import Socket.GameServer;
import Socket.Server;

public class IdentityBUS {
	ClientHandler clientHandler;
	PlayerBUS playerBUS;
	Server server;

	public IdentityBUS(ClientHandler clientHandler, Server server) {
		this.clientHandler = clientHandler;
		this.playerBUS = new PlayerBUS();
		this.server = server;
	}

	public boolean performLogin(SocketRequest_AccessLogin request) {
		PlayerDTO player = null;

		HashMap<UUID, ClientHandler> clientConections = ((GameServer) this.server).getClientManager()
				.getClientConnections();
		MatchPlayer connectPlayer;
		PlayerDTO onlinePlayer;
		if (clientConections.size() > 1) {
			for (ClientHandler clientHandler : clientConections.values()) {
				connectPlayer = ((MatchPlayer) clientHandler.getClientIdentifier());
				if (connectPlayer == null) {
					continue;
				}
				onlinePlayer = connectPlayer.getPlayer();
				if (onlinePlayer.getUsername().equals(request.username)) {
					throw new RuntimeException("This account is already logged in.");
				}

			}
		}

		if (playerBUS.login(request.username, request.password)) {
			player = playerBUS.getOneByUsername(request.username);
		}

		if (player != null) { // Validate thành công => có player
			this.clientHandler.setClientIdentifier(new MatchPlayer_Server(player));
			this.clientHandler.setLoggedIn(true);
			this.clientHandler.sendResponse(
					new SocketResponse(SocketResponse.Status.SUCCESS, SocketResponse.Action.MSG, "Logged in."));
		} else {
			this.clientHandler.sendResponse(new SocketResponse(SocketResponse.Status.FAILED, SocketResponse.Action.MSG,
					"Invalid username or password."));
		}

		return player != null;
	}

//	public boolean checkLeaderBoardUser(SocketRequest_LeaderboardUser request) {
//		boolean result = false;
//		PlayerBUS playerBus = new PlayerBUS();
//		ArrayList<PlayerDTO> players = new ArrayList<PlayerDTO>();
//		players = playerBus.getAll();
//		for (PlayerDTO player : players) {
//			if (request.username.equals(player.getUsername())) {
//				return true;
//			}
//		}
//		
//		throw new RuntimeException("No user found.");
//	}

	public boolean performRegister(SocketRequest_AccessRegister request) throws Exception {
		PlayerBUS playerBus = new PlayerBUS();
		ArrayList<PlayerDTO> players = new ArrayList<PlayerDTO>();
		players = playerBus.getAll();
		for (PlayerDTO player : players) {
			if (request.username.equals(player.getUsername())) {
				throw new RuntimeException("This Username is already been taken. Please choose another username.");
			}
		}

		PlayerDTO player = new PlayerDTO(request.username, request.password, request.email, request.firstName,
				request.lastName);

		boolean result = playerBUS.register(player);

		return result;
	}

	public boolean performUpdateInfo(SocketRequest_AccessUpdateInfo request) throws Exception {
		PlayerBUS playerBus = new PlayerBUS();

		PlayerDTO newPlayerInfo = new PlayerDTO(request.username, request.password, request.email, request.firstName,
				request.lastName);

		boolean result = playerBUS.updateInfo(newPlayerInfo);

		return result;
	}

	public boolean performChangePassword(SocketRequest_AccessChangePassword request) throws Exception {
		PlayerBUS playerBus = new PlayerBUS();

//		PlayerDTO player = new PlayerDTO(request.username, request.password, request.email, request.firstName,
//				request.lastName);

		boolean result = playerBUS.changePassword(request.username, request.password);

		return result;
	}
}

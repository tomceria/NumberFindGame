package Socket;

import Socket.Request.*;
import Socket.Response.SocketResponse;
import Socket.Response.SocketResponse_LeaderboardResult;
import bus.*;
import dto.*;

public class RequestHandler {
	Thread requestHandleThread;
	ClientHandler clientHandler;
	SocketRequest requestRaw;

	public RequestHandler(SocketRequest requestRaw, IClientIdentifier clientIdentifier, ClientHandler clientHandler) {
		this.requestRaw = requestRaw;
		this.clientHandler = clientHandler;
		this.requestHandleThread = new Thread() {
			@Override
			public void run() {
				ClientHandler thisClientHandler = RequestHandler.this.clientHandler;

				if (thisClientHandler.isLoggedIn == false) {
					IdentityBUS identityBUS = new IdentityBUS(thisClientHandler,
							RequestHandler.this.clientHandler.clientManager.server);

					switch (requestRaw.getAction()) {
					case ACCESS_LOGIN: {
						boolean result = false;
						try {
							result = identityBUS.performLogin((SocketRequest_AccessLogin) requestRaw);
							if (result == true) {
								RequestHandler.this.onSuccessConnection();
							} else {
								thisClientHandler.isRunning = false;
							}
						} catch (Exception e) {
							thisClientHandler.sendResponse(new SocketResponse(SocketResponse.Status.FAILED,
									SocketResponse.Action.MSG, e.getMessage()));
						}

						break;
					}
					case ACCESS_REGISTER: {
						SocketRequest_AccessRegister request = ((SocketRequest_AccessRegister) requestRaw);
						boolean result = false;
						try {
							result = identityBUS.performRegister(request);
							if (result == true) {
								thisClientHandler.sendResponse(new SocketResponse(SocketResponse.Status.SUCCESS,
										SocketResponse.Action.MSG, "Your account has been created."));
							} else {
								thisClientHandler.sendResponse(new SocketResponse(SocketResponse.Status.FAILED,
										SocketResponse.Action.MSG, "Register failed."));
							}
						} catch (Exception e) {
							thisClientHandler.sendResponse(new SocketResponse(SocketResponse.Status.FAILED,
									SocketResponse.Action.MSG, e.getMessage()));
						}

						break;
					}
					default: {
						thisClientHandler.sendResponse(new SocketResponse(SocketResponse.Status.FAILED,
								SocketResponse.Action.MSG, "Invalid access request."));
						thisClientHandler.isRunning = false; // Yêu cầu ĐẦU TIÊN không hợp lệ => Thoát khỏi vòng lặp =>
						// Kết thúc Thread => Disconnect
						break;
					}
					}
				} else if (thisClientHandler.isLoggedIn && thisClientHandler.clientIdentifier != null) {
					switch (requestRaw.getAction()) {
					case MSG: {
						System.out.println("Received message: " + requestRaw.getMessage());
						break;
					}
					case GAMEROOM_STARTGAME: {
						SocketRequest_GameRoomStartGame request = ((SocketRequest_GameRoomStartGame) requestRaw);
						MatchPlayer_Server matchPlayer = (MatchPlayer_Server) clientIdentifier;

						matchPlayer.getGameRoomBUS().startGame();
						break;
					}
					case PLAYER_UPDATEINFO: {
						IdentityBUS identityBUS = new IdentityBUS(thisClientHandler,
								RequestHandler.this.clientHandler.clientManager.server);
						SocketRequest_AccessUpdateInfo request = ((SocketRequest_AccessUpdateInfo) requestRaw);
						boolean result = false;
						try {
							result = identityBUS.performUpdateInfo(request);
							if (result == true) {
								thisClientHandler.sendResponse(new SocketResponse(SocketResponse.Status.SUCCESS,
										SocketResponse.Action.MSG_UPDATEINFO, "Your account has been updated."));
							} else {
								thisClientHandler.sendResponse(new SocketResponse(SocketResponse.Status.FAILED,
										SocketResponse.Action.MSG_UPDATEINFO, "Update failed."));
							}
						} catch (Exception e) {
							thisClientHandler.sendResponse(new SocketResponse(SocketResponse.Status.FAILED,
									SocketResponse.Action.MSG_UPDATEINFO, e.getMessage()));
						}
						break;
					}
					case PLAYER_CHANGEPASSWORD: {
						IdentityBUS identityBUS = new IdentityBUS(thisClientHandler,
								RequestHandler.this.clientHandler.clientManager.server);
						SocketRequest_AccessChangePassword request = ((SocketRequest_AccessChangePassword) requestRaw);
						boolean result = false;
						try {
							result = identityBUS.performChangePassword(request);
							if (result == true) {
								thisClientHandler.sendResponse(new SocketResponse(SocketResponse.Status.SUCCESS,
										SocketResponse.Action.MSG_UPDATEINFO, "Your password has been updated."));
							} else {
								thisClientHandler.sendResponse(new SocketResponse(SocketResponse.Status.FAILED,
										SocketResponse.Action.MSG_UPDATEINFO, "Update failed."));
							}
						} catch (Exception e) {
							thisClientHandler.sendResponse(new SocketResponse(SocketResponse.Status.FAILED,
									SocketResponse.Action.MSG_UPDATEINFO, e.getMessage()));
						}

						break;
					}
					case GAME_SUBMITLEVELNODE: {
						SocketRequest_GameSubmitLevelNode request = ((SocketRequest_GameSubmitLevelNode) requestRaw);
						MatchPlayer_Server matchPlayer = (MatchPlayer_Server) clientIdentifier;

						matchPlayer.getGameBUS().req_sendLevelNodeForValidation(request.levelNode, matchPlayer);
						break;
					}
					case GAME_QUIT: {
						SocketRequest_GameQuit request = ((SocketRequest_GameQuit) requestRaw);
						MatchPlayer_Server matchPlayer = (MatchPlayer_Server) clientIdentifier;
						matchPlayer.getGameBUS().req_quitGame(thisClientHandler, matchPlayer);
						break;
					}
					case LEADERBOARD_ALL: {
						SocketRequest_LeaderboardAll request = ((SocketRequest_LeaderboardAll) requestRaw);
						thisClientHandler.sendResponse(
								new SocketResponse_LeaderboardResult(LeaderBoardBUS.req_getLeaderBoardResult(request)));
						break;
					}
					case LEADERBOARD_USER: {
						SocketRequest_LeaderboardUser request = ((SocketRequest_LeaderboardUser) requestRaw);
						new SocketResponse_LeaderboardResult(LeaderBoardBUS.req_getLeaderBoardUser(request));
						break;
					}
					}
				}
			}
		};
	}

	public void init() {
		requestHandleThread.start();
	}

	// Privates

	private void onSuccessConnection() {
		PlayerDTO player;
		player = ((MatchPlayer) clientHandler.getClientIdentifier()).getPlayer();
		Logger.writeFile(String.format("%s logged in.", player.getUsername()));

		((GameServer) this.clientHandler.clientManager.server).getGameServerBUS().joinGame(this.clientHandler);
	}

}

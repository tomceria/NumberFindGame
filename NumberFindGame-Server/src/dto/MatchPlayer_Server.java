package dto;

import Socket.GameServer;
import Socket.IClientIdentifier;
import bus.GameBUS;
import bus.GameRoomBUS;
import bus.GameServerBUS;

import java.util.UUID;

public class MatchPlayer_Server extends MatchPlayer implements IClientIdentifier {
    GameServerBUS serverBUS;
    GameRoomBUS gameRoomBUS;
    GameBUS gameBUS;

    public MatchPlayer_Server(PlayerDTO player) {
        super(player);
    }

    // Overrides

    @Override
    public UUID getClientHandlerId() {
        return null;
    }

    // Properties

    public GameServerBUS getServerBUS() {
        return serverBUS;
    }
    public void setServerBUS(GameServerBUS serverBUS) {
        this.serverBUS = serverBUS;
    }

    public GameRoomBUS getGameRoomBUS() {
        return gameRoomBUS;
    }
    public void setGameRoomBUS(GameRoomBUS gameRoomBUS) {
        this.gameRoomBUS = gameRoomBUS;
    }

    public GameBUS getGameBUS() {
        return gameBUS;
    }
    public void setGameBUS(GameBUS gameBUS) {
        this.gameBUS = gameBUS;
    }
}

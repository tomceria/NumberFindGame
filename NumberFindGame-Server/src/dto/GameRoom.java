package dto;

import bus.GameBUS;
import bus.GameRoomBUS;

public class GameRoom {
    private int id;
    protected MatchConfig matchConfig;
    protected GameRoomStatus status;
    protected GameRoomBUS gameRoomBUS;
    private GameBUS gameBUS;

    public GameRoom(int id) {
        this.id = id;
    }

    // Properties
    public int getId() {
        return id;
    }

    public MatchConfig getMatchConfig() {
        return matchConfig;
    }

    public void setMatchConfig(MatchConfig matchConfig) {
        this.matchConfig = matchConfig;
    }

    public GameRoomStatus getStatus() {
        return status;
    }

    public void setStatus(GameRoomStatus status) {
        this.status = status;
    }

    public GameRoomBUS getGameRoomBUS() {
        return gameRoomBUS;
    }

    public GameBUS getGameBUS() {
        return gameBUS;
    }

    public void setGameBUS(GameBUS gameBUS) {
        this.gameBUS = gameBUS;
    }
}

package dto;

public class GameRoom {
    protected MatchConfig matchConfig;
    protected GameRoomStatus status;

    public GameRoom() {
        this.status = GameRoomStatus.OPEN;
    }

    // Properties
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
}

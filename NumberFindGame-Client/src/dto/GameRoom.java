package dto;

public class GameRoom {
    private int id;
    protected MatchConfig matchConfig;
    protected GameRoomStatus status;
    private Game game;

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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}

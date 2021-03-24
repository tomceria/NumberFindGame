package dto;

public class GameRoom {
    private int id;
    private String name;
    protected MatchConfig matchConfig;
    protected GameRoomStatus status;
    private Game game;

    public GameRoom(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Properties
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
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

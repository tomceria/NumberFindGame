package Socket.Response;

import dto.CurrentLevel;
import dto.LevelNode;
import dto.MatchPlayer;

import java.util.ArrayList;

public class SocketResponse_GameProps extends SocketResponse {
    public CurrentLevel currentLevel;
    public ArrayList<LevelNode> level;
    public ArrayList<MatchPlayer> players;

    public SocketResponse_GameProps(CurrentLevel currentLevel, ArrayList<LevelNode> level, ArrayList<MatchPlayer> players) {
        super(Status.SUCCESS, Action.GAME_PROPS, "Update Game Room's state");
        this.currentLevel = currentLevel;
        this.level = level;
        this.players = players;
    }
}

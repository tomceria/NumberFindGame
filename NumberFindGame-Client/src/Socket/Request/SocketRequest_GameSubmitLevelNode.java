package Socket.Request;

import dto.LevelNode;
import dto.MatchPlayer;

public class SocketRequest_GameSubmitLevelNode extends SocketRequest {
    public LevelNode levelNode;
    public MatchPlayer clientPlayer;

    public SocketRequest_GameSubmitLevelNode(LevelNode levelNode, MatchPlayer clientPlayer) {
        super(Action.GAME_SUBMITLEVELNODE, String.format("Player %s submits a level node for validation", clientPlayer.getPlayer().getUsername()));
        this.levelNode = levelNode;
        this.clientPlayer = clientPlayer;
    }
}

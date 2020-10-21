package dto;

import Socket.IClientIdentifier;

public class MatchPlayer_Server extends MatchPlayer implements IClientIdentifier {
    public MatchPlayer_Server(PlayerDTO player) {
        super(player);
    }
}

package Models;

public class MatchPlayer {
    Player player;
    int score = 0;
    double avgTime = 0;
    int placing = 1;

    public MatchPlayer(Player player) {
        this.player = player;
    }
}

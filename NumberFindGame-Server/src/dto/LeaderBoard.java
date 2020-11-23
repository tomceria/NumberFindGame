package dto;

public class LeaderBoard {
    int ranking;
    String username;
    int totalMatches;
    int sumRP;
    double winrate;

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTotalMatches() {
        return totalMatches;
    }

    public void setTotalMatches(int totalMatches) {
        this.totalMatches = totalMatches;
    }

    public int getSumRP() {
        return sumRP;
    }

    public void setSumRP(int sumRP) {
        this.sumRP = sumRP;
    }

    public double getWinrate() {
        return winrate;
    }

    public void setWinrate(double winrate) {
        this.winrate = winrate;
    }
}

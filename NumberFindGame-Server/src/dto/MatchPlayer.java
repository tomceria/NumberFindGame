package dto;

public class MatchPlayer {
	private int playerId;
	private int matchId;
	private int score;
	private int placing;
	private double avgTime;

	/**
	 * @return the playerId
	 */
	public int getPlayerId() {
		return playerId;
	}

	/**
	 * @param playerId the playerId to set
	 */
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	/**
	 * @return the matchId
	 */
	public int getMatchId() {
		return matchId;
	}

	/**
	 * @param matchId the matchId to set
	 */
	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @return the placing
	 */
	public int getPlacing() {
		return placing;
	}

	/**
	 * @param placing the placing to set
	 */
	public void setPlacing(int placing) {
		this.placing = placing;
	}

	/**
	 * @return the avgTime
	 */
	public double getAvgTime() {
		return avgTime;
	}

	/**
	 * @param avgTime the avgTime to set
	 */
	public void setAvgTime(double avgTime) {
		this.avgTime = avgTime;
	}

}

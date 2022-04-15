package Models;

public class Player {
	private String playerName;
	private int playerPoints;
	private int playerHighScore;
	private String levelName;
	
	public Player() {
		this.playerName = "Player1";
		this.playerPoints = 0;
		this.setPlayerHighScore(0);
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	public int getPlayerPoints() {
		return playerPoints;
	}
	
	public void setPlayerPoints(int playerPoints) {
		this.playerPoints = playerPoints;
		if (playerPoints > playerHighScore) {
			this.setPlayerHighScore(playerPoints);
		}
	}
	
	public int getPlayerHighScore() {
		return playerHighScore;
	}

	public void setPlayerHighScore(int playerHighScore) {
		this.playerHighScore = playerHighScore;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
}

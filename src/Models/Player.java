package Models;

import java.util.Date;

public class Player {
	private String playerName;
	private int playerPoints;
	private int playerHighScore = 0;
	private String levelName;
	private long timePlayed;
	private long timeStarted;
	
	public Player() {
		this.playerName = "Player1";
		this.playerPoints = 0;
		this.setPlayerHighScore();
		Date date = new Date();
		this.setTimeStarted(date.getTime());
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
	}
	
	public int getPlayerHighScore() {
		return playerHighScore;
	}

	public void setPlayerHighScore() {
		if (this.playerPoints > this.playerHighScore) {
			this.playerHighScore = this.playerPoints;
		}
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public long getTimeStarted() {
		return timeStarted;
	}

	public void setTimeStarted(long timeStarted) {
		this.timeStarted = timeStarted;
	}

	public long updateTimePlayed() {
		Date date = new Date();
		this.setTimePlayed(date.getTime() - this.getTimeStarted());
		return timePlayed;
	}
	
	public long getTimePlayed() {
		return this.timePlayed;
	}

	public void setTimePlayed(long timePlayed) {
		this.timePlayed = timePlayed;
	}
}

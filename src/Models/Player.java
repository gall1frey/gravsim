package Models;

import java.util.Date;

public class Player {
	public static Player instance = null;
	private String playerName;
	private int playerPoints;
	private int playerHighScore = 0;
	private String levelName;
	private long timePlayed;
	private long timeStarted;
	
	public static Player getInstance() {
		if (instance == null) {
			instance = new Player();
		}
		return instance;
	}
	
	public static Player getNewInstance() {
		instance = new Player();
		return instance;
	}
	
	public Player() {
		this.playerName = "";
		this.playerPoints = 0;
		this.setPlayerHighScore();
		this.setTimeStarted();
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

	public void setTimeStarted() {
		Date date = new Date();
		this.timeStarted = date.getTime();
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

	public void setPlayerHighScore(int score) {
		this.playerHighScore = score;
		
	}
}

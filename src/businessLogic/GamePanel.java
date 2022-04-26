package businessLogic;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import Controllers.DbController;
import Controllers.keyHandler;
import Models.Level;
import Models.Player;
import UI.CreativeMenuUI;
import UI.CreativeUI;
import UI.MessageUI;
import UI.PlayGameUI;
import UI.ScoreboardUI;
import UI.UsermenuUI;
import UI.MenuUI;
import Views.gameplayScreen;


public class GamePanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;

	final int screenWidth = 1280;
	final int screenHeight = 720;

	keyHandler keyH = new keyHandler();

	Thread gameThread;

	private Player player;
	private Level level;
	private gameplayScreen screen = new gameplayScreen("assets\\images\\misc\\background.png");
	public enum gameState { PLAY, WIN, CRASH, SCOREBOARD, MENU, USER_MENU, CREATIVE, EXIT, CREATIVE_MENU };
//	private gameState state = gameState.USER_MENU;
	private gameState state = gameState.MENU;

	private PlayGameUI playUI;
	private MessageUI msgUI;
	private CreativeUI creativeUI;
	private MenuUI menuUI;
	private UsermenuUI usermenuUI;
	private ScoreboardUI scrbrdUI;
	private CreativeMenuUI cmenuUI;

	private PlayHandler playHandler;
	private CreativeHandler creativeHandler;
	private ScoreboardHandler scoreboardHandler;
	private UsermenuHandler usermenuHandler;
	private DbController dbController;

	private Player[] topFive = null;
	
	int FPS = 60;

	public GamePanel() {
		//this.player = player;
		//this.level = level;
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		this.playUI = PlayGameUI.getInstance();
		this.msgUI = MessageUI.getInstance();
		this.creativeUI = CreativeUI.getInstance();
		this.menuUI = MenuUI.getInstance();
		this.usermenuUI = UsermenuUI.getInstance();
		this.scrbrdUI = ScoreboardUI.getInstance();
		this.playHandler = PlayHandler.getInstance();
		this.creativeHandler = CreativeHandler.getInstance();
		this.scoreboardHandler = ScoreboardHandler.getInstance();
		this.usermenuHandler = UsermenuHandler.getInstance();
		this.cmenuUI = CreativeMenuUI.getInstance();
		this.dbController = DbController.getInstance();
		topFive = new Player[5];
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		double lastTime = System.nanoTime();
		long currentTime;

		while (gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;

			lastTime = currentTime;

			if (delta > 1) {
				update();
				repaint();
				delta--;
			}
		}
	}

	public void update() {
		if (this.state == gameState.PLAY) {
			handleCommonPlayKeypress();
			if (this.player != null && this.level != null)
				this.state = playHandler.handlePlay(this.keyH, level, player, state);
		} else if (this.state == gameState.SCOREBOARD) {
			this.state = scoreboardHandler.handleScoreboard(keyH, state);
		} else if (this.state == gameState.MENU) {
			handleMenuKeypress();
		} else if (this.state == gameState.USER_MENU) {
			handleUserMenuKeypress();
		} else if (this.state == gameState.WIN || this.state == gameState.CRASH) {
			handleEndGameKeypress();
		} else if (this.state == gameState.EXIT) {
			handleExit();
		} else if (this.state == gameState.CREATIVE_MENU) {
			handleCreativeMenuKeypress();
		} else if (this.state == gameState.CREATIVE) {
			handleCommonPlayKeypress();
			if (this.player != null && this.level != null)
				creativeHandler.handleCreative(level);
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		this.screen.renderBackground(g2,this);
		if (this.state == gameState.PLAY) {
			this.playUI.draw(g2, screen, level, player);
		} else if (this.state == gameState.CRASH) {
			this.msgUI.draw(g2, screenHeight, screenWidth, false, this.player);
		} else if (this.state == gameState.MENU) {
			this.menuUI.draw(g2);
		} else if (this.state == gameState.USER_MENU) {
			if(this.player != null && this.level != null) {				
				this.usermenuUI.draw(g2,this.player.getPlayerName());
			}
		} else if (this.state == gameState.SCOREBOARD) {
			showScoreboard();
			this.scrbrdUI.draw(g2,topFive);
//			showScoreboard();
		} else if (this.state == gameState.WIN) {
			this.msgUI.draw(g2, screenHeight, screenWidth, true, this.player);
		} else if (this.state == gameState.CREATIVE) {
			this.creativeUI.draw(this.player.updateTimePlayed(), g2, screen, level);
		} else if (this.state == gameState.CREATIVE_MENU) {
			if (this.player != null)
				this.cmenuUI.draw(g2, this.player.getLevelName());
			else
				this.cmenuUI.draw(g2, "");
		}
		g2.dispose();
	}

	private void handleCommonPlayKeypress() {
		//Panning
		if (this.keyH.letterPressed[this.keyH.getLetterCode('W')] == true) {
			this.screen.updateOffsetY(-10);
		}
		if (this.keyH.letterPressed[this.keyH.getLetterCode('A')]== true) {
			this.screen.updateOffsetX(-10);
		}
		if (this.keyH.letterPressed[this.keyH.getLetterCode('S')] == true) {
			this.screen.updateOffsetY(10);
		}
		if (this.keyH.letterPressed[this.keyH.getLetterCode('D')] == true) {
			this.screen.updateOffsetX(10);
		}

		//Zooming
		if (this.keyH.zInPressed == true) {
			this.screen.updateScale(10);
		}
		if (this.keyH.zOutPressed == true) {
			this.screen.updateScale(-10);
		}

		// others
		if (this.keyH.escPressed == true) {
			this.state = gameState.MENU;
			this.player = null;
			this.level = null;
			this.keyH.escPressed = false;
		}
	}

	private void handleEndGameKeypress() {
		// TODO Auto-generated method stub
		// X pressed = quit
		// M pressed = menu
		
		if (this.state == gameState.WIN) {
			this.player.setPlayerHighScore(dbController.getHighScore(this.player.getPlayerName()));
			dbController.addToDb(this.player.getPlayerName(), this.player.getLevelName(), this.player.getPlayerPoints());
		}
		
		if (this.keyH.escPressed == true) {
			this.state = gameState.EXIT;
		}
		if (this.keyH.letterPressed[this.keyH.getLetterCode('M')] == true) {
			this.state = gameState.MENU;
		}
		this.player.setPlayerHighScore();
		this.level = null;
	}

	private void handleExit() {
		System.exit(0);
	}

	private void handleUserMenuKeypress() {
		String player_name = this.player.getPlayerName();
		for (int i = 0; i < 26; i++) {
			if (this.keyH.letterPressed[this.keyH.getLetterCode((char)(i+'A'))]) {
				player_name += String.valueOf((char)(i+'A'));
				this.keyH.letterPressed[this.keyH.getLetterCode((char)(i+'A'))] = false;
			}
		}
		if (this.keyH.bkspPressed && player_name.length() > 0) {
			player_name = player_name.substring(0, player_name.length() - 1);
			this.keyH.bkspPressed = false;
		}
		this.player.setPlayerName(player_name);
		this.state = usermenuHandler.handleUsermenu(keyH, this.player.getPlayerName());
		
		if (this.state == gameState.PLAY) {
			this.screen.setPlayFrame("assets\\images\\misc\\play_frame.png");
			this.player.setLevelName(this.level.getLevelName());
			this.player.setTimeStarted();
		}
	}

	private void handleMenuKeypress() {
		// TODO Auto-generated method stub
		if (this.keyH.letterPressed[this.keyH.getLetterCode('P')]) {
			this.state = gameState.USER_MENU;
			this.player = Player.getNewInstance();
			LevelCatalogue.resetInstance();
			this.level  = LevelCatalogue.getInstance(0);
			this.screen.resetScreen();
			LevelCatalogue.resetInstance();
		} else if (this.keyH.letterPressed[this.keyH.getLetterCode('S')] == true) {
			this.state = gameState.SCOREBOARD;
		} else if (this.keyH.letterPressed[this.keyH.getLetterCode('C')] == true) {
			this.state = gameState.CREATIVE_MENU;
			this.player = Player.getNewInstance();
			this.screen.resetScreen();
			this.keyH.letterPressed[this.keyH.getLetterCode('C')] = false;
		} else if (this.keyH.escPressed == true) {
			this.state = gameState.EXIT;
		}
	}

	private void showScoreboard() {
		this.topFive = this.dbController.getTopFive();
	}
	
	private void handleCreativeMenuKeypress() {
		String filePath = this.player.getLevelName();
		for (int i = 0; i < 26; i++) {
			if (this.keyH.letterPressed[this.keyH.getLetterCode((char)(i+'A'))]) {
				filePath += String.valueOf((char)(i+'A'));
				this.keyH.letterPressed[this.keyH.getLetterCode((char)(i+'A'))] = false;
			}
		}
		if (this.keyH.bkspPressed && filePath.length() > 0) {
			filePath = filePath.substring(0, filePath.length() - 1);
			this.keyH.bkspPressed = false;
		}
		if (this.keyH.bkSlashPressed) {
			filePath += "\\";
			this.keyH.bkSlashPressed = false;
		}
		if (this.keyH.fwSlashPressed) {
			filePath += "/";
			this.keyH.fwSlashPressed = false;
		}
		if (this.keyH.dotPressed) {
			filePath += ".";
			this.keyH.dotPressed = false;
		}
		this.player.setLevelName(filePath);
		this.state = this.creativeHandler.handleCreativeMenu(keyH, filePath);
		if (this.state == gameState.CREATIVE && filePath.length() > 0) {
			this.level = this.creativeHandler.getLevel(filePath);
			if (this.level == null) {
				this.state = gameState.CREATIVE_MENU;
			} else {
				this.level.setLevelName(filePath);
			}
			this.screen.setPlayFrame("assets\\images\\misc\\creative_play_frame.png");
		}
	}
	
}

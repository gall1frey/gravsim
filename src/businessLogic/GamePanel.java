package businessLogic;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import Controllers.keyHandler;
import Models.Level;
import Models.Player;
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
	private gameplayScreen screen = new gameplayScreen("C:\\Users\\sirdm\\Documents\\projects\\gravsim\\assets\\images\\misc\\background.png");
	public enum gameState { PLAY, WIN, CRASH, SCOREBOARD, MENU, USER_MENU, CREATIVE, EXIT, CREATIVE_MENU };
//	private gameState state = gameState.USER_MENU;
	private gameState state = gameState.MENU;

	private PlayGameUI playUI;
	private MessageUI msgUI;
	private CreativeUI creativeUI;
	private MenuUI menuUI;

	private PlayHandler playHandler;
	private CreativeHandler creativeHandler;
	private ScoreboardHandler scoreboardHandler;

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
		this.playHandler = PlayHandler.getInstance();
		this.creativeHandler = CreativeHandler.getInstance();
		this.scoreboardHandler = ScoreboardHandler.getInstance();
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
			//TODO: refer line 92, do something like that
			this.usermenuUI.draw(g2);
		} else if (this.state == gameState.SCOREBOARD) {
			this.scrbrdUI.draw(g2);
//			showScoreboard();
		} else if (this.state == gameState.WIN) {
			this.msgUI.draw(g2, screenHeight, screenWidth, true, this.player);
		} else if (this.state == gameState.CREATIVE) {
			this.creativeUI.draw(this.player.updateTimePlayed(), g2, screen, level);
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
		// TODO Auto-generated method stub
		/*String username = "";
		if (keyH.letterPressed[this.keyH.getLetterCode('A')]) {
			username += "A";
		}
		*/

		if (this.keyH.letterPressed[this.keyH.getLetterCode('P')] == true) {
			this.state = gameState.PLAY;

		} else if (this.keyH.letterPressed[this.keyH.getLetterCode('M')] == true) {
			this.state = gameState.MENU;

		} else if (this.keyH.escPressed == true) {
			this.state = gameState.EXIT;
		}

	}

	private void handleMenuKeypress() {
		// TODO Auto-generated method stub
		if (this.keyH.letterPressed[this.keyH.getLetterCode('P')]) {
			this.state = gameState.USER_MENU;
			LevelCatalogue.resetInstance();
		} else if (this.keyH.letterPressed[this.keyH.getLetterCode('S')] == true) {
			this.state = gameState.SCOREBOARD;
		} else if (this.keyH.letterPressed[this.keyH.getLetterCode('C')] == true) {
			this.state = gameState.CREATIVE_MENU;
		} else if (this.keyH.escPressed == true) {
			this.state = gameState.EXIT;
		}
	}

	private void showScoreboard() {
		//TODO: open scoreboard from here

		if (this.keyH.letterPressed[this.keyH.getLetterCode('M')] == true) {
			this.state = gameState.MENU;

		} else if (this.keyH.escPressed == true) {
			this.state = gameState.EXIT;
		}

	}

	private void showUserMenu() {
		//TODO: show user menu, get user name and level
		//      from user

		// Create a new class in models or business logic for this, and use it.
		// That class can then call the inputView class from views for GUI
		// player name and level number should be returned by the method used.

		// then initialize player and level as follows

		// This func only for *VISUALIZATION*
		// Use handleMenuKeypress to handle the logic of main menu

		// Ideally, handleUserMenuKeypress should set player, state and level details into this class' variables.
		
		Player p = Player.getNewInstance();
		Level lev = LevelCatalogue.getInstance(0);
		System.out.println("GamePanel [228]: "+lev.getEntities()[2].getPos()[0]+"\t"+lev.getEntities()[2].getPos()[1]);
		this.player = p;
		this.level = lev;
		this.state = gameState.PLAY;

		//TODO: put this next line in where you're setting player, state and level variables
		// This is playframe for PLAY mode. CREATIVE mode playframe coming soon
		this.screen.setPlayFrame("C:\\Users\\sirdm\\Documents\\projects\\gravsim\\assets\\images\\misc\\play_frame.png");

		if (this.state == gameState.CREATIVE) {
			lev.setRocketMove(false);
			this.screen.setPlayFrame("C:\\Users\\sirdm\\Documents\\projects\\gravsim\\assets\\images\\misc\\creative_play_frame.png");
		}

		//TODO: Create a new playFrame for creative mode
	}

}

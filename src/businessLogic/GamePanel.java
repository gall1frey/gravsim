package businessLogic;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import Controllers.keyHandler;
import Models.Level;
import Models.Player;
import UI.PlayGameUI;
import Views.gameplayScreen;


public class GamePanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;

	final int screenWidth = 1280;
	final int screenHeight = 720;

	keyHandler keyH = new keyHandler();

	Thread gameThread;

	private Player player;
	private Level level;
	private gameplayScreen screen = new gameplayScreen("C:\\Users\\sirdm\\Documents\\projects\\gravsim2\\assets\\images\\misc\\background.png","C:\\Users\\sirdm\\Documents\\projects\\gravsim2\\assets\\images\\misc\\play_frame.png");
	public enum gameState { PLAY, WIN, CRASH, SCOREBOARD, MENU, USER_MENU };
	private gameState state = gameState.USER_MENU;
	private PlayGameUI playUI = PlayGameUI.getInstance();

	int FPS = 60;

	public GamePanel() {
		//this.player = player;
		//this.level = level;
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
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
		/* TODO: FILL THIS IN*/
		if (this.state == gameState.PLAY) {
			handlePlayKeypress();
		} else if (this.state == gameState.SCOREBOARD) {
			showScoreboard();
		} else if (this.state == gameState.MENU) {
			showMenu();
		} else if (this.state == gameState.USER_MENU) {
			showUserMenu();
		}// else if (this.state == gameState.)
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		this.screen.renderBackground(g2,this);
		if (this.state == gameState.PLAY) {
			this.playUI.draw(g2, screen, level);
		} else if (this.state == gameState.CRASH) {
			
		} else if (this.state == gameState.MENU) {
			
		} else if (this.state == gameState.USER_MENU) {
			
		} else if (this.state == gameState.SCOREBOARD) {
			
		} else if (this.state == gameState.WIN) {
			
		}
		g2.dispose();
	}
	
	private void handlePlayKeypress() {
		//Panning
		if (this.keyH.wPressed == true) {
			this.screen.updateOffsetY(-10);
		}
		if (this.keyH.aPressed == true) {
			this.screen.updateOffsetX(-10);
		}
		if (this.keyH.sPressed == true) {
			this.screen.updateOffsetY(10);
		}
		if (this.keyH.dPressed == true) {
			this.screen.updateOffsetX(10);
		}

		//Zooming
		if (this.keyH.zInPressed == true) {
			this.screen.updateScale(10);
		}
		if (this.keyH.zOutPressed == true) {
			this.screen.updateScale(-10);
		}
		
		if (this.keyH.upPressed == true) {
			this.level.handleAcceleration();
		}
		
		if (this.keyH.downPressed == true) {
			this.level.handleDeceleration();
		}
		
		// updates level and sets player points
		this.player.setPlayerPoints(0);
		this.level.update();
	}
	
	private void showScoreboard() {
		//TODO: open scoreboard from here
	}
	
	private void showMenu() {
		//TODO: main menu stuff goes here
	}
	
	private void showUserMenu() {
		//TODO: show user menu, get user name and level
		//      from user
		
		// Create a new class in models or business logic for this, and use it.
		// That class can then call the inputView class from views for GUI
		// player name and level number should be returned by the method used.
		
		// then initialize player and level as follows
		Player p = new Player();
		new LevelCatalogue();
		Level l = LevelCatalogue.levels[0];

		this.player = p;
		this.level = l;
		System.out.println("GamePanel[150]: "+level.getLevelName());
		this.state = gameState.PLAY;
	}

}

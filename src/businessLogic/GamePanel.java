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
	public enum gameState { PLAY, WIN, CRASH, SCOREBOARD, MENU, USER_MENU, CREATIVE, EXIT };
	private gameState state = gameState.USER_MENU;
	
	private PlayGameUI playUI = PlayGameUI.getInstance();
	private MessageUI msgUI   = MessageUI.getInstance();
	private CreativeUI creativeUI   = CreativeUI.getInstance();

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
		if (this.state == gameState.PLAY) {
			handleCommonPlayKeypress();
			handlePlayKeypress();
		} else if (this.state == gameState.SCOREBOARD) {
			handleScoreboardKeypress();
		} else if (this.state == gameState.MENU) {
			handleMenuKeypress();
		} else if (this.state == gameState.USER_MENU) {
			handleUserMenuKeypress();
		} else if (this.state == gameState.CRASH) {
			handleCrashKeypress();
		} else if (this.state == gameState.WIN) {
			handleWinKeypress();
		} else if (this.state == gameState.EXIT) {
			handleExit();
		} else if (this.state == gameState.CREATIVE) {
			handleCommonPlayKeypress();
			handleCreativeKeypress();
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
			//TODO: refer line 92, do something like that
			showMenu();
		} else if (this.state == gameState.USER_MENU) {
			//TODO: refer line 92, do something like that
			showUserMenu();
		} else if (this.state == gameState.SCOREBOARD) {
			//TODO: refer line 92, do something like that
			showScoreboard();
		} else if (this.state == gameState.WIN) {
			this.msgUI.draw(g2, screenHeight, screenWidth, true, this.player);
		} else if (this.state == gameState.CREATIVE) {
			this.creativeUI.draw(g2, screen, level);
		}
		g2.dispose();
	}
	
	private void handleCommonPlayKeypress() {
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
		
		if (this.keyH.mPressed == true) {
			this.state = gameState.MENU;
		}
		
		if (this.keyH.xPressed == true) {
			this.state = gameState.EXIT;
		}		
	}
	
	private void handlePlayKeypress() {
		int res = this.level.update();
		this.player.setPlayerPoints((int)((this.level.getTimeAllowed() - this.player.getTimePlayed())/100));
		if (res == -1) {
			this.state = gameState.CRASH;
		} else if (res == 1) {
			this.state = gameState.WIN;
		}
		if (this.player.updateTimePlayed() > this.level.getTimeAllowed()) {
			this.state = gameState.CRASH;
		}
	}

	private void handleCreativeKeypress() {
		this.level.update();
	}
	
	private void handleScoreboardKeypress() {
		// X pressed = quit
		// M pressed = menu
		if (this.keyH.xPressed == true) {
			this.state = gameState.MENU;
			// change this to exit ig
		}
		if (this.keyH.mPressed == true) {
			this.state = gameState.MENU;
		}
	}
	
	private void handleWinKeypress() {
		// TODO Auto-generated method stub
		// X pressed = quit
		// M pressed = menu
		this.player.setPlayerHighScore();
		if (this.keyH.xPressed == true) {
			this.state = gameState.EXIT;
			// change this to exit ig
		}
		if (this.keyH.mPressed == true) {
			this.state = gameState.MENU;
		}
	}

	public void handleExit() {
		System.exit(0);
	}
	
	private void handleCrashKeypress() {
		// TODO Auto-generated method stub
		// X pressed = quit
		// M pressed = menu
		if (this.keyH.xPressed == true) {
			this.state = gameState.EXIT;
			// change this to exit ig
		}
		if (this.keyH.mPressed == true) {
			this.state = gameState.MENU;
		}		
	}

	private void handleUserMenuKeypress() {
		// TODO Auto-generated method stub
	}

	private void handleMenuKeypress() {
		// TODO Auto-generated method stub
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

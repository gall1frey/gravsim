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
	private gameplayScreen screen = new gameplayScreen("C:\\Users\\sirdm\\Documents\\projects\\gravsim2\\assets\\images\\misc\\background.png");
	public enum gameState { PLAY, WIN, CRASH, SCOREBOARD, MENU, USER_MENU, CREATIVE, EXIT };
	private gameState state = gameState.USER_MENU;
	
	private PlayGameUI playUI = PlayGameUI.getInstance();
	private MessageUI msgUI   = MessageUI.getInstance();
	private CreativeUI creativeUI   = CreativeUI.getInstance();
	
	private PlayHandler playHandler = PlayHandler.getInstance();
	private CreativeHandler creativeHandler = CreativeHandler.getInstance();
	private ScoreboardHandler scoreboardHandler = ScoreboardHandler.getInstance();

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
			this.state = playHandler.handlePlay(level, player, state);
		} else if (this.state == gameState.SCOREBOARD) {
			this.state = scoreboardHandler.handleScoreboard(keyH, state);
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
		if (this.keyH.downPressed || this.keyH.upPressed) {
			if (this.keyH.downPressed == true) {
				if (this.level.handleDeceleration())
					this.level.setRocketSprite(-1);
			}
			if (this.keyH.upPressed == true) {
				if (this.level.handleAcceleration())
					this.level.setRocketSprite(1);
			}
		} else {
			this.level.setRocketSprite(0);
		}
		
		
		if (this.keyH.escPressed == true) {
			this.state = gameState.MENU;
		}
		
		if (this.keyH.letterPressed[this.keyH.getLetterCode('M')] == true) {
			this.state = gameState.EXIT;
		}		
	}
	
	private void handleWinKeypress() {
		// TODO Auto-generated method stub
		// X pressed = quit
		// M pressed = menu
		this.player.setPlayerHighScore();
		if (this.keyH.escPressed == true) {
			this.state = gameState.EXIT;
			// change this to exit ig
		}
		if (this.keyH.letterPressed[this.keyH.getLetterCode('M')] == true) {
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
		if (this.keyH.escPressed == true) {
			this.state = gameState.EXIT;
			// change this to exit ig
		}
		if (this.keyH.letterPressed[this.keyH.getLetterCode('M')] == true) {
			this.state = gameState.MENU;
		}		
	}

	private void handleUserMenuKeypress() {
		// TODO Auto-generated method stub
		/*String username = "";
		if (keyH.letterPressed[this.keyH.getLetterCode('A')]) {
			username += "A";
		}
		*/
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
		
		// This func only for *VISUALIZATION*
		// Use handleMenuKeypress to handle the logic of main menu
		
		// Ideally, handleUserMenuKeypress should set player, state and level details into this class' variables.
		
		Player p = new Player();
		new LevelCatalogue();
		Level l = LevelCatalogue.levels[0];

		this.player = p;
		this.level = l;	
		this.state = gameState.PLAY;
		
		if (this.state == gameState.CREATIVE) {
			l.setRocketMove(false);
		}
		
		//TODO: put this next line in where you're setting player, state and level variables
		// This is playframe for PLAY mode. CREATIVE mode playframe coming soon
		this.screen.setPlayFrame("C:\\Users\\sirdm\\Documents\\projects\\gravsim2\\assets\\images\\misc\\play_frame.png");
		//TODO: Create a new playFrame for creative mode
	}

}

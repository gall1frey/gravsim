package businessLogic;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import Controllers.keyHandler;
import Models.Level;
import Models.Player;
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

	int FPS = 60;
	int status = 0;

	public GamePanel(Player player, Level level) {
		this.player = player;
		this.level = level;
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
			
			if (status != 0) {
				break;
			}
		}
	}

	public void update() {
		/* TODO: FILL THIS IN*/
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
		status = this.level.update();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		this.screen.renderBackground(g2,this);
		this.screen.renderEntities(this.level.getEntities(),this.level.isRocketMove(),g2,this);
		this.screen.renderFuelBar(this.level.getFuelPercent(), g2);
		this.screen.renderPlayFrame(g2,this);
		g2.dispose();
	}

}

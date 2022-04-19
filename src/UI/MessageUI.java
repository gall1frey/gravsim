package UI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import Models.Player;

public class MessageUI {
	private static MessageUI ui = null;
	
	private static String victoryString = "GG, SIR!";
	private static String lossString = "FA SCHIFO ESSERE TE";
	private static String menuControl = "[M] Menu";
	private static String exitControl = "[ESC] Exit";
	
	private Color cyan = new Color(84,244,252);
	private Color cyanTransparent = new Color(84,244,252,30);
	private Color red = new Color(224, 0, 37);
	private Color redTransparent = new Color(224,0,37,50);
	
	private MessageUI() {
		
	}
	
	public static MessageUI getInstance() {
		if (ui == null) {
			ui = new MessageUI();
		}
		return ui;
	}
	
	private void readyPen(Graphics2D g2, int fontSize, Color color) {
		g2.setColor(color);
		g2.setFont(new Font("Agency FB", Font.PLAIN, fontSize));
	}
	
	private String getName(Player p) {
		return p.getPlayerName();
	}
	
	private int getPoints(Player p) {
		return p.getPlayerPoints();
	}
	
	private int getHighScore(Player p) {
		return p.getPlayerHighScore();
	}
	
	public void draw(Graphics2D g2, int screenH, int screenW, boolean victory, Player p) {
		// Print message on screen
		// Print player stats if win
		if (victory) {
			readyPen(g2,100, cyan);
			g2.drawString(victoryString, screenW/3+100, screenH/3);
			readyPen(g2,70, cyan);
			g2.drawString(getName(p), screenW/2, screenH/2);
			g2.drawString(String.format("%d",getPoints(p)), screenW/2, screenH/2+100);
			g2.drawString(String.format("%d",getHighScore(p)), screenW/2, screenH/2+200);
			readyPen(g2,30, cyan);
			g2.drawString("PLAYER",screenW/2-90,screenH/2);
			g2.drawString("POINTS",screenW/2-90,screenH/2+95);
			g2.drawString("HIGHSCORE",screenW/2-130,screenH/2+195);
			g2.setStroke(new BasicStroke(10));
			g2.drawRoundRect(screenW/4, screenH/6, screenW/2, screenH*4/6, 35, 35);
			g2.setColor(cyanTransparent);
			g2.fillRoundRect(screenW/4, screenH/6, screenW/2, screenH*4/6, 35, 35);
		} else {
			readyPen(g2,100, red);
			g2.drawString(lossString, screenW/3-120, screenH/2+40);
			g2.setStroke(new BasicStroke(10));
			g2.drawRoundRect(screenW/5, screenH/4, screenW*3/5, screenH/2, 35, 35);
			g2.setColor(redTransparent);
			g2.fillRoundRect(screenW/5, screenH/4, screenW*3/5, screenH/2, 35, 35);
		}
		
		// Print control options
		readyPen(g2,30, cyan);
		g2.drawString(menuControl, screenW-100, screenH-20);
		g2.drawString(exitControl, 10, screenH-20);
	}
}

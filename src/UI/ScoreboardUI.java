package UI;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import Models.Player;

public class ScoreboardUI{
	private static  ScoreboardUI ui = null;
	
	private static String title = "SCORE BOARD";
	private static String menu = "[ M ]   MENU";
	private static String exit = "[ ESC ]   EXIT";
	
	private Color cyan = new Color(84,244,252);
	
	private ScoreboardUI() {}
	
	public static ScoreboardUI getInstance() {
		if (ui == null) {
			ui = new ScoreboardUI();
		}
		return ui;
	}
	
	private void readyPen(Graphics2D g2, int fontSize, Color color) {
		g2.setColor(color);
		g2.setFont(new Font("Agency FB", Font.PLAIN, fontSize));
	}
	
	private String[] getPlayerNames(Player[] p) {
		String[] names = new String[p.length];
		for (int i = 0; i < p.length; i++) {
			if (p[i] != null)
				names[i] = p[i].getPlayerName();
			else
				names[i] = "NONE";
		}
		return names;
	}
	
	private String[] getLevelNames(Player[] p) {
		String[] names = new String[p.length];
		for (int i = 0; i < p.length; i++) {
			if (p[i] != null)
				names[i] = p[i].getLevelName();
			else
				names[i] = "NONE";
		}
		return names;
	}
	
	private int[] getPlayerScores(Player[] p) {
		int[] scores = new int[p.length];
		for (int i = 0; i < p.length; i++) {
			if (p[i] != null)
				scores[i] = p[i].getPlayerHighScore();
			else
				scores[i] = 0;
		}
		return scores;
	}
	
	public void draw(Graphics2D g2, Player[] players) {
		
		String[] names = getPlayerNames(players);
		String[] levels = getLevelNames(players);
		int[] scores = getPlayerScores(players);
		
		readyPen(g2,100,cyan);
		g2.drawString(title, 425, 100);
		readyPen(g2,30,cyan);
		g2.drawString(menu, 800, 690);
		readyPen(g2,30,cyan); 
		g2.drawString(exit, 1000, 690);
		
		for (int i = 0; i < 5; i++) {
			g2.drawString(names[i], 420, 220+70*i);
			g2.drawString(levels[i], 650, 220+70*i);
			g2.drawString(String.format("%03d",scores[i]), 850, 220+70*i);
		}
		
		//g2.drawString(player5, 530, 500);
		
		g2.setStroke(new BasicStroke(5));
		
		g2.drawRoundRect(350, 185, 600, 50, 35, 35);
		g2.drawRoundRect(350, 255, 600, 50, 35, 35);
		g2.drawRoundRect(350, 325, 600, 50, 35, 35);
		g2.drawRoundRect(350, 395, 600, 50, 35, 35);
		g2.drawRoundRect(350, 465, 600, 50, 35, 35);
		
		g2.drawRoundRect(765, 647, 170, 60, 35, 35);
		g2.drawRoundRect(970, 647, 170, 60, 35, 35);

	}
}

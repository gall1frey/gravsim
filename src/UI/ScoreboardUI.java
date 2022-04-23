package UI;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class ScoreboardUI{
	private static  ScoreboardUI ui = null;
	
	private static String title = "SCORE BOARD";
	private static String player1 = "name and score goes here";
	private static String player2 = "name and score goes here";
	private static String player3 = "name and score goes here";
	private static String player4 = "name and score goes here";
	private static String player5 = "name and score goes here";
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
	
	public void draw(Graphics2D g2) {
		readyPen(g2,100,cyan);
		g2.drawString(title, 425, 100);
		readyPen(g2,30,cyan);
		g2.drawString(menu, 800, 690);
		readyPen(g2,30,cyan); 
		g2.drawString(exit, 1000, 690);
		g2.drawString(player1, 530, 200);
		g2.drawString(player2, 530, 270);
		g2.drawString(player3, 530, 340);
		g2.drawString(player4, 530, 410);
		g2.drawString(player5, 530, 480);
		
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(765, 640, 170, 75, 35, 35);
		g2.drawRoundRect(970, 640, 170, 75, 35, 35);
//		g2.drawRoundRect(350, 450, 600, 75, 35, 35);
//		g2.drawRoundRect(350, 550, 600, 75, 35, 35);
	}
}

package UI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class UsermenuUI{
	private static  UsermenuUI ui = null;
	
	private static String player = "ENTER PLAYER NAME";
	
	private static String rocket = "SELECT ROCKET";
	// idk about this
	private static String rck1 = "ROCKET 1 image";
	private static String rck2 = "ROCKET 2 image";

	private static String menu = "[ ESC ]   MENU";
	private static String start = "[ ENTER ]   START";
	
	private Color cyan = new Color(84,244,252);
	
	private UsermenuUI() {}
	
	public static UsermenuUI getInstance() {
		if (ui == null) {
			ui = new UsermenuUI();
		}
		return ui;
	}
	
	
	private void readyPen(Graphics2D g2, int fontSize, Color color) {
		g2.setColor(color);
		g2.setFont(new Font("Agency FB", Font.PLAIN, fontSize));
	}
	
	public void draw(Graphics2D g2, String playerName) {
		readyPen(g2,70,cyan);
		g2.drawString(player, 225, 120);
		g2.drawString(rocket, 225, 400);
		
		readyPen(g2,50,cyan);
		g2.drawString(playerName, 370, 240);
		
		readyPen(g2,30,cyan);
		g2.drawString(rck1, 418, 540);
		g2.drawString(rck2, 756, 540);
		g2.drawString(menu, 780, 690);
		g2.drawString(start, 990, 690);
		
		
		g2.setStroke(new BasicStroke(5));
		
		g2.drawRoundRect(350, 185, 600, 70, 35, 35);
		g2.drawRoundRect(410, 465, 150, 130, 35, 35);
		g2.drawRoundRect(750, 465, 150, 130, 35, 35);
//		g2.drawRoundRect(350, 325, 600, 50, 35, 35);
//		g2.drawRoundRect(350, 395, 600, 50, 35, 35);
//		g2.drawRoundRect(350, 465, 600, 50, 35, 35);
//		
		g2.drawRoundRect(765, 647, 170, 60, 35, 35);
		g2.drawRoundRect(970, 647, 200, 60, 35, 35);

	}
}
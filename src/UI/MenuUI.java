package UI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class MenuUI{
	private static MenuUI ui = null;
	
	private static String title = "GRAV SIM";
	private static String menu = "MENU";
	private static String play = "[ P ]       PLAY GAME";
	private static String scrbrd = "[ S ]       SCOREBOARD";
	private static String creative = "[ C ]       CREATIVE MODE";
	private static String exit = "[ ESC ]   EXIT";
	
	private Color cyan = new Color(84,244,252);
	
	private MenuUI() {}
	
	public static MenuUI getInstance() {
		if (ui == null) {
			ui = new MenuUI();
		}
		return ui;
	}
	
	private void readyPen(Graphics2D g2, int fontSize, Color color) {
		g2.setColor(color);
		g2.setFont(new Font("Agency FB", Font.PLAIN, fontSize));
	}
	
	public void draw(Graphics2D g2) {
		readyPen(g2,100,cyan);
		g2.drawString(title, 480, 100);
		readyPen(g2,40,cyan);
		g2.drawString(menu, 600, 180);
		readyPen(g2,30,cyan); 
		g2.drawString(play, 550, 300);
		g2.drawString(scrbrd, 540, 400);
		g2.drawString(creative, 530, 500);
		g2.drawString(exit, 560, 600);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(350, 250, 600, 75, 35, 35);
		g2.drawRoundRect(350, 350, 600, 75, 35, 35);
		g2.drawRoundRect(350, 450, 600, 75, 35, 35);
		g2.drawRoundRect(350, 550, 600, 75, 35, 35);
	}
}
	

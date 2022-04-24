package UI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class CreativeMenuUI {
	private static CreativeMenuUI ui = null;
	
	private static String file = "ENTER CSV FILE PATH";
	
	private static String menu = "[ ESC ]   MENU";
	private static String start = "[ ENTER ]   START";
	
	private Color cyan = new Color(84,244,252);
	
	private CreativeMenuUI() {}
	
	public static CreativeMenuUI getInstance() {
		if (ui == null) {
			ui = new CreativeMenuUI();
		}
		return ui;
	}
	
	private void readyPen(Graphics2D g2, int fontSize, Color color) {
		g2.setColor(color);
		g2.setFont(new Font("Agency FB", Font.PLAIN, fontSize));
	}
	
	public void draw(Graphics2D g2, String filePath) {
		if (filePath == null)
			filePath = "";
		readyPen(g2,70,cyan);
		g2.drawString(file, 425, 420);
		
		readyPen(g2,50,cyan);
		g2.drawString(filePath, 370, 240);
		
		readyPen(g2,30,cyan);
		
		g2.drawString(menu, 780, 690);
		g2.drawString(start, 990, 690);
		
		
		g2.setStroke(new BasicStroke(5));
		
		g2.drawRoundRect(350, 185, 600, 70, 35, 35);

		g2.drawRoundRect(765, 647, 170, 60, 35, 35);
		g2.drawRoundRect(970, 647, 200, 60, 35, 35);

	}
	
}

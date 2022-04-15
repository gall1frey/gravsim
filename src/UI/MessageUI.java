package UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class MessageUI {
	private static MessageUI ui = null;
	
	private MessageUI() {
		
	}
	
	public static MessageUI getInstance() {
		if (ui == null) {
			ui = new MessageUI();
		}
		return ui;
	}
	
	public void draw(Graphics2D g2, int screenH, int screenW, String msg, int score) {
		//TODO: find what color I used for this lmao
		//The cyan one
		Color white = new Color(255,255,255);
		g2.setColor(white);
		g2.setFont(new Font("Agency FB", Font.PLAIN, 50));
		g2.drawString(msg, screenW/3, screenH/4);
	}
}

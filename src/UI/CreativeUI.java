package UI;

import java.awt.Graphics2D;

import Models.Level;
import Views.gameplayScreen;

public class CreativeUI {
	private static CreativeUI ui = null;
	
	private CreativeUI() {}
	
	public static CreativeUI getInstance() {
		if (ui == null) {
			ui = new CreativeUI();
		}
		return ui;
	}
	
	public void draw(Graphics2D g2, gameplayScreen screen, Level level) {
		screen.renderEntities(level.getEntities(), level.isRocketMove(), g2, null);
		screen.renderPlanetDetails(level.getEntities(), false, g2, null);
	}
}

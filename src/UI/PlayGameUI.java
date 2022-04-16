package UI;

import java.awt.Graphics2D;

import Models.Level;
import Models.Player;
import Views.gameplayScreen;

public class PlayGameUI {
	private static PlayGameUI ui = null;
	
	private PlayGameUI() {}
	
	public static PlayGameUI getInstance() {
		if (ui== null) {
			ui= new PlayGameUI();
		}
		return ui;
	}
	
	public void draw(Graphics2D g2, gameplayScreen screen, Level level, Player player) {
		screen.renderEntities(level.getEntities(),level.isRocketMove(),g2,null);
		screen.renderFuelBar(level.getFuelPercent(), g2);
		screen.renderPlayerName(player.getPlayerName(),g2);
		screen.renderPlayDetails(level.getRocketVel(),level.getDistToGo(),level.getDistTravelled(),g2);
		screen.renderPlayFrame(g2,null);
	}
}

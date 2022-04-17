package businessLogic;

import Controllers.keyHandler;
import businessLogic.GamePanel.gameState;

public class ScoreboardHandler {
	private static ScoreboardHandler handler = null;
	/* Use this class to make db calls and stuff */
	private ScoreboardHandler() {}
	
	public gameState handleScoreboard(keyHandler keyH, gameState state) {
		// X pressed = quit
		// M pressed = menu
		if (keyH.xPressed == true) {
			state = gameState.MENU;
			// change this to exit ig
		}
		if (keyH.mPressed == true) {
			state = gameState.MENU;
		}
		return state;
	}
	
	public static ScoreboardHandler getInstance() {
		if (handler == null) {
			handler = new ScoreboardHandler();
		}
		return handler;
	}
}

package businessLogic;

import Controllers.keyHandler;
import businessLogic.GamePanel.gameState;

public class ScoreboardHandler {
	private static ScoreboardHandler handler = null;

	private ScoreboardHandler() {}

	public gameState handleScoreboard(keyHandler keyH, gameState state) {
		// Esc pressed = quit
		// M pressed = menu
		if (keyH.escPressed == true) {
			state = gameState.EXIT;
			keyH.escPressed = false;
			// change this to exit ig ---- no! ----- yes! or you'd have two menu buttons
		}
		if (keyH.letterPressed[keyH.getLetterCode('M')]== true) {
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

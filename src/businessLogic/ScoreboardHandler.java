package businessLogic;

import Controllers.keyHandler;
import businessLogic.GamePanel.gameState;

public class ScoreboardHandler {
	private static ScoreboardHandler handler = null;
	/* Use this class to make db calls and stuff for players and scores */
	private ScoreboardHandler() {}
	
	public gameState handleScoreboard(keyHandler keyH, gameState state) {
		// Esc pressed = quit
		// M pressed = menu
		if (keyH.escPressed == true) {
			state = gameState.MENU;
			// change this to exit ig ---- no!
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

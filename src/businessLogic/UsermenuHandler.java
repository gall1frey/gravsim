package businessLogic;

import Controllers.keyHandler;
import businessLogic.GamePanel.gameState;


public class UsermenuHandler {
	private static UsermenuHandler handler = null;
	/* Use this class to make db calls and stuff for rockets */
	private UsermenuHandler() {}
	
	public gameState handleUsermenu(keyHandler keyH, gameState state) {
		// P pressed = start game
		// M pressed = menu
		if (keyH.escPressed == true) {
			state = gameState.MENU;
			// change this to exit ig ---- no!
		}
		if (keyH.letterPressed[keyH.getLetterCode('M')]== true) {
			state = gameState.MENU;
		}
		if (keyH.letterPressed[keyH.getLetterCode('P')]== true) {
			state = gameState.PLAY;
		}
		return state;
	}
	
	public static UsermenuHandler getInstance() {
		if (handler == null) {
			handler = new UsermenuHandler();
		}
		return handler;
	}
}




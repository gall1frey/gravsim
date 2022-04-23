package businessLogic;

import Controllers.keyHandler;
import Models.Level;
import Models.Player;
import businessLogic.GamePanel.gameState;

public class PlayHandler {
	private static PlayHandler handler = null;
	
	private PlayHandler() {}
	
	public gameState handlePlay(keyHandler keyH, Level level, Player player, gameState state) {
		int res = level.update();
		player.setPlayerPoints((int)((level.getTimeAllowed() - player.getTimePlayed())/100));
		if (res == -1) {
			state = gameState.CRASH;
		} else if (res == 1) {
			state = gameState.WIN;
		}
		if (player.updateTimePlayed() > level.getTimeAllowed()) {
			state = gameState.CRASH;
		}
		
		if (keyH.downPressed || keyH.upPressed) {
			if (keyH.downPressed == true) {
				if (level.handleDeceleration())
					level.setRocketSprite(-1);
				else
					level.setRocketSprite(0);
			}
			if (keyH.upPressed == true) {
				if (level.handleAcceleration())
					level.setRocketSprite(1);
				else
					level.setRocketSprite(0);
			}
		} else {
			level.setRocketSprite(0);
		}

		return state;
	}
	
	public static PlayHandler getInstance() {
		if (handler == null) {
			handler = new PlayHandler();
		}
		return handler;
	}
}

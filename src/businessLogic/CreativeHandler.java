package businessLogic;

import Models.Level;

public class CreativeHandler {
	private static CreativeHandler handler = null;
	
	private CreativeHandler() {}
	
	public void handleCreative(Level level) {
		level.update();
	}
	
	public static CreativeHandler getInstance() {
		if (handler == null) {
			handler = new CreativeHandler();
		}
		return handler;
	}
}

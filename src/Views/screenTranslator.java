package Views;

import Models.Physics;

public class screenTranslator {
	private static screenTranslator translator_instance = null;
	private Physics physics = Physics.getInstance();
	
	public static screenTranslator getInstance() {
		if (translator_instance == null) {
			translator_instance = new screenTranslator();
		}
		return translator_instance;
	}
	
	public int[] convertPosToPixel(double x, double y, double radius, int offset_x, int offset_y) {
		int new_r = Math.max((int) (physics.mToAu(radius * physics.getScale() * 1e13)),25);
		int new_x = (int) (physics.auToM(x) * physics.getScale() + offset_x - new_r);
		int new_y = (int) (physics.auToM(y) * physics.getScale() + offset_y - new_r);
		return new int[] {new_x, new_y, new_r};
	}
}

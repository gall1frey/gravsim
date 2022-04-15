package Views;

import Models.Physics;

public class screenTranslator {
	//int scale;
	private static screenTranslator translator_instance = null;
	private Physics physics = Physics.getInstance();
	
	public static screenTranslator getInstance() {
		if (translator_instance == null) {
			translator_instance = new screenTranslator();
		}
		return translator_instance;
	}
	
	public int[] convertPosToPixel(double x, double y, double radius, int offset_x, int offset_y) {
		/*TODO: diven x and y coords in metres (double), convert to positions on screen*/
		//System.out.println("screenTranslator[19]: "+physics.mToAu(radius) * physics.getScale()*1e16);
		int new_r = Math.max((int) (physics.mToAu(radius * physics.getScale() * 1e13)),10);
		int new_x = (int) (physics.auToM(x) * physics.getScale() + offset_x - new_r);
		int new_y = (int) (physics.auToM(y) * physics.getScale() + offset_y - new_r);
		//System.out.println("screenTranslator 22: {"+new_x+", "+new_y+", "+new_r+"}");
		return new int[] {new_x, new_y, new_r};
	}
}

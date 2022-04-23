package businessLogic;

import Models.Entity;
import Models.Level;
import Models.Planet;
import Models.Rocket;

public class LevelCatalogue {

	private static Level curLevel = null;
	//private static Level curLevelBkp = null;

	public static Level[] levels = new Level[10];
	public static int levCount = 0;

	private static void addLevel(String name, Entity[] objects, boolean rocket_move, boolean planets_move) {
		if (levCount < 10) {
			Level l = new Level(name, objects, rocket_move, planets_move,50000);
			levels[levCount] = l;
			levCount += 1;
		}
	}

	public LevelCatalogue() {}

	private static void start() {
		Rocket r = new Rocket((float) 0.5,20000,420000,109/2,100,"C:\\Users\\sirdm\\Documents\\projects\\gravsim\\assets\\images\\rockets\\rocket1",200);
		r.setPos(1, 2.80753e-6);
		r.setVel(7777.7778, 0);
		Planet p1 = new Planet("Sun", 1.9891e30, 696340000, "C:\\Users\\sirdm\\Documents\\projects\\gravsim\\assets\\images\\planets\\planet1.png", 200);
		p1.setPos(2, 2);
		p1.setVel(0, 0);
		Planet p2 = new Planet("Earth", 5.974e24, 6371000, "C:\\Users\\sirdm\\Documents\\projects\\gravsim\\assets\\images\\planets\\planet2.png", 200);
		p2.setPos(1, 2);
		p2.setVel(0, -30000);
		Entity[] eList = new Entity[3];
		eList[0] = r;
		eList[1] = p1;
		eList[2] = p2;
		addLevel("Basic", eList, true, false);
	}

	public static Level getInstance(int n) {
		if (curLevel == null) {
			start();
			curLevel = levels[n];
		} else if (!curLevel.equals(levels[n])) {
			curLevel = levels[n];
		}
		//System.out.println("LevelCatalogue [33]: "+curLevel.getEntities()[2].getPos()[0]+"\t"+curLevel.getEntities()[2].getPos()[1]);
		return curLevel;
	}

	public static void resetInstance() {
		curLevel = null;
		start();
	}

}

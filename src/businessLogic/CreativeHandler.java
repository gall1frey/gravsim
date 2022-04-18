package businessLogic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import Models.Entity;
import Models.Level;
import Models.Planet;

public class CreativeHandler {
	private static CreativeHandler handler = null;
	String[] SpritePaths = {
		"C:\\Users\\sirdm\\Documents\\projects\\gravsim\\assets\\images\\planets\\planet1.png",
		"C:\\Users\\sirdm\\Documents\\projects\\gravsim\\assets\\images\\planets\\planet2.png",
		"C:\\Users\\sirdm\\Documents\\projects\\gravsim\\assets\\images\\planets\\planet3.png"
	};
	int randomIndex = (int) ((Math.random() * (2 - 0)) + 0);
	
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
	
	private String loadFromFile(String filePath) {
		Scanner sc = null;
		String res = "";
		try {
			sc = new Scanner(new File(filePath));
			while (sc.hasNextLine()) {  
                String fileData = sc.nextLine();  
                res += fileData +"\n";  
            }  
		} catch (FileNotFoundException e) {
			System.out.println("CreativeHandler [30]: Error opening file!");
			e.printStackTrace();
			return "ERROR!";
		}
		return res;
	}
	
	private String getRandomSpritePath() {
		return SpritePaths[randomIndex];
	}	
	
	public Level getLevel(String filePath) {
		String csvData = loadFromFile(filePath);
		String[] entities = csvData.split("\n");
		Entity[] entityList = new Entity[entities.length];
		for (int i = 0; i < entities.length; i++) {
			String[] params = entities[i].split(",");
			String name = params[0];
			double mass = Double.parseDouble(params[1]);
			double posX = Double.parseDouble(params[2]);
			double posY = Double.parseDouble(params[3]);
			double velX = Double.parseDouble(params[4]);
			double velY = Double.parseDouble(params[5]);
			double radius = Double.parseDouble(params[6]);
			Planet p = new Planet(name, mass, radius, getRandomSpritePath(), 200);
			p.setPos(posX, posY);
			p.setVel(velX, velY);
			entityList[i] = p;
		}
		Level l = new Level("CreativeLevel", entityList, false, true, 100000000);
		return l;
	}
	
}

package businessLogic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import Controllers.keyHandler;
import Models.Entity;
import Models.Level;
import Models.Planet;
import businessLogic.GamePanel.gameState;

public class CreativeHandler {
	private static CreativeHandler handler = null;
	String[] SpritePaths = {
		"assets\\images\\planets\\planet1.png",
		"assets\\images\\planets\\planet2.png",
		"assets\\images\\planets\\planet3.png"
	};

	
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
		int randomIndex = (int) ((Math.random() * (SpritePaths.length - 0)) + 0);
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

	public gameState handleCreativeMenu(keyHandler keyH, String filePath) {
		gameState state = gameState.CREATIVE_MENU;
		if (keyH.enterPressed == true && filePath.length() > 0) {
			state = gameState.CREATIVE;
		} else if (keyH.escPressed == true) {
			state = gameState.MENU;
			keyH.escPressed = false;
		}
		return state;
	}
	
}

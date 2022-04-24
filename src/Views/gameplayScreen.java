package Views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Models.Entity;
import Models.Physics;
import Models.Planet;
import Models.Rocket;
import Models.Trail;
import businessLogic.GamePanel;

public class gameplayScreen {
	private int offsetX = 0;
	private int offsetY = 0;

	private int fuelBarLen = 809;
	private int fuelBarx = 13;
	private int fuelBary = 670;
	private int fuelBarHeight = 30;

	private Color cyan = new Color(84,244,252);
	private Color red = new Color(224, 0, 37);
	
	Image background;
	Image playFrame;
	
	screenTranslator st_instance = new screenTranslator();
	public Physics physics = Physics.getInstance();

	public gameplayScreen(String background_path) {
		try {
			background = ImageIO.read(new File(background_path));
			//playFrame = ImageIO.read(new File(playframe_path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setPlayFrame(String playframe_path ) {
		try {
			playFrame = ImageIO.read(new File(playframe_path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateScale(double scaleFactor) {
		physics.updateScale(physics.scaleFactor+scaleFactor);
	}
	
	public void renderFuelBar(float fuelBarPercent, Graphics2D g) {
		/*TODO: draw the fuel bar*/
		if (fuelBarPercent <= 10) {
			g.setColor(red);
		} else {
			g.setColor(cyan);
		}
		g.fillRect(fuelBarx, fuelBary, (int) (fuelBarPercent*fuelBarLen/100), fuelBarHeight);
	}
	
	public int getOffetX() {
		return offsetX;
	}
	
	public int getOffetY() {
		return offsetY;
	}
	
	public void updateOffsetX(int val) {
		offsetX += val;
	}
	
	public void updateOffsetY(int val) {
		offsetY += val;
	}

	public void renderBackground(Graphics2D g, GamePanel observer) {
		g.drawImage(background, 0, 0, observer);
	}

	public void resetScreen() {
		offsetX = 0;
		offsetY = 0;
		physics.updateScale(250);
	}
	
	public void renderEntities(Entity[] entities_list, boolean rocket_exists, Graphics2D g, GamePanel observer) {
		for (int i = entities_list.length-1; i >= 0; i--) {
			if (entities_list[i].getClass() == Planet.class) {
				Planet p = (Planet) entities_list[i];
				renderTrail(p.trail, g);
				Image image_to_render = p.getPlanetSprite();
				int[] position = st_instance.convertPosToPixel(p.getPos()[0], p.getPos()[1], p.radius, offsetX, offsetY);
				g.drawImage(image_to_render, position[0], position[1], position[2]*2, position[2]*2, observer);

			} else if (entities_list[i].getClass() == Rocket.class && rocket_exists){
				Rocket r = (Rocket) entities_list[i];
				renderTrail(r.trail,g);
				Image rocket_img = rotate((BufferedImage) r.sprite,physics.getAngle(r.getVel()[0], r.getVel()[1])+Math.PI/2);
				int[] rocket_pos = st_instance.convertPosToPixel(r.getPos()[0], r.getPos()[1], r.radius, offsetX, offsetY);
				g.drawImage(rocket_img, rocket_pos[0], rocket_pos[1], rocket_pos[2]*2, rocket_pos[2]*2, observer);
			}
		}
	}
	
	public void renderTrail(Trail t, Graphics2D g) {
		// This function works. Setting the trail is the problem.
		g.setColor(cyan);
		if (t.pathTravelled.size() >= 2) {
			System.out.println("gameplayScreen [127]: HERE!");
			int[] x = new int[t.pathTravelled.size()];
			int[] y = new int[t.pathTravelled.size()];
			for (int i = 0; i < t.pathTravelled.size(); i++) {
	            double[] tmp = t.pathTravelled.get(i);
	            int[] tmp2 = new int[2];
	            tmp2 = st_instance.convertPosToPixel(tmp[0], tmp[1], 2, offsetX, offsetY);
	            x[i] = tmp2[0];
	            y[i] = tmp2[1];
	        }
			Path2D polyline = new Path2D.Double();
	        polyline.moveTo(x[0], y[0]);
	        for (int i = 1; i < x.length-1; i+=1) {
	             polyline.lineTo(x[i], y[i]);
	        }
	        g.draw(polyline);
		}
		/*int[] x = new int[] {10,20,30,40,50,60,70,80,90,100,110,120,130,140,150};
		int[] y = new int[] {20,30,50,70,110,170,190,230,290,310,370,410,430,470,530};
		Path2D polyline = new Path2D.Double();
        polyline.moveTo(x[0], y[0]);
        for (int i = 1; i < x.length-1; i+=1) {
             polyline.lineTo(x[i], y[i]);
        }
        g.draw(polyline);*/
		//g.drawPolyline(x, y, x.length-1);
	}

	public void renderPlayFrame(Graphics2D g, GamePanel observer) {
		g.drawImage(playFrame, 0, 0, observer);
	}

	public void renderPlayerName(String name, Graphics2D g) {
		/*TODO: Render name of player on screen*/
		String name_to_print = name.substring(0, 3).toUpperCase();
		g.setColor(cyan);
		g.setFont(new Font("Agency FB", Font.PLAIN, 100));
		g.drawString(name_to_print, 1100, 700);
	}

	public void renderCreativeDetail(int planetsInPlay, float time, Graphics2D g) {
		time = physics.getRealWorldTime(time);
		String timeString = String.format("%.2f", time);
		String planetString = String.format("%03d", planetsInPlay);
		g.setColor(cyan);
		g.setFont(new Font("Agency FB", Font.PLAIN, 50));
		g.drawString(timeString,533,80);
		g.drawString(planetString,35,80);
		String timeUnits = "yrs";
		g.setFont(new Font("Agency FB", Font.PLAIN, 20));
		g.drawString(timeUnits, 613, 80);
	}
	
	public void renderPlayDetails(double vel, double dist_travelled, double dist_togo, float time, int points, Graphics2D g) {
		String velString = String.format("%.2f",vel/1000);// + "km/s";
		String toGoString = String.format("%.0f",dist_togo/1e9);
		String travelledString = String.format("%04.0f",dist_travelled/1e9);
		time = physics.getRealWorldTime(time);
		String timeString = String.format("%.2f", time);
		String pointString = String.format("%05d", points);
		String velUnits = "km/s";
		String distUnits = "M km";
		String timeUnits = "yrs";
		g.setColor(cyan);
		g.setFont(new Font("Agency FB", Font.PLAIN, 50));
		g.drawString(velString, 15, 80);
		g.drawString(toGoString, 1110, 80);
		g.drawString(timeString,533,80);
		g.drawString(travelledString, 275, 80);
		g.drawString(pointString, 943, 660);
		g.setFont(new Font("Agency FB", Font.PLAIN, 20));
		g.drawString(velUnits, 100, 80);
		g.drawString(distUnits, 380, 80);
		g.drawString(timeUnits, 613, 80);
		g.drawString(distUnits, 1180, 80);
	}

	public void renderPlanetDetails(Entity[] entities_list, boolean rocket_exists, Graphics2D g, GamePanel observer) {
		for (int i = 0; i < entities_list.length; i++) {
			if (entities_list[i].getClass() == Planet.class) {
				Planet p = (Planet) entities_list[i];
				String name = p.name;
				int[] pos = st_instance.convertPosToPixel(p.getPos()[0], p.getPos()[1], p.radius, offsetX, offsetY);
				g.setColor(cyan);
				g.setFont(new Font("Agency FB", Font.PLAIN, 15));
				String toPrint = name + " [" + String.format("%4.2f", entities_list[i].getVel()[0]/1000) + "km/s, " + String.format("%4.2f", entities_list[i].getVel()[1]/1000) + "km/s]";
				g.drawString(toPrint,pos[0],pos[1]);
			}
		}
	}
	
	public BufferedImage rotate(BufferedImage image, double angle) {
	    double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
	    BufferedImage result = null;
	    if (image != null) {
	    	int w = image.getWidth(), h = image.getHeight();
		    int neww = (int)Math.floor(w*cos+h*sin), newh = (int) Math.floor(h * cos + w * sin);
		    GraphicsConfiguration gc = getDefaultConfiguration();
		    result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
		    Graphics2D g = result.createGraphics();
		    g.translate((neww - w) / 2, (newh - h) / 2);
		    g.rotate(angle, w / 2, h / 2);
		    g.drawRenderedImage(image, null);
		    g.dispose();
	    }
	    return result;
	}
	
	private static GraphicsConfiguration getDefaultConfiguration() {
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice gd = ge.getDefaultScreenDevice();
	    return gd.getDefaultConfiguration();
	}

}

package Models;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Planet extends Entity{
	private Image planet_sprite;
	
	public Planet(String name, double mass, double radius, String sprite_path, long trail_length) {
		this.name = name;
		this.mass = mass;
		this.radius = radius;
		this.velocity = new double[2];
	    this.position = new double[2];
	    this.setPlanetSprite(sprite_path);
	    this.trail = new Trail();
	    this.trail.setLenOfTrail(trail_length);
	}
	
	public Image getPlanetSprite() {
		return planet_sprite;
	}
	
	public void setPlanetSprite(String sprite) {
		Image img;
		try {
			img = ImageIO.read(new File(sprite));
			planet_sprite = img;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

package Models;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Rocket extends Entity {
	private float fuelPercentage;
	public Image rocketSprite;
	public Image[] rocketSpriteAccelerating = new Image[2];
	public Image[] rocketSpriteDecelerating = new Image[2];
	public Image sprite;
	public int spriteCounter;
	public int spriteNum;
	public Image curSprite;
	float fuelBurnedPerTime;
	float forcePerFuelBurnt;
	double distTravelled;
	
	public Rocket(float fuel_per_time, float force, double mass, double size, float fuel_percentage, String name, long trail_length) {
		this.mass = mass;
		this.radius = size;
		this.name = name;
		this.position = new double[2];
		this.velocity = new double[2];
		this.spriteCounter = 0;
		this.fuelPercentage = fuel_percentage;
		this.fuelBurnedPerTime = fuel_per_time;
		this.forcePerFuelBurnt = force;
		this.setRocketSprite(name+".png",name+"a.png",name+"d.png");
		this.trail = new Trail();
		this.trail.setLenOfTrail(trail_length);
		this.distTravelled = 0.0;
	}
	
	public void updateDistTravelled(double newDist) {
		this.distTravelled += newDist;
	}
	
	public void setRocketSprite(String sprite, String accelerating_sprite, String decelerating_sprite) {
		BufferedImage img0, img1, img2, img3, img4;
		try {
			img0 = ImageIO.read(new File(sprite));
			img1 = ImageIO.read(new File(accelerating_sprite));
			img2 = ImageIO.read(new File(decelerating_sprite));
			img3 = ImageIO.read(new File(accelerating_sprite));
			img4 = ImageIO.read(new File(decelerating_sprite));
			this.rocketSprite = img0;
			this.rocketSpriteAccelerating[0] = img1;
			this.rocketSpriteDecelerating[0] = img2;
			this.rocketSpriteAccelerating[1] = img3;
			this.rocketSpriteDecelerating[1] = img4;
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
		
	public float getFuelPercentage() {
		return this.fuelPercentage;
	}
	
	public void setFuelPercentage(float f) {
		this.fuelPercentage = f;
	}
	
	public void accelerateTo(double[] new_vel) {
		if (this.fuelPercentage > 0) {
			this.setFuelPercentage(this.fuelPercentage - this.fuelBurnedPerTime);
			this.velocity = new_vel;
		}
	}
	
}

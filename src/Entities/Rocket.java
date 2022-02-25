package Entities;

import java.awt.Image;

//import Physics.Physics;

public class Rocket extends Entity {
	/*
		Class Rocket defines a Rocket
	*/

	double fuel_percentage;
	Image rocket_sprite;
	Image[] rocket_sprite_accelerating;
	Image[] rocket_sprite_decelerating;
	double max_acceleration;
	double[] pos;
	double[] velocity;

	/*
		Constructor
	*/
	public Rocket(double mass_, double max_acceleration_, double fuel_percentage_) {
		mass=mass_;
		max_acceleration = max_acceleration_;
		fuel_percentage = fuel_percentage_;
	}

	/*
		Constructor
	*/
	public Rocket(Rocket r) {
		mass = r.mass;
		volume = r.volume;
		max_acceleration = r.max_acceleration;
	}

	/*
		Set percentage of fuel in rocket
		Returns nothing
	*/
	public void setFuelPercentage(float percentage) {
		fuel_percentage = percentage;
	}

	/*
		Rocket sprites include five images:
		sprite - idle rocket
		accelerating_sprite - two images animating accelerating rocket
		decelerating_sprite - two images animating decelerating rocket
	*/
	public void set_rocket_sprite(String sprite, String accelerating_sprite, String decelerating_sprite) {

		// TODO: Load sprite images into variables

		//rocket_sprite = sprite;
		//rocket_sprite_accelerating = accelerating_sprite;
		//rocket_sprite_decelerating = decelerating_sprite;
	}

}

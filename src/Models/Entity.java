package Models;

public abstract class Entity {
	/*
	 Class Entity: defines objects in space that have mass and velocity 
	*/	
	public double mass; // Mass in KG
	protected double[] velocity; // Velocity in m/s
	protected double[] position; // Position in AU
	public double radius; // Size of object (consider radius) in m
	public String name; // Name of entity
	public Trail trail; // Object to store trail of entity
	
	public void setPos(double x, double y) {
		this.position[0] = x;
		this.position[1] = y;
	}
	
	public double[] getPos() {
		return this.position;
	}
	
	public void setVel(double vx, double vy) {
		/*
		 REMEMBER TO ONLY PASS VELOCITIES IN m/s 
		*/
		this.velocity[0] = vx;
		this.velocity[1] = vy;
	}
	
	public double[] getVel() {
		return this.velocity;
	}
	
}

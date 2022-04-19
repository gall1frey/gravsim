package Models;

public class Level {
	protected String levelName;
	private Entity[] entities;
	protected boolean rocketMove;
	protected boolean planetsMove;
	private Physics physics;
	@SuppressWarnings("unused")
	private Planet goalPlanet = null;
	private long timeAllowed; // in milliseconds

	public Level(String name, Entity[] objects, boolean rocket_move, boolean planets_move, long timeAllowed) {
		this.levelName = name;
		// First entity is rocket. Rest are planets.
		this.setEntities(objects);
		this.setRocketMove(rocket_move);
		this.setPlanetsMove(planets_move);
		this.setTimeAllowed(timeAllowed);
		this.physics = Physics.getInstance();
		if (rocket_move) {
			this.goalPlanet = (Planet) objects[objects.length-1];
		}
	}
	
	public boolean handleAcceleration() {
		//if rocket exists
		if (this.rocketMove) {
			Rocket r = (Rocket) entities[0];
			if (r.getFuelPercentage() > 0) {
				double[] cur_vel = r.getVel();
				double vel = physics.newVel(r.forcePerFuelBurnt, r.mass);
				double theta = physics.getAngle(cur_vel[0],cur_vel[1]);
				double[] components = physics.getComponents(vel, theta);
				cur_vel[0] += components[0];
				cur_vel[1] += components[1];
				r.accelerateTo(cur_vel);
				return true;
			}
			return false;
		}
		return false;
	}
	
	public boolean handleDeceleration() {
		//if rocket exists
		if (this.rocketMove) {
			Rocket r = (Rocket) entities[0];
			if (r.getFuelPercentage() > 0) {
				double[] cur_vel = r.getVel();
				double vel = physics.newVel(r.forcePerFuelBurnt, r.mass);
				double theta = physics.getAngle(cur_vel[0],cur_vel[1]);
				double[] components = physics.getComponents(vel, theta);
				cur_vel[0] -= components[0];
				cur_vel[1] -= components[1];
				r.accelerateTo(cur_vel);
				return true;
			}
			return false;
		}
		return false;
	}

	public void setRocketSprite(int n) {
		if (this.entities[0].getClass() == Rocket.class) {
			Rocket r = (Rocket) this.entities[0];
			if (n == 1) {
				// accelerating
				r.sprite = r.rocketSpriteAccelerating[r.spriteNum];
			} else if (n == -1) {
				// accelerating
				r.sprite = r.rocketSpriteDecelerating[r.spriteNum];
			} else {
				r.sprite = r.rocketSprite;
			}
		}
	}
	
	public int update() {
		// For every object in space, calculate force experienced by it due to every other object in space
		// if rocket collides 		-> return -1
		// if rocket reaches goal 	-> return 1
		// if all smooth 			-> return 0
		for (int i = 0; i < this.entities.length; i++) {
			double total_fx = 0;
			double total_fy = 0;
			if (this.entities[i].getClass() == Rocket.class) {
				Rocket r = (Rocket) this.entities[i];
				r.spriteCounter++;
				if (r.spriteCounter > 10) {
					if(r.spriteNum == 0)
						r.spriteNum = 1;
					if (r.spriteNum == 1)
						r.spriteNum = 0;
				}
			}
			for (int j = 0; j < this.entities.length; j++) {
				if (i != j) {
					if (this.rocketMove && this.entities[i].getClass() == Rocket.class) {
						double thresh = 1e10;
						int retval = -1;
						if (this.entities[j].name == this.goalPlanet.name) {
							retval = 1;
							thresh = 1.5e10;
						}
						if (this.Collision(this.entities[i].position, this.entities[j].position, this.entities[i].radius, this.entities[j].radius,thresh)) {
							return retval;
						}
					}
					double[] pos1 = physics.auToM(this.entities[i].position);
					double[] pos2 = physics.auToM(this.entities[j].position);
					double[] force = physics.gravForce(this.entities[i].mass, this.entities[j].mass, pos1, pos2);
					double force_x = force[0];
					double force_y = force[1];
					total_fx += force_x;
					total_fy += force_y;
				}
			}
			
			// Use force to calculate updated velocity
			this.entities[i].velocity[0] += physics.newVel(total_fx, this.entities[i].mass);
			this.entities[i].velocity[1] += physics.newVel(total_fy, this.entities[i].mass);
			
			// update dist+travelled
			this.updateDistTravelled(this.entities[i]);
						
			addTrail(this.entities[i]);
			
			// Use velocity to calculate updated position
			this.entities[i].position[0] += physics.mToAu(physics.newPos(this.entities[i].velocity[0]));
			this.entities[i].position[1] += physics.mToAu(physics.newPos(this.entities[i].velocity[1]));

		}
		return 0;
	}
	
	public void addTrail(Entity e) {
		//TODO: Fill this in
		if (e.trail.pathTravelled.size() == 0) {
			e.trail.addPathTravelled(e.position);
		} else {
			double[] pos1 = e.position;
			double[] pos2 = e.trail.pathTravelled.peekLast();
			System.out.println("Level [112]: "+physics.getDistance(physics.auToM(pos1), physics.auToM(pos2))+"\t"+(1/physics.getScale()));
			if (physics.auToM(physics.getDistance(pos1, pos2)) >= (1/physics.getScale())) {
				e.trail.addPathTravelled(pos1);
			}
		}
	}
	
	public boolean Collision(double[] pos1, double[] pos2, double rad1, double rad2, double thresh) {
		//System.out.println("Level[100]: "+physics.getDistance(physics.auToM(pos1), physics.auToM(pos2))+"\t"+rad1+rad2+thresh);
		if (physics.getDistance(physics.auToM(pos1), physics.auToM(pos2)) <= rad1 + rad2 + thresh) {
			return true;
		}
		return false;
	}
	
	public float getFuelPercent() {
		//if rocket exists
		if (this.rocketMove) {
			Rocket r = (Rocket) entities[0];
			return r.getFuelPercentage();
		}
		return (float) 0.0;
	}

	public String getLevelName() {
		return this.levelName;
	}

	public Entity[] getEntities() {
		return entities;
	}

	public void setEntities(Entity[] entities) {
		this.entities = entities;
	}

	public boolean isPlanetsMove() {
		return planetsMove;
	}

	public void setPlanetsMove(boolean planetsMove) {
		this.planetsMove = planetsMove;
	}

	public boolean isRocketMove() {
		return rocketMove;
	}

	public void setRocketMove(boolean rocketMove) {
		this.rocketMove = rocketMove;
	}
	
	public double getRocketVel() {
		if (this.rocketMove) {
			Rocket r = (Rocket) entities[0];
			double[] v = r.getVel();
			return Math.sqrt(Math.pow(v[0],2)+Math.pow(v[0],2));
		}
		return 0.0;
	}
	
	public double getDistToGo() {
		if (this.rocketMove) {
			Rocket r = (Rocket) entities[0];
			Planet p = (Planet) entities[entities.length - 1];
			double[] rocketPos = r.getPos();
			double[] planetPos = p.getPos();
			double dist = physics.getDistance(physics.auToM(planetPos), physics.auToM(rocketPos));
			return dist;
		}
		return 0.0;
	}
	
	public void updateDistTravelled(Entity e) {
		if (this.rocketMove && e.getClass() == Rocket.class) {
		double[] old_pos = e.position;
			double[] new_pos = new double[2];
			new_pos[0] = physics.newPos(e.velocity[0]);
			new_pos[1] = physics.newPos(e.velocity[1]);
			double dist_travelled = physics.getDistance(old_pos, new_pos);
			Rocket r = (Rocket) e;
			r.updateDistTravelled(dist_travelled);
		}
	}
	
	public double getDistTravelled() {
		if (this.rocketMove) {
			Rocket r = (Rocket) entities[0];
			return r.distTravelled;
		}
		return 0.0;
	}

	public long getTimeAllowed() {
		return timeAllowed;
	}

	public void setTimeAllowed(long timeAllowed) {
		this.timeAllowed = timeAllowed;
	}
}

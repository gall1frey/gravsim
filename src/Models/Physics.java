package Models;

public class Physics {
	
	private static Physics physics_instance = null;
	public static double univGravConst;
	private double astronomicalUnit;
	public static double timestep;
	public double scaleFactor;
	public double scale;
	
	private Physics() {
		univGravConst = 6.67408e-11;
		astronomicalUnit = 149.6e6 * 1000;
		timestep = 3600 * 5;
		scaleFactor = 250;
		setScale(scaleFactor/astronomicalUnit);
	}
	
	public static Physics getInstance() {
		if (physics_instance == null) {
			physics_instance = new Physics();
		}
		return physics_instance;
	}
	
	public double[] gravForce(double m1, double m2, double[] pos1, double[] pos2) {
		double dist = getDistance(pos1,pos2);
		double force = (univGravConst*m1*m2)/Math.pow(dist, 2);
		double theta = getAngle(pos1,pos2);
		double force_x = Math.cos(theta)*force;
		double force_y = Math.sin(theta)*force;
		return new double[] {force_x, force_y};
	}
	
	public double newVel(double force, double mass) {
		return force / mass * timestep;
	}
	
	public double newPos(double vel) {
		return vel * timestep;
	}
	
	public double getDistance(double[] pos1, double[] pos2) {
		return Math.sqrt(Math.pow(pos2[0]-pos1[0],2)+Math.pow(pos2[1]-pos1[1],2));
	}
	
	public double getAngle(double[] pos1, double[] pos2) {
		return Math.atan2((pos2[1] - pos1[1]),(pos2[0] - pos1[0]));
	}
	
	public double getAngle(double vx, double vy) {
		return Math.atan2(vy, vx);
	}
	
	public double[] getComponents(double val, double angle) {
		double[] components = new double[2];
		components[0] = Math.cos(angle)*val;
		components[1] = Math.sin(angle)*val;
		return components;
	}
	
	public void updateScale(double scaleFactor) {
		this.scaleFactor = scaleFactor;
		setScale(this.scaleFactor/astronomicalUnit);
	}

	public double getScale() {
		return scale;
	}

	private void setScale(double scale) {
		this.scale = scale;
	}
	
	public double auToM(double au) {
		return astronomicalUnit*au;
	}
	
	public double[] auToM(double[] au) {
		double[] res = new double[au.length];
		for (int i = 0; i < au.length; i++) {
			res[i] = auToM(au[i]);
		}
		return res;
	}
	
	public double mToAu(double km) {
		return km/astronomicalUnit;
	}
	
	public double[] mToAu(double[] km) {
		double[] res = new double[km.length];
		for (int i = 0; i < km.length; i++) {
			res[i] = mToAu(km[i]);
		}
		return res;
	}
}

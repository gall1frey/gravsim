package Models;

import java.util.LinkedList;

public class Trail {
	public LinkedList<double[]> pathTravelled = new LinkedList<double[]>();
	private long lenOfTrail = 100000000;
	public int count = 0;
	
	public long getLenOfTrail() {
		return this.lenOfTrail;
	}
	
	public void setLenOfTrail(long l) {
		this.lenOfTrail = l;
	}
	
	public void addPathTravelled(double[] point, int n) {
		if ((count++)%n == 0) {
			if( this.pathTravelled.size() >= this.lenOfTrail ) {
				this.pathTravelled.poll();
			}
			double[] tmp_point = new double[2];
			tmp_point[0] = point[0]; tmp_point[1] = point[1];
			this.pathTravelled.offer(point);
		}
	}
	
	public LinkedList<double[]> getPathTravelled () {
		return this.pathTravelled;
	}
}

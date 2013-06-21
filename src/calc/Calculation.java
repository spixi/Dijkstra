package calc;

import model.Airport;

public class Calculation {

	private int idFrom;
	private int idTo;
	
	public int getIdFrom() {
		return idFrom;
	}
	public void setIdFrom(int idFrom) {
		this.idFrom = idFrom;
	}
	public int getIdTo() {
		return idTo;
	}
	public void setIdTo(int idTo) {
		this.idTo = idTo;
	}
	
	/**
	 * Do every calculation
	 */
	public void calculate() {
		
	}
	
	/**
	 * get the calculated stations in between necessary to reach the destination
	 */
	public int getHops(){
		return -1;
	}
	
	/**
	 * get the calculated distance to the destination
	 */
	public double getDistance() {
		return 0.0;
	}
	
	/**
	 * get the object representing the location / airport where we are currently located
	 */
	public Airport getCurrentLocation() {
		return null;
	}
	
	/**
	 * get the next Airport in line
	 * @return
	 */
	public Airport getNextLocation(){
		return null;
	}
	
	/**
	 * get the previous Airport in line
	 * @return
	 */
	public Airport getPreviousLocation(){
		return null;
	}
	
}

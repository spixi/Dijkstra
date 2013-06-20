package model;

import org.joda.time.Duration;

/**
 * 
 * @author Marius
 * 
 * This structure describes a single connection to another airport
 * 
 */

public class Connection {
	private Integer  destination; //The connection only knows the id of the destination airport,
	                              //so you can add destinations even when the corresponding airports
	                              //are (not yet) created.
	private Duration duration;
	
	public Connection(Integer destination, Duration duration) {
		this.setDestination(destination);
		this.setDuration(duration);
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public Integer getDestination() {
		return this.destination;
	}


	public void setDestination(Integer destination) {
		this.destination = destination;
	}
	
    @Override
	public int hashCode() {
    	//We take the id of the airport as hash key
		return getDestination();
	}

}

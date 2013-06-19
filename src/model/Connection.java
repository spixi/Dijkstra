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
	private Airport  destination;
	private Duration duration;
	
	public Connection(Airport destination, Duration duration) {
		this.setDestination(destination);
		this.setDuration(duration);
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public Airport getDestination() {
		return this.destination;
	}


	public void setDestination(Airport destination) {
		this.destination = destination;
	}
	
    @Override
	public int hashCode() {
    	//We take the id of the airport as hash key
		return this.destination.getId();
	}

}

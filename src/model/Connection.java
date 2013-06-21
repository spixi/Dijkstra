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
	private Duration duration;
	
	public Connection(Duration duration) {
		this.setDuration(duration);
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

}

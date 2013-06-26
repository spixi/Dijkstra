/**
* Connection
* <p>
* This structure describes a single connection to another airport
* @author Marius Spix
*/

package model;

import org.joda.time.Duration;

public class Connection {
	private Duration duration;
	private String apName; // Name of the Airport
	private long apId; // id of the Airport
	
	/*
	 * Constructors
	 */
	public Connection(Duration duration) {
		this.setDuration(duration);
	}
	
	public Connection(Duration duration, int id) {
		this.setDuration(duration);
		this.setId(id);
	}
	
	public Connection(Duration duration, long id, String name) {
		this.setDuration(duration);
		this.setId(id);
		this.setName(name);
	}

	/*
	 * Getter & Setter
	 */
	public String getName() {
		return apName;
	}

	public void setName(String apName) {
		this.apName = apName;
	}

	public long getId() {
		return apId;
	}

	public void setId(long apId) {
		this.apId = apId;
	}
	
	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

}

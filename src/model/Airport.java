package model;

import java.util.HashMap;
import java.util.LinkedHashSet;

/**
 * 
 * @author Marius
 * 
 * This structure describes an airport and its connections to other airports
 * 
 */

public class Airport {
	private String name;
	private HashMap<Airport,Connection> connections = new HashMap<Airport,Connection>();
	
	public Airport(String name) {
		this.setName(name);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashMap<Airport,Connection> getConnections() {
		return this.connections;
	}
}

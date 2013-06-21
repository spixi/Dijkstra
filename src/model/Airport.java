package model;

import java.util.LinkedHashSet;

/**
 * 
 * @author Marius
 * 
 * This structure describes an airport and its connections to other airports
 * 
 */

public class Airport {
	private Integer id;
	private String name;
	private LinkedHashSet<Connection> connections = new LinkedHashSet<Connection>();
	
	public Airport(Integer id, String name) {
		this.setId(id);
		this.setName(name);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LinkedHashSet<Connection> getConnections() {
		return this.connections;
	}
	
    @Override
	public int hashCode() {
    	//We take the id of the airport as hash key
		return getId();
	}
}

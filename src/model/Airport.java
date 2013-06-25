/**
* Airport
* <p>
* This structure describes an airport and its connections to other airports.
* @author Marius Spix
*/
package model;

import java.util.HashMap;

public class Airport {
	private Long   id;
	private String name;
	
	//These are incoming connections, not outgoing connections!!!
	//In the file, however, the outgoing connections are saved.
	private HashMap<Airport,Connection> connections = new HashMap<Airport,Connection>();
	
	protected Airport(Long id) {}
	
	public Airport(Long id, String name) {
		this.setId(id);
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

	public Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public String toString() {
		return getName();
	}
}

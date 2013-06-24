package model;

import java.util.Vector;

public interface ListDataModel {

	public Vector<Airport> getLocations();
	public Vector<Vector<Object>> getRoute(Airport from, Airport to);
	
}

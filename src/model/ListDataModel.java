package model;

import java.util.Vector;

public interface ListDataModel {

	public Vector<String> getLocations();
	public String getFormattedDuration();
	public Vector<String> getRouteHops(Airport from, Airport to);
	
}

package model;

import java.util.ArrayList;
import java.util.Vector;

public interface ListDataModel {

	public Vector<String> getLocations();
	public String getFormattedDuration();
	public ArrayList<String> getRouteHops();
	
}

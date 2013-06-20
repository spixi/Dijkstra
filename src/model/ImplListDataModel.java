package model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Vector;

public class ImplListDataModel implements ListDataModel {
	
	private HashSet<Airport> airportList;
	
	public ImplListDataModel() {
		airportList = new HashSet<Airport>();
	}

	@Override
	public Vector<String> getLocations() {
		Vector<String> v = new Vector<String>();
		for(Airport a : airportList)
			v.add(a.getName());
		return v;
	} 

	public HashSet<Airport> getAirportList() {
		return airportList;
	}

}

package model;

import java.util.LinkedList;
import java.util.Vector;

public class ImplListDataModel implements ListDataModel {
	
	private LinkedList<Airport> airportList = new LinkedList<Airport>();

	@Override
	public Vector<String> getLocations() {
		Vector<String> v = new Vector<String>();
		for(Airport a : airportList)
			v.add(a.getName());
		return v;
	}

	public LinkedList<Airport> getAirportList() {
		return airportList;
	}

}

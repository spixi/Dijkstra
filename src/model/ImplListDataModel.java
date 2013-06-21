package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Vector;

public class ImplListDataModel implements ListDataModel {
	
	private HashMap<Long,Airport> airportList;
	
	public ImplListDataModel() {
		airportList = new HashMap<Long,Airport>();
	}

	@Override
	public Vector<String> getLocations() {
		Vector<String> v = new Vector<String>();
		for(Airport a : airportList.values())
			v.add(a.getName());
		return v;
	} 

	public HashMap<Long,Airport> getAirportList() {
		return airportList;
	}

}

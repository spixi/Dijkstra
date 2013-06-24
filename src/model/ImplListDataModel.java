package model;

import helpers.DateHelper;
import helpers.Pathfinder;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class ImplListDataModel implements ListDataModel {
	
	private HashMap<Long,Airport> airportList;
	
	public ImplListDataModel() {
		airportList = new HashMap<Long,Airport>();
	}

	@Override
	public Vector<Airport> getLocations() {
		Vector<Airport> v = new Vector<Airport>();
		for(Airport a : airportList.values())
			v.add(a);
		return v;
	} 

	public HashMap<Long,Airport> getAirportList() {
		return airportList;
	}


	@Override
	public Vector<Vector<Object>> getRoute(Airport from, Airport to) {
		Pathfinder pf = new Pathfinder(from,airportList.values());
		Vector<Vector<Object>> routeHops = new Vector<Vector<Object>>();
		Airport previous = null;
		
		List<Airport> path = pf.determineShortestPathTo(to);
		
		for(Airport a : path) {
			Vector<Object> row = new Vector<Object>();
			row.add(a.getName());
			if (previous != null) {
				row.add(DateHelper.INSTANCE.durationToString(previous.getConnections().get(a).getDuration()));
			}
			
			routeHops.add(row);
			previous = a;
		}
		
		return routeHops;
	}

}

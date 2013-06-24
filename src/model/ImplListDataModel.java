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
	public Vector<String> getLocations() {
		Vector<String> v = new Vector<String>();
		for(Airport a : airportList.values())
			v.add(a.getName());
		return v;
	} 

	public HashMap<Long,Airport> getAirportList() {
		return airportList;
	}

	@Override
	public Vector<String> getRouteHops(Airport from, Airport to) {
		// TODO Auto-generated method stub
		Pathfinder pf = new Pathfinder(from,airportList.values());
		Vector<String> routeHops = new Vector<String>();
		Airport previous = null;
		
		List<Airport> path = pf.determineShortestPathTo(to);
		
		for(Airport a : path) {
			String s;
			s = a.getName();
			if (previous != null) {
				s += '\t';
				s += DateHelper.INSTANCE.durationToString(previous.getConnections().get(a).getDuration());
			}
			routeHops.add(s);
			previous = a;
		}
		
		return routeHops;
	}

}

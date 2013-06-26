/**
* ImplListDataModel
* <p>
* This is the real implementation of the data model.
* @author Marius Spix
*/

package model;

import helpers.DateHelper;
import helpers.Pathfinder;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.joda.time.Duration;


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
	public Vector<Vector<Object>> getRoute(Airport to, Airport from) {
		Pathfinder pf = new Pathfinder(to,airportList.values());
		Vector<Vector<Object>> routeHops = new Vector<Vector<Object>>();
		Airport previous = null;
		Duration sum     = Duration.ZERO;
		Vector<Object> row;
		
		List<Airport> path = pf.determineShortestPathFrom(from);
		
        for (Airport a : path) {
			row = new Vector<Object>();
			
			if (previous != null) {
				row.add(previous);
				row.add(a);
				
				Duration d = a.getIncomingConnections().get(previous).getDuration();
				
				//cumulate the durations
				sum = sum.plus(d);
				row.add(DateHelper.INSTANCE.durationToString(d));
				routeHops.add(row);
			}
		
			previous = a;
		}
        
        if (path.indexOf(to) > 0) {
        	row = new Vector<Object>();
        	row.add("Summe");
        	row.add("");
        	row.add(DateHelper.INSTANCE.durationToString(sum));
        	routeHops.add(row);
        }
        
		return routeHops;
	}

}

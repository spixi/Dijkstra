package helpers;

import java.util.HashMap;

import org.joda.time.Duration;

import model.Airport;
import model.Connection;

public class Pathfinder {
	
	private class DijkstraAirport implements AirportInterface {
		private Duration d;
		private DijkstraAirport	precursor;
		
		DijkstraAirport(Airport a) {
			super();
		}
	}
	
	private Airport startAirport;
	private Airport destinationAirport;
	
	public Pathfinder(Airport startAirport, Airport destinationAirport) {
		this.startAirport       = startAirport;
		this.destinationAirport = startAirport;
	}
	
	public HashMap<Airport,Connection> determineShortestPath() {
		return null;
	}

}

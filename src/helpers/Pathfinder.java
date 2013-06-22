package helpers;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.joda.time.Duration;

import model.Airport;
import model.Connection;

/**
 * 
 * @author Marius
 *
 * This class contains the implementation of the Dijkstra algorithm
 */

import org.joda.time.ReadableDuration;

public class Pathfinder {
	private HashMap<Airport,Airport>    precessors;
	private HashMap<Airport,Duration>   durations;
	private PriorityQueue<Airport>      connections;
	
	public Pathfinder(Airport startAirport, Collection<Airport> airports) {
		//Create the lookup tables
		precessors        = new HashMap<Airport,Airport>();
		durations         = new HashMap<Airport,Duration>();
		
		
		//Initialize the lookup tables
		for( Airport airport: airports ) {
		    durations.put(airport, new Duration(Long.MAX_VALUE));
		}
		
		durations.put (startAirport, new Duration(Duration.ZERO));
		precessors.put(startAirport, null);
		
		//Create the priority queue of the connections
		connections =
		new PriorityQueue<Airport>(100,
			new MapValueComparator<Airport,Duration>(durations, new ReadableDurationComparator())
		);
		
		//Fill the priority queue with the airports from the data model
		for (Airport airport: airports)
			connections.offer(airport);
		
		calculate();
	}
	
	private void calculate() {
		Airport next;
		
		while(! connections.isEmpty()) {
			next = connections.poll();
			
			for(Airport c : next.getConnections().keySet()) {
				if(connections.contains(c)) {
					updateDuration(next,c);
				}
			}
		}
	}
	
	private void updateDuration(Airport from, Airport to) {
		Duration alternative = getDurationBetween(from,to).plus(durations.get(from));
		
		if (alternative.isShorterThan(durations.get(to))) {
			durations.put(to, new Duration(alternative));
			precessors.put(to, from);
		}
	}
	
	private static Duration getDurationBetween(Airport from, Airport to) {
		//TODO Check for null
		return from.getConnections().get(to).getDuration();
	}
	
	//TODO also transmit Durations
	public Queue<Airport> determineShortestPathTo(Airport destinationAirport) {
		Deque<Airport> deque   = new ConcurrentLinkedDeque<Airport>();
		Airport        current = destinationAirport;
		
		deque.push(destinationAirport);
		
		while(precessors.get(current) != null) {
			current = precessors.get(current);
			deque.push(current);
		}
		
		return deque;
	}

}

/**
* DateHelper
* <p>
* This class contains the actual Dijkstra algorithm and selects the shortest path between
* two nodes.
* @author Marius Spix
*/

package helpers;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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
	
	/**
	 * Constructor of the Pathfinder class
	 * <p>
	 * Initializes the Pathfinder
	 * @param startAirport: the start node
	 * @param airports: a list of all nodes
	 */
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
			new MapValueComparator<Airport,Duration>(durations, new GenericComparator<Duration>())
		);
		
		//Fill the priority queue with the airports from the data model
		for (Airport airport: airports)
			connections.offer(airport);
		
		calculate();
	}
	
	/**
	 * calculate
	 * <p>
	 * The actual calculation method.
	 */
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
	
	/**
	 * updateDuration
	 * <p>
	 * Updates the shortest duration to a node relative to the start node. For internal use only!
	 * @param from: a Node
	 * @param to: another Node
	 */
	private void updateDuration(Airport from, Airport to) {
		Duration alternative = getDurationBetween(from,to).plus(durations.get(from));
		
		if (alternative.isShorterThan(durations.get(to))) {
			durations.put(to, new Duration(alternative));
			precessors.put(to, from);
		}
	}
	
	/**
	 * getDurationBetween
	 * <p>
	 * Gets the duration between two nodes. For internal use only!
	 * @param from
	 * @param to
	 * @return
	 */
	private static Duration getDurationBetween(Airport from, Airport to) {
		Duration d;
		try {
			d = from.getConnections().get(to).getDuration();
		}
		catch( NullPointerException e ) {
			d = new Duration(Long.MAX_VALUE);
		}
		return d;
	}
	
	//TODO also transmit Durations
	/**
	 * determineShortestPathTo
	 * <p>
	 * Determines the shortest path from the start node to another node.
	 * If there is no possible way the behavior is undefined.
	 * @param destinationAirport
	 * @return
	 */
	public List<Airport> determineShortestPathTo(Airport destinationAirport) {
		LinkedList<Airport> list   = new LinkedList<Airport>();
		Airport            current = destinationAirport;
		
		list.addFirst(destinationAirport);
		
		while(precessors.get(current) != null) {
			current = precessors.get(current);
			list.addFirst(current);
		}
		
		return list;
	}

}

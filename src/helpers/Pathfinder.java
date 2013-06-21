package helpers;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

import org.joda.time.Duration;

import model.Airport;
import model.Connection;

/**
 * 
 * @author Marius
 *
 * This class contains the implementation of the Dijkstra algorithm
 */

public class Pathfinder {
	
	private HashMap<Long,Airport>  graph; 
	private MinimumAwareHashMap<Long,ComparableDuration> duration;
	private HashMap<Long,Long>     precessor;
	private HashSet<Long>          allNodes;
	
	private void initialize(HashMap<Long,Airport> graph, Long startId, Long destinationID) {
		//Initialization
		this.graph = graph;
		
		for( Long id: graph.keySet() ) {
			duration.put(id, new ComparableDuration(Long.MAX_VALUE));
			allNodes.add(id);
		}
		
		duration.put(startId, new ComparableDuration(Duration.ZERO));
		precessor.put(startId, null);
	}
	
	private void calculate() {
		Long idWithShortestDistance;
		
		while(!allNodes.isEmpty()) {
			idWithShortestDistance = duration.getKeyOfMinValue();
			
			//TODO stopped here
			foo.bar();
		}
	}
	
	private void updateDuration(Long from, Long to) {
		Duration alternative = getDurationBetween(from,to).plus(duration.get(from));
		
		if (alternative.isShorterThan(duration.get(to))) {
			duration.put(to, new ComparableDuration(alternative));
			precessor.put(to, from);
		}
	}
	
	private Duration getDurationBetween(Long from, Long to) {
		//TODO error check (null)
		return graph.get(from).getConnections().get(to).getDuration();
	}

}
